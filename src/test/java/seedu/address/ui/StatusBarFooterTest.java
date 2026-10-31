package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.scene.control.Label;

/**
 * Checks status-bar rendering using real JavaFX controls. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class StatusBarFooterTest {

    @Test
    void constructor_relativeSaveLocation_displaysResolvedPath() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Path saveLocation = Path.of("data", "addressbook.json");
            StatusBarFooter statusBarFooter = new StatusBarFooter(saveLocation);
            Label saveLocationStatus = (Label) statusBarFooter.getRoot().lookup("#saveLocationStatus");

            assertEquals(Paths.get(".").resolve(saveLocation).toString(), saveLocationStatus.getText());
            return null;
        });
    }
}
