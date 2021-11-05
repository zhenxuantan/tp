package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.model.task.SortTaskComparator.ORDERS;
import static sweebook.model.task.SortTaskComparator.PARAMETERS;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortTaskComparatorTest {

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
}
