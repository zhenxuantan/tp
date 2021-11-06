package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;

/**
 * Queries the task list for the saved tasks of an existing person.
 */
public class ListTasksCommand extends Command {

    public static final String COMMAND_WORD = "listTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks saved for the current user.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
