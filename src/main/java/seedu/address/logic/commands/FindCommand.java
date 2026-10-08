package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonSearchPredicate;

/**
 * Finds and lists all persons in the address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds contacts by exactly one field.\n"
            + "Parameters: n/NAME, p/PHONE, e/EMAIL, or t/EVENT_TYPE\n"
            + "Examples:\n"
            + "find n/Alice Pauline\n"
            + "find p/91234567\n"
            + "find e/alice@example.com\n"
            + "find t/Wedding";

    private final PersonSearchPredicate predicate;

    public FindCommand(PersonSearchPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        int numberOfMatches = model.getFilteredPersonList().size();

        if (numberOfMatches == 0) {
            return new CommandResult("No matching contacts found.");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
