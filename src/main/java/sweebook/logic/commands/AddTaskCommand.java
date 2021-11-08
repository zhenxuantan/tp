package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static sweebook.logic.parser.CliSyntax.PREFIX_DATE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static sweebook.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static sweebook.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addTask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to specified group. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_GROUP + "GROUP "
            + PREFIX_TASKTYPE + "TASKTYPE "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_RECURRING_FREQUENCY + "RECURRING_FREQUENCY] "
            + "[" + PREFIX_PRIORITY + "PRIORITY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Project Meeting "
            + PREFIX_GROUP + "CS2101 "
            + PREFIX_TASKTYPE + "Event "
            + PREFIX_DATE + "2021-11-11 ";
    public static final String MESSAGE_RECURRING_FREQ_NO_DATE =
            "Note that any Task with recurring frequency must have a date as well!\n" + MESSAGE_USAGE;
    public static final String MESSAGE_SUCCESS = "Got it. I've added this task:\n %1$s \n\t";
    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }
}
