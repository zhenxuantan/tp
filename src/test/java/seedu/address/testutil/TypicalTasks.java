package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskRecords;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task DEADLINE1 = new TaskBuilder().withDescription("Deadline 1")
        .withGroup("cs2101").withTaskType("deadline").withDate("2021-12-03").build();
    public static final Task EVENT1 = new TaskBuilder().withDescription("event 1")
        .withGroup("cs2103t").withTaskType("event").withDate("2022-02-03").build();
    public static final Task TODO1 = new TaskBuilder().withDescription("todo 1")
        .withGroup("cs2101").withTaskType("todo").withDate("2022-10-29").build();
    public static final Task DEADLINE2 = new TaskBuilder().withDescription("Deadline 2")
        .withGroup("cs2103t").withTaskType("deadline").withDate("2022-07-01").build();

    // Manually add
    public static final Task EVENT2 = new TaskBuilder().withDescription("Event 2")
        .withGroup("cs2103t").withTaskType("event").withDate("2022-09-01").build();
    public static final Task TODO2 = new TaskBuilder().withDescription("todo 2")
        .withGroup("cs2101").withTaskType("todo").withDate("2023-01-21").build();

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
        return new ArrayList<>(Arrays.asList(DEADLINE1, EVENT1, TODO1, DEADLINE2));
    }
}
