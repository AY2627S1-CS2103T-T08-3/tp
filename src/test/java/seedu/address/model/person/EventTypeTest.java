package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventType(null));
    }

    @Test
    public void constructor_invalidEventType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventType(" "));
    }

    @Test
    public void isValidEventType() {
        assertFalse(EventType.isValidEventType(""));
        assertFalse(EventType.isValidEventType(" "));
        assertTrue(EventType.isValidEventType("Wedding"));
    }
}
