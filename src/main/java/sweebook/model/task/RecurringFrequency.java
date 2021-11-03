package sweebook.model.task;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

public class RecurringFrequency {
    public static final String MESSAGE_CONSTRAINTS = "Recurring frequency of task can be either week, month or year";

    public final String recurringFrequency;

    /**
     * Constructs an {@code Group}.
     *
     * @param recurringFrequency A valid recurring frequency of a task.
     */
    public RecurringFrequency(String recurringFrequency) {
        requireNonNull(recurringFrequency);
        checkArgument(isValidRecurringFrequency(recurringFrequency), MESSAGE_CONSTRAINTS);
        this.recurringFrequency = recurringFrequency.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid task type.
     */
    public static boolean isValidRecurringFrequency(String test) {
        String[] validrecurringFreqs = {"week", "month", "year", "none"};
        for (String validrecurringFreq : validrecurringFreqs) {
            if (test.equalsIgnoreCase(validrecurringFreq)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Display for show in TaskCard
     * @return "Recurring: weekly/monthly/yearly"
     */
    public String display() {
        if (recurringFrequency.equals("none")) {
            return "";
        } else {
            return "Recurring: " + recurringFrequency + "ly";
        }
    }

    @Override
    public String toString() {
        return recurringFrequency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecurringFrequency) {
            return recurringFrequency.toLowerCase().equals(((RecurringFrequency) obj).recurringFrequency.toLowerCase());
        } else {
            return false;
        }
    }

    /**
     * Determines if recurring frequency is active.
     * @return true if is recurring, false otherwise
     */
    public boolean isRecurring() {
        if (this.recurringFrequency.equals("none")) {
            return false;
        } else {
            return true;
        }
    }
}
