package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventDate(null));
    }

    @Test
    public void constructor_invalidEventDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventDate("2026-02-30"));
    }

    @Test
    public void isValidEventDate() {
        assertFalse(EventDate.isValidEventDate("2026-02-30"));
        assertFalse(EventDate.isValidEventDate("07-10-2026"));
        assertTrue(EventDate.isValidEventDate("2026-10-07"));
    }
}
