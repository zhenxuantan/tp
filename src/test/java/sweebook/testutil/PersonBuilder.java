package sweebook.testutil;

import java.util.HashSet;
import java.util.Set;

import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Telegram;
import sweebook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_GROUP_CS2101 = "CS2101";
    public static final String DEFAULT_GROUP_CS22103T = "CS2103T";
    public static final String DEFAULT_TELEGRAM = "amybee";
    public static final String DEFAULT_GITHUB = "amybee";

    private Name name;
    private Set<Group> groups;
    private Phone phone;
    private Email email;
    private Telegram tele;
    private GitHub git;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        groups = SampleDataUtil.getGroupSet(DEFAULT_GROUP_CS2101, DEFAULT_GROUP_CS22103T);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tele = new Telegram(DEFAULT_TELEGRAM);
        git = new GitHub(DEFAULT_GITHUB);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        groups = new HashSet<>(personToCopy.getGroups());
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        tele = personToCopy.getTelegram();
        git = personToCopy.getGitHub();

    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code Person} that we are building.
     */
    public PersonBuilder withGroups(String... groups) {
        this.groups = SampleDataUtil.getGroupSet(groups);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String username) {
        this.tele = new Telegram(username);
        return this;
    }

    /**
     * Sets the {@code GitHub} of the {@code Person} that we are building.
     */
    public PersonBuilder withGitHub(String username) {
        this.git = new GitHub(username);
        return this;
    }


    public Person build() {
        return new Person(name, groups, phone, email, tele, git);
    }

}
