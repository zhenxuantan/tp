package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAMETER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.SortTaskComparator;

public class SortTasksCommand extends Command {
    public static final String COMMAND_WORD = "sortTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to given parameter "
        + "and order (a: ascending, d: descending).\n"
        + "Parameters: "
        + PREFIX_PARAMETER + "PARAMETER "
        + PREFIX_ORDER + "ORDER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PARAMETER + "d "
        + PREFIX_ORDER + "a\n"
        + "Sorts the tasks by description in ascending order (lexicographical / chronological order)";

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
}
