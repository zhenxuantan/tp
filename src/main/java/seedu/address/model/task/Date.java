package seedu.address.model.task;

import javafx.application.Application;
import seedu.address.MainApp;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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

    public LocalDate getLocalDate(){ return this.date; }

    public int compare(Date date){
        return this.date.compareTo(date.getLocalDate());
    }

    /**
     *
     * @return String representation of date , i.e. "YYYY-MM-DD"
     */
    public String getString(){
        return this.dateString;
    }


}
