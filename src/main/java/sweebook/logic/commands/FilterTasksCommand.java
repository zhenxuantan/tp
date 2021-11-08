package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;

import sweebook.commons.core.Messages;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.task.FilterTaskPredicate;

/**
 * Filters the tasks in the task list with a specified criterion.
 */
public class FilterTasksCommand extends Command {
    public static final String COMMAND_WORD = "filterTasks";

    public static final String MULTIPLE_FIELD_ERROR_MESSAGE = "filterTasks can only accept 1 field at a time.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": filters tasks based on specified criterion.\n"
            + "Parameters: " + "FILTER_CRITERION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "CS2101 ";

    public static final String MESSAGE_SUCCESS = "Filtered tasks by %1$s";

    private final FilterTaskPredicate predicate;

    /**
     * Creates FilterTasksCommand to filter according to the specified {@code filterTaskCriterion}
     */
    public FilterTasksCommand(FilterTaskPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!predicate.isValidPredicate()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        String returnMessage = String.format(MESSAGE_SUCCESS, predicate.toString()) + "\n";
        model.updateFilteredTaskList(predicate);
        return new CommandResult(returnMessage);
    }

}
