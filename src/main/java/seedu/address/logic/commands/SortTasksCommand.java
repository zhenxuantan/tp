package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAMETER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskRecords;
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

    private String param;
    private boolean ascending;

    /**
     * Constructor for SortTasksCommand
     * @param param The parameter in which the tasks are to be sorted.
     * @param order The order in which the tasks are to be sorted.
     */
    public SortTasksCommand(String param, String order) {
        requireNonNull(param);
        requireNonNull(order);
        this.param = param;
        this.ascending = order.equals("a");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder returnMessage = new StringBuilder("Your sorted tasks: \n");
        requireNonNull(model);
        ReadOnlyTaskRecords taskRecords = model.getTaskList();
        ObservableList<Task> taskList = taskRecords.getTaskList();
        List<Task> modifiableList = new ArrayList<Task>(taskList);

        switch (this.param) {
        case "d":
            modifiableList.sort((t1, t2) -> this.ascending ? t1.compareDescription(t2) : t2.compareDescription(t1));
            break;
        case "date":
            modifiableList.sort((t1, t2) -> this.ascending ? t1.compareDate(t2) : t2.compareDate(t1));
            break;
        case "added":
            if (!this.ascending) {
                Collections.reverse(modifiableList);
            }
            break;
        default:
            break;
        }

        for (Task t : modifiableList) {
            returnMessage.append(t.toString()).append("\n");
        }
        return new CommandResult(returnMessage.toString());
    }
}
