package sweebook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import sweebook.model.ContactList;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.TaskRecords;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Telegram;
import sweebook.model.task.Date;
import sweebook.model.task.Deadline;
import sweebook.model.task.Description;
import sweebook.model.task.Event;
import sweebook.model.task.Priority;
import sweebook.model.task.RecurringFrequency;
import sweebook.model.task.Task;
import sweebook.model.task.TaskType;
import sweebook.model.task.Todo;

/**
 * Contains utility methods for populating {@code ContactList} with sample data.
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

    public static ReadOnlyContactList getSampleContactList() {
        ContactList sampleAb = new ContactList();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Deadline(new Description("OP1 script"), new Group("CS2101"),
                    new Date("2021-11-11"), new TaskType("deadline"), new RecurringFrequency("none"),
            new Priority("med")),
            new Deadline(new Description("Update User Guide"), new Group("CS2103T"),
                    new Date("2021-12-11"), new TaskType("deadline"),
                new RecurringFrequency("none"), new Priority("med")),
            new Event(new Description("Project Meeting"), new Group("CS2103T"),
                    new Date("2021-12-21"), new TaskType("event"),
                new RecurringFrequency("none"), new Priority("med")),
            new Event(new Description("OP1 Presentation"), new Group("CS2101"),
                    new Date("2021-11-21"), new TaskType("event"), new RecurringFrequency("none"),
                new Priority("med")),
            new Todo(new Description("Create slides"), new Group("CS2101"),
                    new Date("2021-11-17"), new TaskType("todo"), new RecurringFrequency("none"),
                new Priority("med"))
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
