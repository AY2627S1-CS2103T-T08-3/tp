package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Checks the highlight of real result display controls. Set RUN_UI_TESTS=true on a machine with a display to run
 * this test.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class ResultDisplayTest {
    private static final String WARNING = "warning";

    @Test
    void feedback_warningThenNormal_highlightedOnlyForWarning() throws Exception {
        FutureTask<Void> checks = new FutureTask<>(() -> {
            Platform.setImplicitExit(false);
            ResultDisplay resultDisplay = new ResultDisplay();
            TextArea textArea = (TextArea) resultDisplay.getRoot().lookup("#resultDisplay");

            resultDisplay.setFeedbackToUser("Showing 3 engagement(s).");
            assertEquals("Showing 3 engagement(s).", textArea.getText());
            assertFalse(textArea.getStyleClass().contains(WARNING));

            resultDisplay.setFeedbackToUser("New engagement added.\nWarning: possible typo.");
            assertTrue(textArea.getStyleClass().contains(WARNING));

            // repeated warnings keep a single style class
            resultDisplay.setFeedbackToUser("New engagement added.\nWarning: possible typo.");
            assertEquals(1, textArea.getStyleClass().stream().filter(WARNING::equals).count());

            // the next normal message clears the highlight
            resultDisplay.setFeedbackToUser("Showing 3 engagement(s).");
            assertFalse(textArea.getStyleClass().contains(WARNING));
            return null;
        });
        try {
            Platform.startup(checks);
        } catch (IllegalStateException e) {
            // Another UI test may already have started the shared JavaFX toolkit.
            Platform.runLater(checks);
        }
        checks.get(15, TimeUnit.SECONDS);
    }
}
