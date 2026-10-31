package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void constructor_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UserPrefs(null));
    }

    @Test
    public void constructor_userPrefs_copiesGuiSettings() {
        UserPrefs original = new UserPrefs();
        GuiSettings originalSettings = new GuiSettings(800, 600, 10, 20);
        original.setGuiSettings(originalSettings);

        UserPrefs copy = new UserPrefs(original);

        assertEquals(original, copy);
        original.setGuiSettings(new GuiSettings());
        assertEquals(originalSettings, copy.getGuiSettings());
        assertNotEquals(original, copy);
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UserPrefs first = new UserPrefs();
        UserPrefs second = new UserPrefs();

        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void equals_differentValuesOrType_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(800, 600, 10, 20));

        assertNotEquals(userPrefs, differentUserPrefs);
        assertNotEquals(userPrefs, null);
        assertNotEquals(userPrefs, new Object());
    }

    @Test
    public void toStringMethod() {
        UserPrefs userPrefs = new UserPrefs();

        assertEquals("Gui Settings : " + userPrefs.getGuiSettings(), userPrefs.toString());
    }
}
