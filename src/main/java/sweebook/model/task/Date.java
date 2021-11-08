package sweebook.model.task;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in YYYY-MM-DD, and it should not be blank";
    public static final DateTimeFormatter DTF_STORAGE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    public static final DateTimeFormatter DTF_COMMAND = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate date;
    private final String dateString;


    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
        this.dateString = date;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date.format(DTF_STORAGE);
    }

    public LocalDate getLocalDate() {
        return this.date;
    }

    /**
     *
     * @return String representation of date , i.e. "YYYY-MM-DD"
     */
    public String getString() {
        return this.dateString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            return date.equals(((Date) obj).date);
        } else {
            return false;
        }
    }

    public int compareTo(Date otherDate) {
        return date.compareTo(otherDate.date);
    }

    /**
     * Checks if current date is from last week (or even before that).
     * @return true if date is from last week, false otherwise
     */
    public boolean isLastWeek() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        if (this.date.compareTo(monday) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if current date is from last month (or even before that).
     * @return true if date is from last month, false otherwise
     */
    public boolean isLastMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        if (this.date.compareTo(firstDayOfMonth) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if current date is from last year (or even before that).
     * @return true if date is from last year, false otherwise
     */
    public boolean isLastYear() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);
        if (this.date.compareTo(firstDayOfYear) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the day for this current date and return the same day for the current week.
     * @return Date with the same day as current instance but for the current week.
     */
    public Date getDateForThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        switch (date.getDayOfWeek()) {
        case MONDAY:
            return new Date(monday.format(DTF_COMMAND));
        case TUESDAY:
            return new Date(monday.plusDays(1).format(DTF_COMMAND));
        case WEDNESDAY:
            return new Date(monday.plusDays(2).format(DTF_COMMAND));
        case THURSDAY:
            return new Date(monday.plusDays(3).format(DTF_COMMAND));
        case FRIDAY:
            return new Date(monday.plusDays(4).format(DTF_COMMAND));
        case SATURDAY:
            return new Date(monday.plusDays(5).format(DTF_COMMAND));
        case SUNDAY:
            return new Date(monday.plusDays(6).format(DTF_COMMAND));
        default:
            return null;
        }
    }

    /**
     * Get the day for this current date and return the same day for the current month.
     * @return Date with the same day as current instance but for the current month.
     */
    public Date getDateForThisMonth() {
        LocalDate today = LocalDate.now();
        int todayMonth = today.getMonthValue();
        int month = this.date.getMonthValue();
        return new Date(this.date.plusMonths(todayMonth - month).format(DTF_COMMAND));
    }

    /**
     * Get the day for this current date and return the same day for the current year.
     * @return Date with the same day as current instance but for the current year.
     */
    public Date getDateForThisYear() {
        LocalDate today = LocalDate.now();
        int todayYear = today.getYear();
        int year = this.date.getYear();
        return new Date(this.date.plusYears(todayYear - year).format(DTF_COMMAND));
    }

}
