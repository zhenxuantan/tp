package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskRecords;
import seedu.address.model.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTasksCommand extends Command {
    public static final String COMMAND_WORD = "sortTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to given parameter " +
        "and order (a: ascending, d: descending).\n"
        + "Parameters: "
        + PREFIX_PARAMETER + "PARAMETER "
        + PREFIX_ORDER + "ORDER\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PARAMETER + "d "
        + PREFIX_ORDER + "a\n"
        + "Sorts the tasks by description in ascending order (lexicographical / chronological order)";

    public static final String MESSAGE_SUCCESS = "Got it. I've sorted the list.";

    private final String PARAM;
    private final boolean ASCENDING;

    public SortTasksCommand(String param, String order) {
        requireNonNull(param);
        requireNonNull(order);
        this.PARAM = param;
        this.ASCENDING = order.equals("a");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder returnMessage = new StringBuilder("Your sorted tasks: \n");
        requireNonNull(model);
        ReadOnlyTaskRecords taskRecords = model.getTaskList();
        ObservableList<Task> taskList = taskRecords.getTaskList();
        List<Task> modifiableList = new ArrayList<Task>(taskList);

        switch (this.PARAM) {
            case "d":
                modifiableList.sort((t1, t2) -> this.ASCENDING ? t1.compareDescription(t2) : t2.compareDescription(t1));
                break;
            case "date":
                modifiableList.sort((t1, t2) -> this.ASCENDING ? t1.compareDate(t2) : t2.compareDate(t1));
                break;
            case "added":
                if (!this.ASCENDING) Collections.reverse(modifiableList);
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
