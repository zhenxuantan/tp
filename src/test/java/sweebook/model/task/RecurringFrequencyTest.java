package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class RecurringFrequencyTest {

    private static final RecurringFrequency NONE = new RecurringFrequency("none");
    private static final RecurringFrequency WEEK = new RecurringFrequency("week");
    private static final RecurringFrequency MONTH = new RecurringFrequency("month");
    private static final RecurringFrequency YEAR = new RecurringFrequency("year");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecurringFrequency(null));
    }

    @Test
    public void constructor_invalidRecurringFrequency_throwsIllegalArgumentException() {
        String invalidRecurringFrequency1 = "";
        String invalidRecurringFrequency2 = "extreme";
        assertThrows(IllegalArgumentException.class, () -> new RecurringFrequency(invalidRecurringFrequency1));
        assertThrows(IllegalArgumentException.class, () -> new RecurringFrequency(invalidRecurringFrequency2));
    }

    @Test
    void display() {
        assertEquals("", NONE.display());
        assertEquals("Recurring: weekly", WEEK.display());
        assertEquals("Recurring: monthly", MONTH.display());
        assertEquals("Recurring: yearly", YEAR.display());
    }

    @Test
    void isRecurring() {
        assertFalse(NONE.isRecurring());
        assertTrue(WEEK.isRecurring());
        assertTrue(MONTH.isRecurring());
        assertTrue(YEAR.isRecurring());
    }
}
