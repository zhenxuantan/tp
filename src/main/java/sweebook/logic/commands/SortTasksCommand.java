package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static sweebook.logic.parser.CliSyntax.PREFIX_ORDER;
import static sweebook.logic.parser.CliSyntax.PREFIX_PARAMETER;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.task.SortTaskComparator;

/**
 * Sorts the tasks in the task list with a specified parameter and order.
 */
public class SortTasksCommand extends Command {
    public static final String COMMAND_WORD = "sortTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to a given parameter "
        + "('desc' (for description), 'date' (for a deadline / time of event),\n"
        + "'pty' (for priority) and 'group') and order ('a' (for ascending), 'd' (for descending)).\n"
        + "Parameters: "
        + PREFIX_PARAMETER + "PARAMETER "
        + PREFIX_ORDER + "ORDER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PARAMETER + "desc "
        + PREFIX_ORDER + "a\n"
        + "Sorts the tasks by description in ascending order (lexicographical order)";

    public static final String MESSAGE_SUCCESS = "Sorted tasks by %1$s";

    private final SortTaskComparator comparator;

    /**
     * Constructor for SortTasksCommand
     * @param comparator the comparator to compare tasks.
     */
    public SortTasksCommand(SortTaskComparator comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String returnMessage = String.format(MESSAGE_SUCCESS, comparator.toString()) + "\n";
        requireNonNull(model);
        model.updateSortedTaskList(comparator);
        return new CommandResult(returnMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortTasksCommand // instanceof handles nulls
            && comparator.equals(((SortTasksCommand) other).comparator)); // state check
    }
}
