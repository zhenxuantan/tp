package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;

import sweebook.commons.core.Messages;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.group.GroupPredicate;

/**
 * Retrieves people from the same specified group.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are in the specified group.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    private final GroupPredicate predicate;

    /**
     * Constructor of the GroupCommand class.
     *
     * @param predicate The predicate to filter the list of people.
     */
    public GroupCommand(GroupPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && predicate.equals(((GroupCommand) other).predicate)); // state check
    }
}
