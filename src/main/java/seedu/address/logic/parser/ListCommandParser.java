package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses list arguments without changing the current view on invalid input.
 * Stage filtering is not supported in this iteration.
 */
public class ListCommandParser implements Parser<ListCommand> {
    public static final String MESSAGE_INVALID_FORMAT = "Invalid command format. Usage: " + ListCommand.MESSAGE_USAGE;
    public static final String MESSAGE_INVALID_SORT = "Invalid sort option. Use list sort/date.";
    public static final String MESSAGE_EMPTY_SORT = "Empty value for sort/.";
    public static final String MESSAGE_DUPLICATE_SORT = "Duplicate parameter: sort/.";
    public static final String MESSAGE_CONTROL_CHARACTER =
            "Enter one command on a single line without control characters.";

    private static final Pattern PREFIX_PATTERN = Pattern.compile("(?:^|[ \t])([a-zA-Z]+/)");

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.codePoints().anyMatch(this::isDisallowedControlCharacter)) {
            throw new ParseException(MESSAGE_CONTROL_CHARACTER);
        }
        String trimmedArgs = args.strip();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        Matcher prefixes = PREFIX_PATTERN.matcher(trimmedArgs);
        if (!prefixes.find() || prefixes.start() != 0) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        int valueStart = prefixes.end();
        validatePrefixes(prefixes);
        String sortValue = trimmedArgs.substring(valueStart).strip();
        if (sortValue.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_SORT);
        }
        if (sortValue.split("[ \t]+").length > 1) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        if (!sortValue.equalsIgnoreCase("date")) {
            throw new ParseException(MESSAGE_INVALID_SORT);
        }
        return new ListCommand(true);
    }

    /**
     * Returns whether a code point would violate the single-line command format.
     */
    private boolean isDisallowedControlCharacter(int character) {
        return (Character.isISOControl(character) && character != '\t')
                || Character.getType(character) == Character.LINE_SEPARATOR
                || Character.getType(character) == Character.PARAGRAPH_SEPARATOR;
    }

    /**
     * Rejects unsupported or repeated prefixes in input order before validating values.
     */
    private void validatePrefixes(Matcher prefixes) throws ParseException {
        boolean hasSortPrefix = false;
        do {
            String prefix = prefixes.group(1).toLowerCase(Locale.ROOT);
            if (!prefix.equals("sort/")) {
                throw new ParseException("Unsupported parameter: " + prefix + ".");
            }
            if (hasSortPrefix) {
                throw new ParseException(MESSAGE_DUPLICATE_SORT);
            }
            hasSortPrefix = true;
        } while (prefixes.find());
    }
}
