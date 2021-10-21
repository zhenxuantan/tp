package seedu.address.model.task;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.function.Predicate;

import seedu.address.model.group.Group;

public class FilterTaskPredicate implements Predicate<Task> {
    public static final String MESSAGE_CONSTRAINTS =
            "FilterTaskPredicate can be either 'g/GROUP', 'date/DATE' or 'type/TASKTYPE'";

    private final String paramAndKeywords;

    /**
     * Constructs a {@code FilterTaskPredicate}.
     *
     * @param paramAndKeywords A valid parameter and keywords to filter by.
     */
    public FilterTaskPredicate(String paramAndKeywords) {
        requireNonNull(paramAndKeywords);
        checkArgument(isValidCriterion(paramAndKeywords), MESSAGE_CONSTRAINTS);
        this.paramAndKeywords = paramAndKeywords;
    }

    /**
     * Returns true if a given string is a valid FilterTaskPredicate.
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
    public boolean test(Task task) {
        requireNonNull(task);
        Character firstChar = paramAndKeywords.charAt(0);
        switch(firstChar) {
        case 'd':
            Date date = new Date(paramAndKeywords.substring(5));
            if (isNull(task.getDate())) {
                return false;
            } else {
                return task.getDate().equals(date);
            }
        case 't':
            TaskType taskType = new TaskType(paramAndKeywords.substring(5));
            return task.getTaskType().equals(taskType);
        case 'g':
            Group group = new Group(paramAndKeywords.substring(2));
            return task.getGroup().equals(group);
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FilterTaskPredicate // instanceof handles nulls
            && paramAndKeywords.equals(((FilterTaskPredicate) other).paramAndKeywords)); // state check
    }

    @Override
    public String toString() {
        return paramAndKeywords.replace("/", " criterion: ");
    }


}
