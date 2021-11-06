package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class FilterTaskPredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterTaskPredicate(null));
    }

    @Test
    public void isValidPredicate() {
        assertFalse(FilterTaskPredicate.isValidCriterion("")); // empty string
        assertFalse(FilterTaskPredicate.isValidCriterion(" ")); // spaces only
        assertFalse(FilterTaskPredicate.isValidCriterion("fail")); // invalid predicate
        assertFalse(FilterTaskPredicate.isValidCriterion(null)); // null

        assertTrue(FilterTaskPredicate.isValidCriterion("g/cs2101")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("g/cS2103t")); // valid, lowercase and uppercase
        assertTrue(FilterTaskPredicate.isValidCriterion("type/event")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("type/toDO")); // valid, lowercase and uppercase
        assertFalse(FilterTaskPredicate.isValidCriterion("date/2010-21-21")); // invalid date
        assertTrue(FilterTaskPredicate.isValidCriterion("date/2012-12-12")); // valid date
        assertTrue(FilterTaskPredicate.isValidCriterion("d/aaaa")); // valid, lowercase
        assertTrue(FilterTaskPredicate.isValidCriterion("d/AaAAa")); // valid, lowercase and uppercase
        assertFalse(FilterTaskPredicate.isValidCriterion("pty/MeD")); // invalid, contains uppercase
        assertTrue(FilterTaskPredicate.isValidCriterion("pty/med")); // valid, lowercase and uppercase
    }

    @Test
    public void equals() {
        final FilterTaskPredicate date = new FilterTaskPredicate("date/2021-11-11");
        final FilterTaskPredicate pty = new FilterTaskPredicate("pty/high");
        final FilterTaskPredicate desc = new FilterTaskPredicate("d/swimming");
        final FilterTaskPredicate type = new FilterTaskPredicate("type/deadline");
        final FilterTaskPredicate group = new FilterTaskPredicate("g/CS2103T");

        // same object -> returns true
        assertTrue(date.equals(date));
        assertTrue(pty.equals(pty));
        assertTrue(desc.equals(desc));
        assertTrue(type.equals(type));
        assertTrue(group.equals(group));

        // same value -> returns true
        assertTrue(date.equals(new FilterTaskPredicate("date/2021-11-11")));
        assertTrue(pty.equals(new FilterTaskPredicate("pty/high")));
        assertTrue(desc.equals(new FilterTaskPredicate("d/swimming")));
        assertTrue(type.equals(new FilterTaskPredicate("type/deadline")));
        assertTrue(group.equals(new FilterTaskPredicate("g/CS2103T")));

        // null -> returns false
        assertFalse(date.equals(null));
        assertFalse(pty.equals(null));
        assertFalse(desc.equals(null));
        assertFalse(type.equals(null));
        assertFalse(group.equals(null));

        // different types -> returns false
        assertFalse(date.equals(pty));
        assertFalse(desc.equals(type));
        assertFalse(date.equals(group));

        // different value -> returns false
        assertFalse(date.equals(new FilterTaskPredicate("date/2021-10-11")));
        assertFalse(pty.equals(new FilterTaskPredicate("pty/low")));
        assertFalse(desc.equals(new FilterTaskPredicate("d/swim")));
        assertFalse(type.equals(new FilterTaskPredicate("type/event")));
        assertFalse(group.equals(new FilterTaskPredicate("g/CS2101")));
    }
}
