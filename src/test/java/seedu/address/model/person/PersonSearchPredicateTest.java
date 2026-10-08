package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests the PersonSearchPredicate class.
 */
public class PersonSearchPredicateTest {

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withEventType("Wedding")
                .build();
    }

    @Test
    public void exactNameMatch_returnsTrue() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.NAME,
                "Alice Pauline");

        assertTrue(predicate.test(person));
    }

    @Test
    public void partialNameMatch_returnsFalse() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.NAME,
                "Alice");

        assertFalse(predicate.test(person));
    }

    @Test
    public void exactPhoneMatch_returnsTrue() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.PHONE,
                "91234567");

        assertTrue(predicate.test(person));
    }

    @Test
    public void partialPhoneMatch_returnsFalse() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.PHONE,
                "9123");

        assertFalse(predicate.test(person));
    }

    @Test
    public void exactEmailMatch_returnsTrue() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.EMAIL,
                "alice@example.com");

        assertTrue(predicate.test(person));
    }

    @Test
    public void partialEmailMatch_returnsFalse() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.EMAIL,
                "alice@");

        assertFalse(predicate.test(person));
    }

    @Test
    public void exactEventTypeMatch_returnsTrue() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.EVENT_TYPE,
                "Wedding");

        assertTrue(predicate.test(person));
    }

    @Test
    public void caseInsensitiveMatch_returnsTrue() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.EVENT_TYPE,
                "wedding");

        assertTrue(predicate.test(person));
    }

    @Test
    public void unknownValue_returnsFalse() {
        PersonSearchPredicate predicate = new PersonSearchPredicate(
                PersonSearchPredicate.Field.NAME,
                "Unknown Person");

        assertFalse(predicate.test(person));
    }
}