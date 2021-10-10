package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.group.Group;

public class FilterTaskCriterion {
    public static final String MESSAGE_CONSTRAINTS =
            "FilterTaskCriterion can be either 'g/GROUP', 'date/DATE' or 'type/TASKTYPE'";

    public final String filterTaskCriterion;

    /**
     * Constructs a {@code FilterTaskCriterion}.
     *
     * @param filterTaskCriterion A valid FilterTaskCriterion.
     */
    public FilterTaskCriterion(String filterTaskCriterion) {
        requireNonNull(filterTaskCriterion);
        checkArgument(isValidCriterion(filterTaskCriterion), MESSAGE_CONSTRAINTS);
        this.filterTaskCriterion = filterTaskCriterion;
    }

    /**
     * Returns true if a given string is a valid FilterTaskCriterion.
     */
    public static boolean isValidCriterion(String test) {
        boolean isValid;
        Character firstChar = test.charAt(0);
        switch(firstChar) {
        case 'd':
            isValid = Date.isValidDate(test.substring(5));
            break;
        case 't':
            isValid = TaskType.isValidTaskType(test.substring(5));
            break;
        case 'g':
            isValid = Group.isValidGroup(test.substring(2));
            break;
        default:
            isValid = false;
            break;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return filterTaskCriterion;
    }

    public static void main(String[] args) {
        System.out.println(FilterTaskCriterion.isValidCriterion("date/1212-12-12"));
    }

}
