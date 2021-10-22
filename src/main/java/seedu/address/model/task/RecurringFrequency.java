package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RecurringFrequency {
    public static final String MESSAGE_CONSTRAINTS = "Recurring frequency of task is of the following formats: \n"
            + "week DAY or month MM or year MM-DD";

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
     * Returns true if a given string is a valid recurring freqency.
     */
    public static boolean isValidRecurringFrequency(String test) {
        String[] testSplit = test.split(" ");
        if (testSplit[0].equalsIgnoreCase("none")) {
            return true;
        }
        if (testSplit.length != 2) {
            return false;
        }
        String[] validrecurringFreqs = {"week", "month", "year"};
        if (validrecurringFreqs[0].equalsIgnoreCase(testSplit[0])) {
            return isValidWeeklyRecurringFrequency(testSplit[1]);
        } else if (validrecurringFreqs[1].equalsIgnoreCase(testSplit[0])) {
            return isValidMonthlyRecurringFrequency(testSplit[1]);
        } else if (validrecurringFreqs[2].equalsIgnoreCase(testSplit[0])) {
            return isValidYearlyRecurringFrequency(testSplit[1]);
        } else {
            return true;
        }
    }

    /**
     * Returns true if a given string is a valid weekly recurring frequency.
     * @param test Given string
     * @return True if valid, false otherwise
     */
    public static boolean isValidWeeklyRecurringFrequency(String test) {
        String[] validWeekValues = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        for (String day : validWeekValues) {
            if (day.equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid monthly recurring frequency.
     * @param test Given string
     * @return True if valid, false otherwise
     */
    public static boolean isValidMonthlyRecurringFrequency(String test) {
        int i;
        try {
            i = Integer.parseInt(test);
            if (i > 0 && i <= 31) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid yearly recurring frequency.
     * @param test Given string
     * @return True if valid, false otherwise
     */
    public static boolean isValidYearlyRecurringFrequency(String test) {
        String toTest = "2020-" + test;
        try {
            LocalDate date = LocalDate.parse(toTest);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Display for show in TaskCard
     * @return "Recurring: weekly/monthly/yearly"
     */
    public String display() {
        if (recurringFrequency.equals("none")) {
            return "";
        } else {
            String[] split = recurringFrequency.split(" ");
            return "Recurring: " + split[0] + "ly on " + split[1];
        }
    }

    @Override
    public String toString() {
        return recurringFrequency;
    }

    /**
     * Gets either "week", "month", "year" or "none".
     * @return "week", "month", "year" or "none"
     */
    public String getFirstString() {
        return recurringFrequency.split(" ")[0].toLowerCase();
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
