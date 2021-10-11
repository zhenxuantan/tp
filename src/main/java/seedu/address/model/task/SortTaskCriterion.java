package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

public class SortTaskCriterion {
    public static final String MESSAGE_CONSTRAINTS =
        "SortTaskCriterion can either have 'desc', 'date', 'added' or 'group' as PARAM in param/PARAM and"
            + "'a' (ascending) or 'd' (descending) as ORDER in o/ORDER.";

    public static final String[] PARAMETERS = {"desc", "date", "added", "group"};
    public static final String[] ORDERS = {"a", "d"};


    private final String param;
    private final boolean ascending;

    /**
     * Constructs a {@code SortTaskCriterion}.
     *
     * @param param A valid parameter to sort the taskList.
     * @param order A valid order to sort the taskList with.
     */
    public SortTaskCriterion(String param, String order) {
        requireNonNull(param);
        requireNonNull(order);
        checkArgument(isValidCriterion(param, order), MESSAGE_CONSTRAINTS);
        this.param = param;
        this.ascending = order.equals("a");
    }

    /**
     * Returns true if a given string is a valid SortTaskCriterion.
     */
    public static boolean isValidCriterion(String testParam, String testOrder) {
        requireNonNull(testParam);
        requireNonNull(testOrder);
        return Arrays.asList(PARAMETERS).contains(testParam)
            && (Arrays.asList(ORDERS).contains(testOrder));
    }

    @Override
    public String toString() {
        return "param: " + param + ", order: " + ascending;
    }

    public String getParam() {
        return this.param;
    }

    public boolean getAscending() {
        return this.ascending;
    }
}
