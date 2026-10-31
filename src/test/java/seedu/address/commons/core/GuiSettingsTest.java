package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.geometry.Rectangle2D;

public class GuiSettingsTest {
    private static final Rectangle2D PRIMARY = new Rectangle2D(0, 0, 1920, 1040);
    private static final Rectangle2D SECONDARY = new Rectangle2D(-1600, -200, 1600, 900);

    @Test
    public void savedTitle_insideScreenOrExactlyAtSafeBoundary_isVisible() {
        assertTrue(new GuiSettings(800, 600, 100, 80).hasVisibleWindowTitle(List.of(PRIMARY)));
        assertTrue(new GuiSettings(800, 600, 1760, 1000).hasVisibleWindowTitle(List.of(PRIMARY)));
    }

    @Test
    public void savedTitle_missingOffscreenOrOverTaskbar_isNotVisible() {
        assertFalse(new GuiSettings().hasVisibleWindowTitle(List.of(PRIMARY)));
        assertFalse(new GuiSettings(800, 600, 5000, 4000).hasVisibleWindowTitle(List.of(PRIMARY)));
        assertFalse(new GuiSettings(800, 600, 1761, 1000).hasVisibleWindowTitle(List.of(PRIMARY)));
        assertFalse(new GuiSettings(800, 600, 100, 1001).hasVisibleWindowTitle(List.of(PRIMARY)));
        assertFalse(new GuiSettings(800, 600, 100, -1).hasVisibleWindowTitle(List.of(PRIMARY)));
    }

    @Test
    public void savedTitle_onNegativeCoordinateMonitor_isVisibleUntilMonitorIsDisconnected() {
        GuiSettings savedSettings = new GuiSettings(800, 600, -1500, -150);
        assertTrue(savedSettings.hasVisibleWindowTitle(List.of(PRIMARY, SECONDARY)));
        assertFalse(savedSettings.hasVisibleWindowTitle(List.of(PRIMARY)));
    }

    @Test
    public void savedTitle_splitAcrossMonitors_doesNotHaveEnoughSpaceOnEitherScreen() {
        assertFalse(new GuiSettings(800, 600, -100, 100).hasVisibleWindowTitle(List.of(PRIMARY, SECONDARY)));
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
