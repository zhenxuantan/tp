package sweebook.model.task;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Abstracts the compare method for task into a class of its own.
 */
public class SortTaskComparator implements Comparator<Task> {
    public static final String MESSAGE_CONSTRAINTS =
        "SortTaskComparator can either have 'desc', 'date', 'group' or 'pty' as PARAM in param/PARAM and"
            + "'a' (ascending) or 'd' (descending) as ORDER in o/ORDER.";

    public static final String[] PARAMETERS = {"desc", "date", "group", "pty"};
    public static final String[] ORDERS = {"a", "d"};

    private final String param;
    private final boolean isAscending;

    /**
     * Constructs a {@code SortTaskComparator}.
     *
     * @param param A valid parameter to sort the taskList.
     * @param order A valid order to sort the taskList with.
     */
    public SortTaskComparator(String param, String order) {
        requireNonNull(param);
        requireNonNull(order);
        checkArgument(isValidComparator(param, order), MESSAGE_CONSTRAINTS);
        this.param = param;
        this.isAscending = order.equals("a");
    }

    /**
     * Returns true if a given string is a valid SortTaskComparator.
     */
    public static boolean isValidComparator(String testParam, String testOrder) {
        requireNonNull(testParam);
        requireNonNull(testOrder);
        return Arrays.asList(PARAMETERS).contains(testParam)
            && (Arrays.asList(ORDERS).contains(testOrder));
    }

    @Override
    public int compare(Task task1, Task task2) {
        requireNonNull(task1);
        requireNonNull(task2);
        switch (param) {
        case "desc":
            return isAscending ? task1.compareDescription(task2) : task2.compareDescription(task1);
        case "date":
            return isAscending ? task1.compareBefore(task2) : task1.compareAfter(task2);
        case "group":
            return isAscending ? task1.compareGroup(task2) : task2.compareGroup(task1);
        case "pty":
            return isAscending ? task1.comparePriority(task2) : task2.comparePriority(task1);
        default:
            return 0;
        }
    }

    @Override
    public String toString() {
        String parameter = "";
        String orderString = isAscending ? "ascending" : "descending";
        switch (param) {
        case "desc":
            parameter = "description";
            break;
        case "date":
            parameter = "deadline / event date";
            break;
        case "group":
            parameter = "group";
            break;
        case "pty":
            parameter = "priority";
            break;
        default:
            break;
        }
        return parameter + " in " + orderString + " order.";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortTaskComparator // instanceof handles nulls
            && param.equals(((SortTaskComparator) other).param))
            && isAscending == ((SortTaskComparator) other).isAscending; // state check
    }
}
