package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Names should start with a letter or digit, "
            + "and may only contain letters, digits, spaces, apostrophes ('), hyphens (-), periods (.) "
            + "and slashes (/), e.g. Raj s/o Kumar.";

    /*
     * The first character of the name must be a letter or digit,
     * otherwise " " (a blank string) or "-" becomes a valid input.
     * Letters include accented letters, and ’ is the typographic apostrophe.
     * Slashes allow relation markers such as s/o (son of) and d/o (daughter of).
     */
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{N}][\\p{L}\\p{M}\\p{N} '’./\\-]*";

    /** Typographic apostrophe, which autocorrect often inserts in place of a straight apostrophe. */
    private static final char TYPOGRAPHIC_APOSTROPHE = '’';

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns {@code name} with surrounding whitespace removed, runs of spaces or tabs collapsed into one space,
     * and typographic apostrophes replaced by straight ones, so that the same name typed differently is stored
     * identically.
     */
    public static String normalize(String name) {
        requireNonNull(name);
        return StringUtil.normalizeWhitespace(name).replace(TYPOGRAPHIC_APOSTROPHE, '\'');
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name otherName)) {
            return false;
        }

        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
