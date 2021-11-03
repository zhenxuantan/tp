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
        final Description modelDescription = getDescriptionFromJson(description);

        final Group modelGroup = getGroupFromJson(group);

        final TaskType modelTaskType = getTaskTypeFromJson(taskType);

        final Date modelDate = getDateFromJson(date);

        final Priority modelPriority = getPriorityFromJson(priority);

        final RecurringFrequency modelRecurringFreq = getRecurringFrequencyFromJson(recurringFrequency);

        Task taskFromJson;
        switch (taskType) {
        case "todo":
            taskFromJson = new Todo(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            break;
        case "event":
            taskFromJson = new Event(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            break;
        case "deadline":
            taskFromJson = new Deadline(modelDescription, modelGroup, modelDate, modelTaskType,
                modelRecurringFreq, modelPriority);
            break;
        default:
            throw new IllegalValueException(TaskType.MESSAGE_CONSTRAINTS);
        }
        updateIfTaskDone(taskFromJson, status);
        return taskFromJson;
    }

    private Description getDescriptionFromJson(String descriptionFromJson) throws IllegalValueException {
        if (descriptionFromJson == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(descriptionFromJson)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        Description finalDescription = new Description(descriptionFromJson);
        return finalDescription;
    }

    private Group getGroupFromJson(String groupFromJson) throws IllegalValueException {
        if (groupFromJson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }
        if (!Group.isValidGroup(groupFromJson)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        Group finalGroup = new Group(groupFromJson);
        return finalGroup;
    }

    private TaskType getTaskTypeFromJson(String taskTypeFromJson) throws IllegalValueException {
        if (taskTypeFromJson == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, TaskType.class.getSimpleName()));
        }
        if (!TaskType.isValidTaskType(taskTypeFromJson)) {
            throw new IllegalValueException(TaskType.MESSAGE_CONSTRAINTS);
        }
        TaskType finalTaskType = new TaskType(taskTypeFromJson);
        return finalTaskType;
    }

    private Date getDateFromJson(String dateFromJson) throws IllegalValueException {
        Date finalDate;
        if (isNull(dateFromJson)) {
            finalDate = null;
        }
        try {
            DateTimeFormatter storageDtf = DateTimeFormatter.ofPattern("MMM dd yyyy");
            DateTimeFormatter commandDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String formattedDate = commandDtf.format(storageDtf.parse(dateFromJson));
            finalDate = new Date(formattedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        return finalDate;
    }

    private Priority getPriorityFromJson(String priorityFromJson) throws IllegalValueException {
        Priority finalPriority;
        if (isNull(priorityFromJson)) {
            finalPriority = new Priority("med");
        }
        if (!Priority.isValidPriority(priorityFromJson)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        finalPriority = new Priority(priorityFromJson);
        return finalPriority;
    }

    private RecurringFrequency getRecurringFrequencyFromJson(String recurringFrequencyFromJson)
        throws IllegalValueException {
        if (recurringFrequencyFromJson == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, RecurringFrequency.class.getSimpleName()));
        }
        if (!RecurringFrequency.isValidRecurringFrequency(recurringFrequencyFromJson)) {
            throw new IllegalValueException(RecurringFrequency.MESSAGE_CONSTRAINTS);
        }
        RecurringFrequency finalRecurringFrequency = new RecurringFrequency(recurringFrequencyFromJson);
        return finalRecurringFrequency;
    }

    private void updateIfTaskDone(Task task, String status) {
        if (isDone(status)) {
            task.markAsDone();
        }
    }

    private boolean isDone(String status) {
        return status.equals("[X]");
    }
}
