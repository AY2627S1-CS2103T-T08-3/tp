package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(List.of(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withName(ALICE.getName().fullName.toUpperCase())
                .withEventType(ALICE.getEventType().value.toLowerCase()).build();
        List<Person> newPersons = List.of(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void copyConstructor_sourceChangedLater_copyIsUnaffected() {
        AddressBook source = getTypicalAddressBook();
        AddressBook copy = new AddressBook(source);

        source.removePerson(ALICE);

        assertTrue(copy.hasPerson(ALICE));
        assertFalse(source.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withName(ALICE.getName().fullName.toUpperCase())
                .withEventType(ALICE.getEventType().value.toLowerCase()).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void addPerson_duplicatePerson_throwsDuplicatePersonException() {
        addressBook.addPerson(ALICE);

        assertThrows(DuplicatePersonException.class, () -> addressBook.addPerson(ALICE));
    }

    @Test
    public void setPerson_existingPerson_replacesPerson() {
        addressBook.addPerson(ALICE);

        addressBook.setPerson(ALICE, BOB);

        assertEquals(List.of(BOB), addressBook.getPersonList());
    }

    @Test
    public void setPerson_missingTarget_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setPerson(ALICE, BOB));
    }

    @Test
    public void removePerson_existingPerson_removesPerson() {
        addressBook.addPerson(ALICE);

        addressBook.removePerson(ALICE);

        assertTrue(addressBook.getPersonList().isEmpty());
    }

    @Test
    public void removePerson_missingPerson_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removePerson(ALICE));
    }

    @Test
    public void equals_samePersons_returnsTrue() {
        AddressBook first = getTypicalAddressBook();
        AddressBook second = getTypicalAddressBook();

        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void equals_differentPersonsOrType_returnsFalse() {
        AddressBook addressBook = getTypicalAddressBook();
        AddressBook differentAddressBook = getTypicalAddressBook();
        differentAddressBook.removePerson(ALICE);

        assertFalse(addressBook.equals(differentAddressBook));
        assertFalse(addressBook.equals(null));
        assertFalse(addressBook.equals(new Object()));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
