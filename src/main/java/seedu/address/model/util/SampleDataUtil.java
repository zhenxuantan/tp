package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskRecords;
import seedu.address.model.TaskRecords;
import seedu.address.model.group.Group;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.social.GitHub;
import seedu.address.model.person.social.Telegram;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), getGroupSet("CS2103T"),
                    new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Telegram("alexyeoh"), new GitHub("alexyeoh")),
            new Person(new Name("Bernice Yu"), getGroupSet("CS2103T"),
                    new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Telegram("berniceyu"), new GitHub("berniceyu")),
            new Person(new Name("Charlotte Oliveiro"), getGroupSet("CS2103T"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Telegram("charlotteoliverio"), new GitHub("charlotteoliverio")),
            new Person(new Name("David Li"), getGroupSet("CS2103T"),
                    new Phone("91031282"), new Email("lidavid@example.com"),
                    new Telegram("davidli"), new GitHub("davidli")),
            new Person(new Name("Irfan Ibrahim"), getGroupSet("CS2103T", "CS2101"),
                    new Phone("92492021"), new Email("irfan@example.com"),
                    new Telegram("irfan"), new GitHub("irfan")),
            new Person(new Name("Roy Balakrishnan"), getGroupSet("CS2103T", "CS2101"),
                    new Phone("92624417"), new Email("royb@example.com"),
                    new Telegram("roybalakrishnan"), new GitHub("roybalakrishnan"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Task[] getSampleTasks() {
        return new Task[] { new Deadline(new Description("Project Meeting"), new Group("CS2101"),
                new Date("2021-11-11"), new TaskType("deadline"))
        };
    }

    public static ReadOnlyTaskRecords getSampleTaskRecords() {
        TaskRecords sampleTr = new TaskRecords();
        for (Task sampleTask : getSampleTasks()) {
            sampleTr.addTask(sampleTask);
        }
        return sampleTr;
    }

    /**
     * Returns a group set containing the list of strings given.
     */
    public static Set<Group> getGroupSet(String... strings) {
        return Arrays.stream(strings)
                .map(Group::new)
                .collect(Collectors.toSet());
    }

}
