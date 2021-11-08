package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.model.task.SortTaskComparator.ORDERS;
import static sweebook.model.task.SortTaskComparator.PARAMETERS;
import static sweebook.testutil.Assert.assertThrows;
import static sweebook.testutil.TypicalTasks.FIRST_DEADLINE;
import static sweebook.testutil.TypicalTasks.FIRST_EVENT;

import org.junit.jupiter.api.Test;

public class SortTaskComparatorTest {

    private static final SortTaskComparator STC_DESC_A = new SortTaskComparator("desc", "a");
    private static final SortTaskComparator STC_DESC_D = new SortTaskComparator("desc", "d");
    private static final SortTaskComparator STC_DATE_A = new SortTaskComparator("date", "a");
    private static final SortTaskComparator STC_DATE_D = new SortTaskComparator("date", "d");
    private static final SortTaskComparator STC_GROUP_A = new SortTaskComparator("group", "a");
    private static final SortTaskComparator STC_GROUP_D = new SortTaskComparator("group", "d");
    private static final SortTaskComparator STC_PTY_A = new SortTaskComparator("pty", "a");
    private static final SortTaskComparator STC_PTY_D = new SortTaskComparator("pty", "d");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortTaskComparator(null, null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortTaskComparator("love", "a"));
    }

    @Test
    public void constructor_invalidOrder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortTaskComparator("desc", "f"));
    }

    @Test
    public void isValidComparator() {
        // null name
        assertThrows(NullPointerException.class, () -> SortTaskComparator.isValidComparator(null, null));

        // invalid param
        assertFalse(SortTaskComparator.isValidComparator("", "a")); // empty string
        assertFalse(SortTaskComparator.isValidComparator(" ", "a")); // spaces only
        assertFalse(SortTaskComparator.isValidComparator("fail", "a")); // invalid param

        // invalid order
        assertFalse(SortTaskComparator.isValidComparator("desc", "")); // empty string
        assertFalse(SortTaskComparator.isValidComparator("desc", " ")); // spaces only
        assertFalse(SortTaskComparator.isValidComparator("desc", "fail")); // invalid param

        // valid param and order
        for (String param: PARAMETERS) {
            for (String order: ORDERS) {
                assertTrue(SortTaskComparator.isValidComparator(param, order));
            }
        }
    }

    @Test
    public void compare() {
        assertTrue(STC_DESC_A.compare(FIRST_DEADLINE, FIRST_EVENT) < 0);
        assertTrue(STC_DESC_D.compare(FIRST_DEADLINE, FIRST_EVENT) > 0);
        assertTrue(STC_DATE_A.compare(FIRST_DEADLINE, FIRST_EVENT) < 0);
        assertTrue(STC_DATE_D.compare(FIRST_DEADLINE, FIRST_EVENT) > 0);
        assertTrue(STC_GROUP_A.compare(FIRST_DEADLINE, FIRST_EVENT) < 0);
        assertTrue(STC_GROUP_D.compare(FIRST_DEADLINE, FIRST_EVENT) > 0);
        assertTrue(STC_PTY_A.compare(FIRST_DEADLINE, FIRST_EVENT) > 0);
        assertTrue(STC_PTY_D.compare(FIRST_DEADLINE, FIRST_EVENT) < 0);
    }

    @Test
    public void testToString() {
        assertEquals("description in ascending order.", STC_DESC_A.toString());
        assertEquals("description in descending order.", STC_DESC_D.toString());
        assertEquals("deadline / event date in ascending order.", STC_DATE_A.toString());
        assertEquals("deadline / event date in descending order.", STC_DATE_D.toString());
        assertEquals("group in ascending order.", STC_GROUP_A.toString());
        assertEquals("group in descending order.", STC_GROUP_D.toString());
        assertEquals("priority in ascending order.", STC_PTY_A.toString());
        assertEquals("priority in descending order.", STC_PTY_D.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(STC_DESC_A, STC_DESC_A);
        assertEquals(new SortTaskComparator("desc", "a"), STC_DESC_A);
        assertNotEquals(new SortTaskComparator("desc", "d"), STC_DESC_A);
        assertNotEquals(new SortTaskComparator("date", "a"), STC_DESC_A);
    }
}
