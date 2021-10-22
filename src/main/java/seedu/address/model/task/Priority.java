package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

public class Priority {
    public static final String MESSAGE_CONSTRAINTS = "Priority should be low, med or high.";
    public static final String[] PRIORITIES = new String[]{"low", "med", "high"};

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
        this.priority = priority;
        switch (priority) {
        case "low":
            priorityInt = 1;
            break;
        case "high":
            priorityInt = 3;
            break;
        default:
            priorityInt = 2;
        }
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        try {
            return Arrays.stream(PRIORITIES).anyMatch(p -> p.equals(test));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return priority;
    }

    public int compareTo(Priority priority) {
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
        if (obj instanceof Priority) {
            return priorityInt == ((Priority) obj).getPriorityInt();
        } else {
            return false;
        }
    }

    public String getPriorityIcon() {
        switch (priority) {
        case "low":
            return "!";
        case "med":
            return "!!";
        default:
            return "!!!";
        }
    }

}
