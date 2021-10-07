package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "deleteTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes task at specified index. "
        + "Parameters: "
        + PREFIX_INDEX + "INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Noted! I've removed this task:\n\t %1$s\n\t";

    private final int index;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public DeleteTaskCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task toDelete = model.deleteTask(this.index);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.toString()));
    }
}
