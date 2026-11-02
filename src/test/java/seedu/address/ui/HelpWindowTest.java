package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Checks help-window behavior using a real JavaFX stage. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class HelpWindowTest {

    @Test
    void constructor_withOwner_configuresContentAndModality() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Stage owner = new Stage();
            Stage root = new Stage();
            try {
                HelpWindow helpWindow = new HelpWindow(root, owner, () -> { });
                owner.show();
                helpWindow.show();
                Label helpMessage = (Label) helpWindow.getRoot().getScene().lookup("#helpMessage");

                assertEquals(HelpWindow.HELP_MESSAGE, helpMessage.getText());
                assertEquals(owner, root.getOwner());
                assertEquals(Modality.WINDOW_MODAL, root.getModality());
                assertTrue(helpWindow.isShowing());
            } finally {
                root.close();
                owner.close();
            }
            return null;
        });
    }

    @Test
    void keyboardInput_nonEscapeThenEscape_closesOnlyOnEscape() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            AtomicInteger hiddenCount = new AtomicInteger();
            HelpWindow helpWindow = new HelpWindow(hiddenCount::incrementAndGet);
            helpWindow.show();

            fireKeyPressed(helpWindow, KeyCode.A);
            assertTrue(helpWindow.isShowing());

            fireKeyPressed(helpWindow, KeyCode.ESCAPE);
            assertFalse(helpWindow.isShowing());
            assertEquals(1, hiddenCount.get());
            return null;
        });
    }

    @Test
    void copyGuideLink_spaceKey_copiesUrlAndResetsAfterReopen() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            HelpWindow helpWindow = new HelpWindow();
            try {
                helpWindow.show();
                Button copyButton = (Button) helpWindow.getRoot().getScene().lookup("#copyLinkButton");

                assertTrue(copyButton.isFocusTraversable());
                copyButton.requestFocus();
                copyButton.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE,
                        false, false, false, false));
                copyButton.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.SPACE,
                        false, false, false, false));

                assertEquals(HelpWindow.USERGUIDE_URL, Clipboard.getSystemClipboard().getString());
                assertEquals("Link copied", copyButton.getText());
                assertTrue(helpWindow.isShowing());

                fireKeyPressed(helpWindow, KeyCode.ESCAPE);
                assertFalse(helpWindow.isShowing());

                helpWindow.show();
                assertEquals("Copy User Guide link", copyButton.getText());
                assertTrue(helpWindow.isShowing());
            } finally {
                helpWindow.hide();
            }
            return null;
        });
    }

    /**
     * Fires a key-pressed event at the help window.
     */
    private static void fireKeyPressed(HelpWindow helpWindow, KeyCode keyCode) {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", keyCode,
                false, false, false, false);
        helpWindow.getRoot().getScene().getRoot().fireEvent(event);
    }
}
