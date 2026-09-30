package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Remark;
import seedu.address.storage.JsonAddressBookStorage;

public class RemarkCommandTest {
    @TempDir
    public Path temporaryDirectory;

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void execute_addReplaceClearAndEdit_preservesOtherFields() throws Exception {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        parser.parseCommand("remark 1 r/Likes swimming").execute(model);
        var updated = model.getFilteredPersonList().get(0);
        assertEquals(new Remark("Likes swimming"), updated.getRemark());
        assertEquals(ALICE.getName(), updated.getName());
        assertEquals(ALICE.getPhone(), updated.getPhone());
        assertEquals(ALICE.getEmail(), updated.getEmail());
        assertEquals(ALICE.getAddress(), updated.getAddress());
        assertEquals(ALICE.getTags(), updated.getTags());
        assertNotEquals(ALICE, updated);
        assertTrue(ALICE.isSamePerson(updated));

        parser.parseCommand("edit 1 p/98765432").execute(model);
        assertEquals(new Remark("Likes swimming"), model.getFilteredPersonList().get(0).getRemark());
        parser.parseCommand("remark 1 r/Enjoys hiking").execute(model);
        assertEquals(new Remark("Enjoys hiking"), model.getFilteredPersonList().get(0).getRemark());
        CommandResult result = parser.parseCommand("remark 1 r/").execute(model);
        assertTrue(result.getFeedbackToUser().startsWith("Removed remark"));
        assertEquals(new Remark(""), model.getFilteredPersonList().get(0).getRemark());
        parser.parseCommand("remark 1 r/New note").execute(model);
        parser.parseCommand("remark 1").execute(model);
        assertEquals(new Remark(""), model.getFilteredPersonList().get(0).getRemark());
    }

    @Test
    public void execute_filteredList_usesDisplayedIndex() throws Exception {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        model.addPerson(BOB);
        model.updateFilteredPersonList(person -> person.equals(BOB));
        assertThrows(CommandException.class, () -> parser.parseCommand("remark 2 r/Note").execute(model));
        parser.parseCommand("remark 1 r/Bob only").execute(model);
        assertEquals(new Remark(""), model.getAddressBook().getPersonList().get(0).getRemark());
        assertEquals(new Remark("Bob only"), model.getAddressBook().getPersonList().get(1).getRemark());
        assertEquals(2, model.getFilteredPersonList().size());
    }

    @Test
    public void parse_invalidIndex_rejected() {
        for (String command : new String[]{"remark", "remark 0 r/Note", "remark -1 r/Note",
            "remark abc r/Note", "remark 999999999999999 r/Note"}) {
            assertThrows(ParseException.class, () -> parser.parseCommand(command));
        }
    }

    @Test
    public void storage_roundTripAndLegacyData_preservesRemarks() throws Exception {
        Model model = new ModelManager();
        model.addPerson(ALICE);
        parser.parseCommand("remark 1 r/Likes café and swimming").execute(model);
        Path path = temporaryDirectory.resolve("addressbook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(path);
        storage.saveAddressBook(model.getAddressBook());
        assertEquals(model.getAddressBook(), storage.readAddressBook().orElseThrow());

        Files.writeString(path, Files.readString(path).replaceAll("(?m)^.*\\\"remark\\\".*\\R", ""));
        assertEquals(new Remark(""), storage.readAddressBook().orElseThrow().getPersonList().get(0).getRemark());
    }
}
