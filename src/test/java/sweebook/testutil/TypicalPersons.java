package sweebook.testutil;

import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_AMY_CS2101;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_AMY_CS2103T;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sweebook.model.ContactList;
import sweebook.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withGroups("CS2103T", "CS2101")
            .withPhone("94351253")
            .withEmail("alice@example.com")
            .withTelegram("alice")
            .withGitHub("alice")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withGroups("CS2103T")
            .withPhone("98765432")
            .withEmail("johnd@example.com")
            .withTelegram("Bensonmeirer")
            .withGitHub("benson")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withGroups("cs2101", "cs2103t")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withTelegram("carl")
            .withGitHub("carl")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withGroups("Cs2101")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withTelegram("Daniel")
            .withGitHub("daniel")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withGroups("CS2101", "cs2103t")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withTelegram("elle")
            .withGitHub("elle")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withGroups("CS2103T")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withTelegram("lydia")
            .withGitHub("lydia")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withGroups("CS2103T")
            .withPhone("9482442")
            .withEmail("george@example.com")
            .withTelegram("george")
            .withGitHub("george")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withGroups("CS2103T")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withTelegram("STEFAN")
            .withGitHub("stefan")
            .build();
    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withGroups("CS2103T")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withTelegram("hans")
            .withGitHub("hans")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withGroups(VALID_GROUP_AMY_CS2103T, VALID_GROUP_AMY_CS2101)
            .withTelegram(VALID_TELEGRAM_AMY).withGitHub(VALID_GITHUB_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withGroups(VALID_GROUP_BOB).withTelegram(VALID_TELEGRAM_BOB).withGitHub(VALID_GITHUB_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code ContactList} with all the typical persons.
     */
    public static ContactList getTypicalContactList() {
        ContactList ab = new ContactList();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
