package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.task.SortTaskCriterion.ORDERS;
import static seedu.address.model.task.SortTaskCriterion.PARAMETERS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortTaskCriterionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortTaskCriterion(null, null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortTaskCriterion("love", "a"));
    }

    @Test
    public void constructor_invalidOrder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortTaskCriterion("desc", "f"));
    }

    @Test
    public void isValidCriterion() {
        // null name
        assertThrows(NullPointerException.class, () -> SortTaskCriterion.isValidCriterion(null, null));

        // invalid param
        assertFalse(SortTaskCriterion.isValidCriterion("", "a")); // empty string
        assertFalse(SortTaskCriterion.isValidCriterion(" ", "a")); // spaces only
        assertFalse(SortTaskCriterion.isValidCriterion("fail", "a")); // invalid param

        // invalid order
        assertFalse(SortTaskCriterion.isValidCriterion("desc", "")); // empty string
        assertFalse(SortTaskCriterion.isValidCriterion("desc", " ")); // spaces only
        assertFalse(SortTaskCriterion.isValidCriterion("desc", "fail")); // invalid param

        // valid param and order
        for (String param: PARAMETERS) {
            for (String order: ORDERS) {
                assertTrue(SortTaskCriterion.isValidCriterion(param, order));
            }
        }
    }
}
