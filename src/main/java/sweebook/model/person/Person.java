package sweebook.model.person;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import sweebook.model.group.Group;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Telegram;

/**
 * Represents a Person in the contact list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final String MESSAGE_DUPLICATE_PERSON =
            "A person with this %s (%s) already exists in the contact list.";

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
        return unmodifiableSet(groups);
    }

    public Telegram getTelegram() {
        return tg;
    }

    public GitHub getGitHub() {
        return gh;
    }

    /**
     * Returns true if both persons are the same, defined by
     * a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && (otherPerson.getName().equalsIgnoreCaseAndWhiteSpaces(getName())
                || otherPerson.getPhone().equals(getPhone())
                || otherPerson.getEmail().value.equalsIgnoreCase(getEmail().value)
                || otherPerson.getTelegram().username.equalsIgnoreCase(getTelegram().username)
                || otherPerson.getGitHub().username.equalsIgnoreCase(getGitHub().username));
    }

    /**
     * Returns an empty string if both persons are the same, defined by
     * a weaker notion of equality between two persons. Else, returns the corresponding constraint message.
     * PRECONDITION: otherPerson returns false with this person, when used with Person#isSamePerson.
     */
    public String getSamePersonConstraintMessage(Person otherPerson) {
        requireNonNull(otherPerson);
        if (otherPerson.getName().equalsIgnoreCaseAndWhiteSpaces(getName())) {
            return String.format(MESSAGE_DUPLICATE_PERSON, "name", getName());
        }

        if (otherPerson.getPhone().equals(getPhone())) {
            return String.format(MESSAGE_DUPLICATE_PERSON, "phone number", getPhone());
        }

        if (otherPerson.getEmail().value.equalsIgnoreCase(getEmail().value)) {
            return String.format(MESSAGE_DUPLICATE_PERSON, "email address", getEmail());
        }

        if (otherPerson.getTelegram().username.equalsIgnoreCase(getTelegram().username)) {
            return String.format(MESSAGE_DUPLICATE_PERSON, "telegram username", getTelegram());
        }

        if (otherPerson.getGitHub().username.equalsIgnoreCase(getGitHub().username)) {
            return String.format(MESSAGE_DUPLICATE_PERSON, "github username", getGitHub());
        }

        assert false; // should not reach here due to precondition
        return "";
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
