package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Checks normal UI-manager startup using JavaFX. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class UiManagerTest {

    @Test
    void start_validDependencies_showsPopulatedPrimaryStage() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Stage primaryStage = new Stage();
            try {
                UiManager uiManager = new UiManager(new LogicStub(), Path.of("data", "addressbook.json"));

                uiManager.start(primaryStage);

                assertTrue(primaryStage.isShowing());
                assertNotNull(primaryStage.getScene());
                assertFalse(primaryStage.getIcons().isEmpty());
                assertNotNull(primaryStage.getScene().lookup("#commandTextField"));
                assertNotNull(primaryStage.getScene().lookup("#resultDisplay"));
                assertNotNull(primaryStage.getScene().lookup("#personListView"));
            } finally {
                primaryStage.close();
            }
            return null;
        });
    }

    /**
     * Supplies the minimum logic state needed to start the UI.
     */
    private static class LogicStub implements Logic {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private GuiSettings guiSettings = new GuiSettings();

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("success");
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
