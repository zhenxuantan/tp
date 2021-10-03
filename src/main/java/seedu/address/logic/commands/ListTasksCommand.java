package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskRecords;
import seedu.address.model.task.Task;

/**
 * Queries the addressbook for the saved tasks of an existing person.
 */
public class ListTasksCommand extends Command {

    public static final String COMMAND_WORD = "listTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks saved for the current user.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String returnMessage = "Your Tasks: \n";
        requireNonNull(model);
        ReadOnlyTaskRecords taskRecords = model.getTaskList();
        ObservableList<Task> taskList = taskRecords.getTaskList();
        for (Task t : taskList) {
            returnMessage = returnMessage + t.toString() + "\n";
        }
        return new CommandResult(returnMessage);
    }
}
