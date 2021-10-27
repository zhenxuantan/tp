package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.group.Group;
import seedu.address.model.person.social.GitHub;
import seedu.address.model.person.social.Telegram;
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Set<Group> groups = new HashSet<>(); // a person can be in 1 or 2 groups (CS2103T/CS2101)

    // Contact fields
    private final Phone phone;
    private final Email email;
    private final Telegram tg;
    private final GitHub gh;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Set<Group> group, Phone phone, Email email, Telegram tele, GitHub git) {
        requireAllNonNull(name, group, phone, email, tele, git);
        this.name = name;
        this.groups.addAll(group);
        this.phone = phone;
        this.email = email;
        this.tg = tele;
        this.gh = git;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    public Telegram getTelegram() {
        return tg;
    }

    public GitHub getGitHub() {
        return gh;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equalsIgnoreCaseAndWhiteSpaces(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getGroups().equals(getGroups())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getTelegram().equals(getTelegram())
                && otherPerson.getGitHub().equals(getGitHub());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, groups, phone, email, tg, gh);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Telegram: ")
                .append(getTelegram())
                .append("; GitHub: ")
                .append(getGitHub());

        Set<Group> groups = getGroups();
        builder.append("; Group(s): ");
        String groupsString = groups.stream()
                .map(group -> group.toString())
                .collect(Collectors.joining(", "));
        builder.append(groupsString);

        return builder.toString();
    }

}
