package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents the date of a photography event in ISO-8601 format.
 */
public class EventDate {
    public static final String MESSAGE_CONSTRAINTS = "Event date should be a valid date in YYYY-MM-DD format";
    public final LocalDate value;

    /**
     * Creates an event date from an ISO-8601 date string.
     */
    public EventDate(String eventDate) {
        requireNonNull(eventDate);
        checkArgument(isValidEventDate(eventDate), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(eventDate);
    }

    /**
     * Returns true if the value is a valid date in canonical ISO-8601 form.
     */
    public static boolean isValidEventDate(String test) {
        try {
            LocalDate parsedDate = LocalDate.parse(test);
            return test.equals(parsedDate.toString());
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EventDate otherEventDate && value.equals(otherEventDate.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
