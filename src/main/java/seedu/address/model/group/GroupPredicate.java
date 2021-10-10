package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class GroupPredicate implements Predicate<Person> {
    private Group group;

    public GroupPredicate(Group group) {
        this.group = group;
    }

    @Override
    public boolean test(Person person) {
        return person.getGroup().equals(group);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupPredicate // instanceof handles nulls
                && group.equals(((GroupPredicate) other).group)); // state check
    }

}
