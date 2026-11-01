package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Checks command entry using real JavaFX controls. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class CommandBoxTest {

    @Test
    void commandEntered_emptyCommand_isIgnored() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            List<String> executedCommands = new ArrayList<>();
            CommandBox commandBox = new CommandBox(commandText -> {
                executedCommands.add(commandText);
                return new CommandResult("success");
            });
            TextField textField = (TextField) commandBox.getRoot().lookup("#commandTextField");

            textField.fireEvent(new ActionEvent());

            assertTrue(executedCommands.isEmpty());
            return null;
        });
    }

    @Test
    void commandEntered_validCommand_executesAndClearsInput() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            List<String> executedCommands = new ArrayList<>();
            CommandBox commandBox = new CommandBox(commandText -> {
                executedCommands.add(commandText);
                return new CommandResult("success");
            });
            TextField textField = (TextField) commandBox.getRoot().lookup("#commandTextField");
            textField.setText("list");

            textField.fireEvent(new ActionEvent());

            assertEquals(List.of("list"), executedCommands);
            assertEquals("", textField.getText());
            assertFalse(textField.getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS));
            return null;
        });
    }

    @Test
    void commandEntered_failedCommand_marksInputUntilEdited() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            CommandBox commandBox = new CommandBox(commandText -> {
                throw new CommandException("failed");
            });
            TextField textField = (TextField) commandBox.getRoot().lookup("#commandTextField");
            textField.setText("invalid");

            textField.fireEvent(new ActionEvent());
            textField.fireEvent(new ActionEvent());

            assertEquals(1, textField.getStyleClass().stream()
                    .filter(CommandBox.ERROR_STYLE_CLASS::equals).count());
            assertEquals("invalid", textField.getText());

            textField.setText("list");
            assertFalse(textField.getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS));
            return null;
        });
    }
}
