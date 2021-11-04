package sweebook.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sweebook.testutil.PersonBuilder;

public class GroupPredicateTest {
    @Test
    public void equals() {
        Group firstGroup = new Group("CS2103T");
        Group secondGroup = new Group("CS2101");

        GroupPredicate firstPredicate = new GroupPredicate(firstGroup);
        GroupPredicate secondPredicate = new GroupPredicate(secondGroup);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupPredicate firstPredicateCopy = new GroupPredicate(firstGroup);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personInGroup_returnsTrue() {
        GroupPredicate predicate = new GroupPredicate(new Group("CS2103T"));

        // Person with one matching group
        assertTrue(predicate.test(new PersonBuilder().withGroups("CS2103T").build()));

        // Person with two groups and one matching group
        assertTrue(predicate.test(new PersonBuilder().withGroups("CS2103T", "CS2101").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Person with no matching group
        GroupPredicate predicate = new GroupPredicate(new Group("CS2103T"));
        assertFalse(predicate.test(new PersonBuilder().withGroups("CS2101").build()));
    }
}
