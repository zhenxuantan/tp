package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TaskType {
    public static final String MESSAGE_CONSTRAINTS = "Type of task can be either todo or event or deadline, "
            + "and it should not be blank";

    public final String taskType;

    /**
     * Constructs an {@code Group}.
     *
     * @param taskType A valid taskType.
     */
    public TaskType(String taskType) {
        requireNonNull(taskType);
        checkArgument(isValidTaskType(taskType), MESSAGE_CONSTRAINTS);
        this.taskType = taskType.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid task type.
     */
    public static boolean isValidTaskType(String test) {
        String[] validTaskTypes = {"todo", "event", "deadline"};
        for (String validTaskType : validTaskTypes) {
            if (test.equalsIgnoreCase(validTaskType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return taskType;
    }
}
