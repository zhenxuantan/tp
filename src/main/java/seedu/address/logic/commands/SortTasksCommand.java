package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAMETER;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.SortTaskCriterion;
import seedu.address.model.task.Task;

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

    public static final String MESSAGE_SUCCESS = "Got it. I've sorted the list.";

    private SortTaskCriterion toSort;

    /**
     * Constructor for SortTasksCommand
     * @param sortTaskCriterion the criterion in which the tasks are going to be sorted by.
     */
    public SortTasksCommand(SortTaskCriterion sortTaskCriterion) {
        requireNonNull(sortTaskCriterion);
        this.toSort = sortTaskCriterion;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder returnMessage = new StringBuilder("Your sorted tasks: \n");
        requireNonNull(model);
        ObservableList<Task> sortedTaskList = model.sortTask(toSort);
        for (Task t : sortedTaskList) {
            returnMessage.append(t.toString()).append("\n");
        }
        return new CommandResult(returnMessage.toString());
    }
}
