package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in YYYY-MM-DD, and it should not be blank";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public final LocalDate date;
    public final String dateString;


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
        if (date.equals(null)) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    public LocalDate getLocalDate() {
        return this.date;
    }

    public int compare(Date date) {
        return this.date.compareTo(date.getLocalDate()); }

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
            return new Date(monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case TUESDAY:
            return new Date(monday.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case WEDNESDAY:
            return new Date(monday.plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case THURSDAY:
            return new Date(monday.plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case FRIDAY:
            return new Date(monday.plusDays(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case SATURDAY:
            return new Date(monday.plusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        case SUNDAY:
            return new Date(monday.plusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        default:
            return null;
        }
    }

    /**
     * Get the day for this current date and return the same day for the current week.
     * @return Date with the same day as current instance but for the current week.
     */
    public Date getDateForThisMonth() {
        LocalDate today = LocalDate.now();
        int todayMonth = today.getMonthValue();
        int month = this.date.getMonthValue();
        return new Date(this.date.plusMonths(todayMonth - month).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Get the day for this current date and return the same day for the current week.
     * @return Date with the same day as current instance but for the current week.
     */
    public Date getDateForThisYear() {
        LocalDate today = LocalDate.now();
        int todayYear = today.getYear();
        int year = this.date.getYear();
        return new Date(this.date.plusYears(todayYear - year).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

}
