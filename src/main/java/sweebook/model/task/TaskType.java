package sweebook.model.task;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

public class TaskType {
    public static final String MESSAGE_CONSTRAINTS = "Type of task can be either todo or event or deadline, "
            + "and it should not be blank";

    public final String taskType;

    /**
     * Constructs a {@code Group}.
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskType) {
            return taskType.equalsIgnoreCase(((TaskType) obj).taskType);
        } else {
            return false;
        }
    }
}
