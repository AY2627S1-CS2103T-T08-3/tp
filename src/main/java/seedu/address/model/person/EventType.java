package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of photography event for a person.
 */
public class EventType {
    public static final String MESSAGE_CONSTRAINTS = "Event type should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Creates an event type.
     */
    public EventType(String eventType) {
        requireNonNull(eventType);
        checkArgument(isValidEventType(eventType), MESSAGE_CONSTRAINTS);
        value = eventType;
    }

    /**
     * Returns true if the value is a non-blank event type.
     */
    public static boolean isValidEventType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EventType otherEventType && value.equals(otherEventType.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
