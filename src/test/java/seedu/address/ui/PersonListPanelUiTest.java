package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Checks person-list controls using JavaFX. Set RUN_UI_TESTS=true on a machine with a display to run.
 */
@EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
class PersonListPanelUiTest {

    @Test
    void constructorAndSetHeading_bindItemsAndUpdateHeading() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            Person first = new PersonBuilder().withName("First Client").build();
            Person second = new PersonBuilder().withName("Second Client").build();
            ObservableList<Person> persons = FXCollections.observableArrayList(first);
            PersonListPanel panel = new PersonListPanel(persons);
            ListView<?> listView = (ListView<?>) panel.getRoot().lookup("#personListView");
            Label heading = (Label) panel.getRoot().lookup("#listHeading");

            panel.setHeading("Engagements (1)");
            persons.add(second);

            assertEquals("Engagements (1)", heading.getText());
            assertSame(persons, listView.getItems());
            assertEquals(2, listView.getItems().size());

            ListCell<?> cell = createCell(listView);
            assertNotNull(cell);
            assertEquals(0, cell.getMinWidth());
            assertEquals(0, cell.getPrefWidth());
            return null;
        });
    }

    private static <T> ListCell<T> createCell(ListView<T> listView) {
        return listView.getCellFactory().call(listView);
    }
}
