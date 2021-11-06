package sweebook.testutil;

import sweebook.model.group.Group;
import sweebook.model.task.Date;
import sweebook.model.task.Description;
import sweebook.model.task.Priority;
import sweebook.model.task.RecurringFrequency;
import sweebook.model.task.Task;
import sweebook.model.task.TaskType;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_DESCRIPTION = "Final Project";
    public static final String DEFAULT_GROUP = "cs2101";
    public static final String DEFAULT_TYPE = "deadline";
    public static final String DEFAULT_DATE = "2021-12-12";
    public static final String DEFAULT_RECURRING_FREQUENCY = "none";
    public static final String DEFAULT_PRIORITY = "med";

    private Description description;
    private Group group;
    private TaskType type;
    private Date date;
    private RecurringFrequency recurringFrequency;
    private Priority priority;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        group = new Group(DEFAULT_GROUP);
        type = new TaskType(DEFAULT_TYPE);
        date = new Date(DEFAULT_DATE);
        recurringFrequency = new RecurringFrequency(DEFAULT_RECURRING_FREQUENCY);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        description = taskToCopy.getDescription();
        group = taskToCopy.getGroup();
        type = taskToCopy.getTaskType();
        date = taskToCopy.getDate();
        recurringFrequency = taskToCopy.getRecurringFrequency();
        priority = taskToCopy.getPriority();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code Task} that we are building.
     */
    public TaskBuilder withGroup(String group) {
        this.group = new Group(group);
        return this;
    }

    /**
     * Sets the {@code TaskType} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskType(String taskType) {
        this.type = new TaskType(taskType);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} as null (for Todo) that we are building.
     */
    public TaskBuilder withoutDate() {
        this.date = null;
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code RecurringFrequency} of the {@code Task} that we are building.
     */
    public TaskBuilder withRecurringFrequency(String recurringFrequency) {
        this.recurringFrequency = new RecurringFrequency(recurringFrequency);
        return this;
    }

    public Task build() {
        return new Task(description, group, date, type, recurringFrequency, priority);
    }
}
