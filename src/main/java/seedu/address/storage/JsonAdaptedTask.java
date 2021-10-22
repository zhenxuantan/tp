package seedu.address.storage;

import static java.util.Objects.isNull;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Priority;
import seedu.address.model.task.RecurringFrequency;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.Todo;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String description;
    private final String status;
    private final String group;
    private final String date;
    private final String taskType;
    private final String recurringFrequency;
    private final String priority;

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description, @JsonProperty("status") String status,
                           @JsonProperty("group") String group, @JsonProperty("date") String date,
                           @JsonProperty("taskType") String taskType,
                           @JsonProperty("recurringFrequency") String recurringFrequency,
                           @JsonProperty("priority") String priority) {
        this.description = description;
        this.status = status;
        this.group = group;
        this.date = date;
        this.taskType = taskType;
        this.recurringFrequency = recurringFrequency;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.getDescription().description;
        status = source.getStatusIcon();
        group = source.getGroup().group;
        date = isNull(source.getDate()) ? null : source.getDate().toString();
        taskType = source.getTaskType().taskType;
        recurringFrequency = source.getRecurringFrequency().toString();
        priority = source.getPriority().priority;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (group == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }
        if (!Group.isValidGroup(group)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        final Group modelGroup = new Group(group);

        if (taskType == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TaskType.class.getSimpleName()));
        }
        if (!TaskType.isValidTaskType(taskType)) {
            throw new IllegalValueException(TaskType.MESSAGE_CONSTRAINTS);
        }
        final TaskType modelTaskType = new TaskType(taskType);
        final Date modelDate;
        if (!isNull(date)) {
            try {
                DateTimeFormatter storageDtf = DateTimeFormatter.ofPattern("MMM dd yyyy");
                DateTimeFormatter commandDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String formattedDate = commandDtf.format(storageDtf.parse(date));
                modelDate = new Date(formattedDate);
            } catch (DateTimeParseException e) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
        } else {
            modelDate = null;
        }

        final Priority modelPriority;
        if (priority == null) {
            modelPriority = new Priority("med");
        } else {
            if (!Priority.isValidPriority(priority)) {
                throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
            }
            modelPriority = new Priority(priority);
        }

        if (recurringFrequency == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RecurringFrequency.class.getSimpleName()));
        }
        if (!RecurringFrequency.isValidRecurringFrequency(recurringFrequency)) {
            throw new IllegalValueException(RecurringFrequency.MESSAGE_CONSTRAINTS);
        }
        final RecurringFrequency modelRecurringFreq = new RecurringFrequency(recurringFrequency);

        switch (taskType) {
        case "todo":
            Task task = new Todo(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            if (checkIsDone(status)) {
                task.markAsDone();
            }
            return task;
        case "event":
            task = new Event(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            if (checkIsDone(status)) {
                task.markAsDone();
            }
            return task;
        case "deadline":
            task = new Deadline(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            if (checkIsDone(status)) {
                task.markAsDone();
                return task;
            }
            return task;
        default:
            throw new IllegalValueException(TaskType.MESSAGE_CONSTRAINTS);
        }
    }

    public boolean checkIsDone(String status) {
        return status.equals("[X]");
    }
}
