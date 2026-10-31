package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Checks main-window command integration using JavaFX. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class MainWindowTest {

    @Test
    void commandEntered_success_displaysFeedbackAndUpdatesHeading() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Stage primaryStage = new Stage();
            try {
                LogicStub logic = new LogicStub();
                logic.commandResult = new CommandResult("Showing 0 engagement(s).");
                MainWindow mainWindow = createShownMainWindow(primaryStage, logic);
                TextField commandTextField = findCommandTextField(primaryStage);
                TextArea resultDisplay = findResultDisplay(primaryStage);

                commandTextField.setText("list");
                commandTextField.fireEvent(new ActionEvent());

                assertEquals("list", logic.executedCommand);
                assertEquals("", commandTextField.getText());
                assertEquals("Showing 0 engagement(s).", resultDisplay.getText());
                assertSame(primaryStage, mainWindow.getPrimaryStage());
                assertEquals("All engagements (0)", logic.getPersonListHeading());
            } finally {
                primaryStage.close();
            }
            return null;
        });
    }

    @Test
    void commandEntered_parseFailure_displaysErrorAndRetainsInput() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Stage primaryStage = new Stage();
            try {
                LogicStub logic = new LogicStub();
                logic.parseException = new ParseException("Unknown command");
                createShownMainWindow(primaryStage, logic);
                TextField commandTextField = findCommandTextField(primaryStage);
                TextArea resultDisplay = findResultDisplay(primaryStage);

                commandTextField.setText("invalid");
                commandTextField.fireEvent(new ActionEvent());

                assertEquals("invalid", commandTextField.getText());
                assertTrue(commandTextField.getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS));
                assertEquals("Unknown command", resultDisplay.getText());
                assertTrue(resultDisplay.getStyleClass().contains("feedback-error"));
            } finally {
                primaryStage.close();
            }
            return null;
        });
    }

    @Test
    void commandEntered_exitResult_savesWindowSettingsAndHidesWindow() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Stage primaryStage = new Stage();
            try {
                LogicStub logic = new LogicStub();
                logic.commandResult = new CommandResult("Exiting ShutterLink.", false, true);
                createShownMainWindow(primaryStage, logic);
                GuiSettings expectedSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                        (int) primaryStage.getX(), (int) primaryStage.getY());
                TextField commandTextField = findCommandTextField(primaryStage);
                commandTextField.setText("exit");

                commandTextField.fireEvent(new ActionEvent());

                assertFalse(primaryStage.isShowing());
                assertEquals(expectedSettings, logic.guiSettings);
            } finally {
                primaryStage.close();
            }
            return null;
        });
    }

    private static MainWindow createShownMainWindow(Stage primaryStage, Logic logic) {
        MainWindow mainWindow = new MainWindow(primaryStage, logic, Path.of("data", "addressbook.json"));
        mainWindow.show();
        mainWindow.fillInnerParts();
        return mainWindow;
    }

    private static TextField findCommandTextField(Stage primaryStage) {
        return (TextField) primaryStage.getScene().lookup("#commandTextField");
    }

    private static TextArea findResultDisplay(Stage primaryStage) {
        return (TextArea) primaryStage.getScene().lookup("#resultDisplay");
    }

    /**
     * Provides controllable logic responses for main-window tests.
     */
    private static class LogicStub implements Logic {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private GuiSettings guiSettings = new GuiSettings(900, 700, 12, 34);
        private CommandResult commandResult = new CommandResult("success");
        private ParseException parseException;
        private String executedCommand;

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            executedCommand = commandText;
            if (parseException != null) {
                throw parseException;
            }
            return commandResult;
        }

        @Override
        public String getPersonListHeading() {
            return "All engagements (0)";
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return guiSettings;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            this.guiSettings = guiSettings;
        }
    }
}
