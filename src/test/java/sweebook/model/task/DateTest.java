package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class DateTest {
    //invalid date string: empty string
    private static final String INV_DATE_STRING1 = "";
    //invalid date string: incorrect format
    private static final String INV_DATE_STRING2 = "05-02-2022";
    private static final String INV_DATE_STRING3 = "2022.02.05";
    private static final String INV_DATE_STRING4 = "5 Feb 2022";
    private static final String INV_DATE_STRING5 = "5 February 2022";
    private static final String INV_DATE_STRING6 = "2022-02-5";
    private static final String INV_DATE_STRING7 = "2022-2-05";
    //invalid date string: not a date
    private static final String INV_DATE_STRING8 = "Not a date";
    //invalid date string: invalid year
    private static final String INV_DATE_STRING9 = "10000-02-05";
    //invalid date string: invalid month
    private static final String INV_DATE_STRING10 = "2022-00-05";
    private static final String INV_DATE_STRING11 = "2022-13-05";
    //invalid date string: invalid day
    private static final String INV_DATE_STRING12 = "2022-01-32";
    private static final String INV_DATE_STRING13 = "2022-02-29";
    private static final String INV_DATE_STRING14 = "2022-02-30";
    private static final String INV_DATE_STRING15 = "2022-04-00";
    private static final String INV_DATE_STRING16 = "2022-04-31";
    //valid date string
    private static final String DATE_STRING1 = "2022-01-31";
    private static final String DATE_STRING2 = "2022-04-30";
    private static final String DATE_STRING3 = "2022-02-28";
    private static final String DATE_STRING4 = "2022-02-05";
    private static final String DATE_STRING5 = "2024-02-29";
    private static final String DATE_STRING6 = "2030-01-01";
    private static final String DATE_STRING7 = "9999-12-31";

    private static final Date DATE1 = new Date(DATE_STRING1);
    private static final Date DATE2 = new Date(DATE_STRING2);
    private static final Date DATE3 = new Date(DATE_STRING3);
    private static final Date DATE4 = new Date(DATE_STRING4);
    private static final Date DATE5 = new Date(DATE_STRING5);
    private static final Date DATE6 = new Date(DATE_STRING6);
    private static final Date DATE7 = new Date(DATE_STRING7);

    private static final String[] INV_DATE_STRINGS = {INV_DATE_STRING1, INV_DATE_STRING2, INV_DATE_STRING3,
        INV_DATE_STRING4, INV_DATE_STRING5, INV_DATE_STRING6, INV_DATE_STRING7, INV_DATE_STRING8, INV_DATE_STRING9,
        INV_DATE_STRING10, INV_DATE_STRING11, INV_DATE_STRING12, INV_DATE_STRING13, INV_DATE_STRING14,
        INV_DATE_STRING15, INV_DATE_STRING16};
    private static final String[] DATE_STRINGS = {DATE_STRING1, DATE_STRING2, DATE_STRING3, DATE_STRING4,
        DATE_STRING5, DATE_STRING6, DATE_STRING7};
    private static final Date[] DATES = {DATE1, DATE2, DATE3, DATE4, DATE5, DATE6, DATE7};

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        Arrays.stream(INV_DATE_STRINGS).forEach(str ->
            assertThrows(IllegalArgumentException.class, () -> new Date(str)));
    }

    @Test
    void isValidDate() {
        //valid dates, as the product is released on 2021,
        //there is no point testing dates before 2021 even if they are accepted by SWEe-book
        Arrays.stream(DATE_STRINGS).forEach(str -> assertTrue(Date.isValidDate(str)));
        Arrays.stream(INV_DATE_STRINGS).forEach(str -> assertFalse(Date.isValidDate(str)));
    }

    @Test
    void testToString() {
        String[] formattedDateStrings = {"Jan 31 2022", "Apr 30 2022", "Feb 28 2022",
            "Feb 05 2022", "Feb 29 2024", "Jan 01 2030", "Dec 31 9999"};
        assertArrayEquals(formattedDateStrings, Arrays.stream(DATES).map(Date::toString).toArray());
    }

    @Test
    void getLocalDate() {
        LocalDate[] localDates = {LocalDate.of(2022, 1, 31), LocalDate.of(2022, 4, 30),
            LocalDate.of(2022, 2, 28), LocalDate.of(2022, 2, 5), LocalDate.of(2024, 2, 29),
            LocalDate.of(2030, 1, 1), LocalDate.of(9999, 12, 31)};
        assertArrayEquals(localDates, Arrays.stream(DATES).map(Date::getLocalDate).toArray());
    }

    @Test
    void getString() {
        assertArrayEquals(DATE_STRINGS, Arrays.stream(DATES).map(Date::getString).toArray());
    }

    @Test
    void testEquals() {
        assertArrayEquals(Arrays.stream(DATE_STRINGS).map(Date::new).toArray(), DATES);
        assertNotEquals(DATE2, DATE1);
    }

    @Test
    void compareTo() {
        assertTrue(DATE1.compareTo(DATE7) < 0);
        assertTrue(DATE6.compareTo(DATE2) > 0);
        assertEquals(0, DATE3.compareTo(new Date(DATE_STRING3)));
    }

    @Test
    void isLastWeek() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        int differenceFromMon = today.getDayOfWeek().getValue() - 1;

        Date tomorrow = new Date(dtf.format(today.plusDays(1)));
        Date todayDate = new Date(dtf.format(today));
        Date thisWeekMon = new Date(dtf.format(today.minusDays(differenceFromMon)));

        Date lastWeekSun = new Date(dtf.format(today.minusDays(differenceFromMon + 1)));
        Date lastWeek = new Date(dtf.format(today.minusWeeks(1)));
        Date lastMonth = new Date(dtf.format(today.minusMonths(1)));
        Date lastYear = new Date(dtf.format(today.minusYears(1)));

        assertFalse(tomorrow.isLastWeek());
        assertFalse(todayDate.isLastWeek());
        assertFalse(thisWeekMon.isLastWeek());

        assertTrue(lastWeekSun.isLastWeek());
        assertTrue(lastWeek.isLastWeek());
        assertTrue(lastMonth.isLastWeek());
        assertTrue(lastYear.isLastWeek());
    }

    @Test
    void isLastMonth() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        int differenceFromFirstDayOfMonth = today.getDayOfMonth() - 1;

        Date tomorrow = new Date(dtf.format(today.plusDays(1)));
        Date todayDate = new Date(dtf.format(today));
        Date firstDayOfSameMonth = new Date(dtf.format(today.minusDays(differenceFromFirstDayOfMonth)));

        Date lastDayOfPreviousMonth = new Date(dtf.format(today.minusDays(differenceFromFirstDayOfMonth + 1)));
        Date lastMonth = new Date(dtf.format(today.minusMonths(1)));
        Date lastYear = new Date(dtf.format(today.minusYears(1)));

        assertFalse(tomorrow.isLastMonth());
        assertFalse(todayDate.isLastMonth());
        assertFalse(firstDayOfSameMonth.isLastMonth());

        assertTrue(lastDayOfPreviousMonth.isLastMonth());
        assertTrue(lastMonth.isLastMonth());
        assertTrue(lastYear.isLastMonth());
    }

    @Test
    void isLastYear() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        int differenceFromFirstDayOfYear = today.getDayOfYear() - 1;

        Date tomorrow = new Date(dtf.format(today.plusDays(1)));
        Date todayDate = new Date(dtf.format(today));
        Date firstDayOfSameYear = new Date(dtf.format(today.minusDays(differenceFromFirstDayOfYear)));

        Date lastDayOfLastYear = new Date(dtf.format(today.minusDays(differenceFromFirstDayOfYear + 1)));
        Date lastYear = new Date(dtf.format(today.minusYears(1)));

        assertFalse(tomorrow.isLastYear());
        assertFalse(todayDate.isLastYear());
        assertFalse(firstDayOfSameYear.isLastYear());

        assertTrue(lastDayOfLastYear.isLastYear());
        assertTrue(lastYear.isLastYear());
    }

    @Test
    void getDateForThisWeek() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        int differenceOfTodayFromMon = today.getDayOfWeek().getValue() - 1;
        Date thisWeekMon = new Date(dtf.format(today.minusDays(differenceOfTodayFromMon)));

        for (Date date: DATES) {
            int differenceOfDateFromMon = date.getLocalDate().getDayOfWeek().getValue() - 1;
            Date thisWeek = new Date(dtf.format(thisWeekMon.getLocalDate().plusDays(differenceOfDateFromMon)));
            assertEquals(thisWeek, date.getDateForThisWeek());
        }
    }
}
