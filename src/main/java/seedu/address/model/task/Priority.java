package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Priority {
    public static final String MESSAGE_CONSTRAINTS = "Priority should be 1, 2 or 3.";

    public final String priority;
    public final int priorityInt;

    /**
     * Constructs an {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.priorityInt = Integer.parseInt(priority);
        switch (priorityInt) {
        case 1:
            this.priority = "low";
            break;
        case 3:
            this.priority = "high";
            break;
        default:
            this.priority = "med";
        }
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        try {
            int i = Integer.parseInt(test);
            return i > 0 && i < 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return priority;
    }

    public int compare(Priority priority) {
        return Integer.compare(this.priorityInt, (priority.getPriorityInt())); }

    /**
     *
     * @return Integer representation of priority.
     */
    public int getPriorityInt() {
        return this.priorityInt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            return priorityInt == ((Priority) obj).getPriorityInt();
        } else {
            return false;
        }
    }


}
