package sweebook.model.group;

import java.util.function.Predicate;

import sweebook.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Group} matches the specified group.
 */
public class GroupPredicate implements Predicate<Person> {
    private Group group;

    public GroupPredicate(Group group) {
        this.group = group;
    }

    @Override
    public boolean test(Person person) {
        return person.getGroups().contains(group);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupPredicate // instanceof handles nulls
                && group.equals(((GroupPredicate) other).group)); // state check
    }

}
