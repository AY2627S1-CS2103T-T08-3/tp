package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests accepted list syntax and rejection of malformed arguments without executing commands.
 */
public class ListCommandParserTest {
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parse_plainList_returnsInsertionOrderCommand() throws Exception {
        for (String input : new String[]{"list", "LIST", "  List \t "}) {
            assertEquals(new ListCommand(), parser.parseCommand(input));
        }
    }

    @Test
    public void parse_dateSort_returnsSortedCommand() throws Exception {
        for (String input : new String[]{"list sort/date", "LIST SORT/DATE",
            "list\tSoRt/\tDaTe\t", " list sort/ date "}) {
            assertEquals(new ListCommand(true), parser.parseCommand(input));
        }
    }

    @Test
    public void parse_invalidArguments_reportsSpecificError() {
        String[][] cases = {
            {"list 3", ListCommandParser.MESSAGE_INVALID_FORMAT},
            {"list date sort/date", ListCommandParser.MESSAGE_INVALID_FORMAT},
            {"list sort/date extra", ListCommandParser.MESSAGE_INVALID_FORMAT},
            {"list sort/name", ListCommandParser.MESSAGE_INVALID_SORT},
            {"list sort/date/", ListCommandParser.MESSAGE_INVALID_SORT},
            {"list sort/", ListCommandParser.MESSAGE_EMPTY_SORT},
            {"list sort/date sort/date", ListCommandParser.MESSAGE_DUPLICATE_SORT},
            {"list sort/ SORT/date", ListCommandParser.MESSAGE_DUPLICATE_SORT},
            {"list x/value", "Unsupported parameter: x/."},
            {"list stage/Booked", "Unsupported parameter: stage/."},
            {"list sort/date stage/Booked", "Unsupported parameter: stage/."},
            {"list sort/invalid x/value", "Unsupported parameter: x/."}
        };
        for (String[] testCase : cases) {
            ParseException exception = assertThrows(ParseException.class, () -> parser.parseCommand(testCase[0]));
            assertEquals(testCase[1], exception.getMessage(), testCase[0]);
        }
    }

    @Test
    public void parse_controlCharacters_rejectsInput() {
        for (String input : new String[]{"list\nsort/date", "list sort/date\n",
            "list\rsort/date", "list sort/da\u0000te", "list sort/\u2028date", "list sort/date\u2029",
            "list\u2028sort/date"}) {
            ParseException exception = assertThrows(ParseException.class, () -> parser.parseCommand(input));
            assertEquals(ListCommandParser.MESSAGE_CONTROL_CHARACTER, exception.getMessage());
        }
    }

    @Test
    public void parse_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ListCommandParser().parse(null));
    }
}
