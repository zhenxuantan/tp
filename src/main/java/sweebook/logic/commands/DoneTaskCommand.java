package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import sweebook.commons.core.Messages;
import sweebook.commons.core.index.Index;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.task.Task;

/**
 * Marks a task identified using it's displayed index from the task list as done.
 */
public class DoneTaskCommand extends Command {
    public static final String COMMAND_WORD = "doneTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks task at specified index as done. "
        + "Parameters: "
        + "INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " "
        + "1";

    public static final String MESSAGE_SUCCESS = "Noted! I've marked this task as done:\n\t %1$s\n\t";

    public static final String MESSAGE_ALREADY_DONE = "This task has already been marked as done!\n\t %1$s\n\t";

    private final Index targetIndex;

    public DoneTaskCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> taskList = model.getTasks();

        if (targetIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        Task doneTask = taskList.get(targetIndex.getZeroBased());
        if (doneTask.isDone()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_DONE, doneTask.toString()));
        }
        model.doneTask(doneTask);
        return new CommandResult(String.format(MESSAGE_SUCCESS, doneTask.toString()));
    }
}
