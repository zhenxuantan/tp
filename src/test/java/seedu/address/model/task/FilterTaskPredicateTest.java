package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class FilterTaskPredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterTaskPredicate(null));
    }

    @Test
    public void constructor_invalidParam_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FilterTaskPredicate("love"));
    }

    @Test
    public void isValidPredicate() {
        assertFalse(FilterTaskPredicate.isValidCriterion("")); // empty string
        assertFalse(FilterTaskPredicate.isValidCriterion(" ")); // spaces only
        assertFalse(FilterTaskPredicate.isValidCriterion("fail")); // invalid predicate

        assertTrue(FilterTaskPredicate.isValidCriterion("g/cs2101")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("g/cS2103t")); // valid, lowercase and uppercase
        assertTrue(FilterTaskPredicate.isValidCriterion("type/event")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("type/toDO")); // valid, lowercase and uppercase
        assertFalse(FilterTaskPredicate.isValidCriterion("date/2010-21-21")); // invalid date
        assertTrue(FilterTaskPredicate.isValidCriterion("date/2012-12-12")); // valid date
        assertTrue(FilterTaskPredicate.isValidCriterion("d/aaaa")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("d/AaAAa")); // valid, lowercase and uppercase
    }
}
