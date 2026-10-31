package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Point;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    @Test
    public void defaultConstructor_usesDefaultSizeAndNoCoordinates() {
        GuiSettings guiSettings = new GuiSettings();

        assertEquals(740, guiSettings.getWindowWidth());
        assertEquals(600, guiSettings.getWindowHeight());
        assertNull(guiSettings.getWindowCoordinates());
    }

    @Test
    public void getWindowCoordinates_returnsDefensiveCopy() {
        GuiSettings guiSettings = new GuiSettings(800, 600, 10, 20);
        Point returnedCoordinates = guiSettings.getWindowCoordinates();

        returnedCoordinates.translate(100, 100);

        assertEquals(new Point(10, 20), guiSettings.getWindowCoordinates());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        GuiSettings first = new GuiSettings(800, 600, 10, 20);
        GuiSettings second = new GuiSettings(800, 600, 10, 20);

        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void equals_differentValuesOrType_returnsFalse() {
        GuiSettings guiSettings = new GuiSettings(800, 600, 10, 20);

        assertNotEquals(guiSettings, new GuiSettings(801, 600, 10, 20));
        assertNotEquals(guiSettings, new GuiSettings(800, 601, 10, 20));
        assertNotEquals(guiSettings, new GuiSettings(800, 600, 11, 20));
        assertNotEquals(guiSettings, null);
        assertNotEquals(guiSettings, new Object());
    }

    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }
}
