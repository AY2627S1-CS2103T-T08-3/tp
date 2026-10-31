package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.io.TempDir;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

/**
 * Checks keyboard focus using actual controls. Set RUN_UI_TESTS=true on a machine with a display.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class CommandFocusShortcutTest {
    @TempDir
    public Path temporaryFolder;

    @Test
    void shortcut_fromListAndFeedback_preservesPendingCommandAndSelection() throws Exception {
        FutureTask<Void> checks = new FutureTask<>(() -> {
            Platform.setImplicitExit(false);
            Model model = new ModelManager();
            model.addPerson(new PersonBuilder().build());
            Path dataPath = temporaryFolder.resolve("data.json");
            StorageManager storage = new StorageManager(new JsonAddressBookStorage(dataPath),
                    new JsonUserPrefsStorage(temporaryFolder.resolve("preferences.json")));
            MainWindow window = new MainWindow(new Stage(), new LogicManager(model, storage), dataPath);
            window.fillInnerParts();
            Scene scene = window.getRoot().getScene();
            TextField input = (TextField) scene.lookup("#commandTextField");
            ListView<?> list = (ListView<?>) scene.lookup("#personListView");
            list.getSelectionModel().select(0);
            input.setText("clear");
            for (Node target : List.of(list, scene.lookup("#resultDisplay"))) {
                target.requestFocus();
                assertEquals(target, scene.getFocusOwner());
                target.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.L,
                        false, false, false, false));
                assertEquals(target, scene.getFocusOwner());
                boolean isMac = System.getProperty("os.name").startsWith("Mac");
                target.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.L,
                        false, !isMac, false, isMac));
                assertEquals(input, scene.getFocusOwner());
                assertEquals("clear", input.getText());
                assertEquals(0, list.getSelectionModel().getSelectedIndex());
                assertEquals(1, model.getFilteredPersonList().size());
            }
            return null;
        });
        try {
            Platform.startup(checks);
        } catch (IllegalStateException e) {
            Platform.runLater(checks);
        }
        checks.get(15, TimeUnit.SECONDS);
    }
}
