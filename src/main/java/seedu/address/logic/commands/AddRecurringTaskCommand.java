package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class AddRecurringTaskCommand extends Command {
    public static final String COMMAND_WORD = "addRecurringTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recurring task to specified group. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_GROUP + "GROUP "
            + PREFIX_TASKTYPE + "TASKTYPE "
            + "[" + PREFIX_DATE + "DATE] "
            + PREFIX_RECURRING_FREQUENCY + "RECURRING FREQUENCY (week, month, year)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Project Meeting "
            + PREFIX_GROUP + "CS2101 "
            + PREFIX_TASKTYPE + "Event "
            + PREFIX_DATE + "2021-11-11 "
            + PREFIX_RECURRING_FREQUENCY + "week";

    public static final String MESSAGE_SUCCESS = "Got it. I've added this recurring task:\n %1$s \n\t";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddRecurringTaskCommand(Task task) {
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
