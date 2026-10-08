package seedu.address.model.person;
import java.util.Locale;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests whether a person's selected field exactly matches the search value.
 * Matching is case-insensitive.
 */
public class PersonSearchPredicate implements Predicate<Person> {
    public enum Field {
        NAME,
        PHONE,
        EMAIL,
        EVENT_TYPE
    }
    private final Field field;
    private final String searchValue;

    public PersonSearchPredicate(Field field, String searchValue) {
        this.field = requireNonNull(field);
        this.searchValue = requireNonNull(searchValue).trim();
    }

    @Override
    public boolean test(Person person) {
        String actualValue = switch (field) {
            case NAME -> person.getName().fullName;
            case PHONE -> person.getPhone().value;
            case EMAIL -> person.getEmail().value;
            case EVENT_TYPE -> person.getEventType().value;
        };

        return actualValue.toLowerCase(Locale.ROOT)
                .contains(searchValue.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonSearchPredicate otherPredicate)) {
            return false;
        }

        return field == otherPredicate.field
                && searchValue.equalsIgnoreCase(otherPredicate.searchValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("field", field)
                .add("searchValue", searchValue)
                .toString();
    }



}
