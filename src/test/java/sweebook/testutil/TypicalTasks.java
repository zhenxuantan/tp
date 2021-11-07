package sweebook.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sweebook.model.TaskRecords;
import sweebook.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    //first set
    public static final Task FIRST_DEADLINE = new TaskBuilder().withDescription("Deadline 1")
        .withGroup("cs2101").withTaskType("deadline").withDate("2021-12-03").build();
    public static final Task FIRST_EVENT = new TaskBuilder().withDescription("event 1")
        .withGroup("cs2103t").withTaskType("event").withDate("2022-02-03").withRecurringFrequency("year")
        .withPriority("low").build();
    public static final Task FIRST_TODO = new TaskBuilder().withDescription("todo 1")
        .withGroup("cs2101").withTaskType("todo").withDate("2022-10-29").withRecurringFrequency("week")
        .withPriority("high").build();

    //second set
    public static final Task SECOND_DEADLINE = new TaskBuilder().withDescription("Deadline 2")
        .withGroup("cs2103t").withTaskType("deadline").withDate("2022-07-01").withRecurringFrequency("month")
        .withPriority("med").build();
    public static final Task SECOND_EVENT = new TaskBuilder().withDescription("Event 2")
        .withGroup("cs2103t").withTaskType("event").withDate("2022-09-01").build();
    public static final Task SECOND_TODO = new TaskBuilder().withDescription("todo 2")
        .withGroup("cs2101").withTaskType("todo").withDate("2023-01-21").build();
    public static final Task SECOND_TODO_WITH_CAPS = new TaskBuilder().withDescription("TODO 2")
        .withGroup("cs2103t").withTaskType("todo").withoutDate().build();

    //third set
    public static final Task THIRD_TODO = new TaskBuilder().withDescription("todo 3")
        .withGroup("cs2103t").withTaskType("todo").withoutDate().build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code TaskRecords} with all the typical tasks.
     */
    public static TaskRecords getTypicalTaskRecords() {
        TaskRecords records = new TaskRecords();
        for (Task task : getTypicalTasks()) {
            records.addTask(task);
        }
        return records;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(FIRST_DEADLINE, FIRST_EVENT, FIRST_TODO, SECOND_DEADLINE));
    }
}
