package sweebook.storage;

import static java.util.Objects.isNull;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import sweebook.commons.exceptions.IllegalValueException;
import sweebook.model.group.Group;
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
        if (isNull(descriptionFromJson)) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(descriptionFromJson)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(descriptionFromJson);
    }

    private Group getGroupFromJson(String groupFromJson) throws IllegalValueException {
        if (isNull(groupFromJson)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }
        if (!Group.isValidGroup(groupFromJson)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(groupFromJson);
    }

    private TaskType getTaskTypeFromJson(String taskTypeFromJson) throws IllegalValueException {
        if (isNull(taskTypeFromJson)) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, TaskType.class.getSimpleName()));
        }
        if (!TaskType.isValidTaskType(taskTypeFromJson)) {
            throw new IllegalValueException(TaskType.MESSAGE_CONSTRAINTS);
        }
        return new TaskType(taskTypeFromJson);
    }

    private Date getDateFromJson(String dateFromJson) throws IllegalValueException {
        Date finalDate;
        if (isNull(dateFromJson)) {
            finalDate = null;
        } else {
            try {
                DateTimeFormatter storageDtf = Date.DTF_STORAGE;
                DateTimeFormatter commandDtf = Date.DTF_COMMAND;

                String formattedDate = commandDtf.format(storageDtf.parse(dateFromJson));
                finalDate = new Date(formattedDate);
            } catch (DateTimeParseException e) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
        }
        return finalDate;
    }

    private Priority getPriorityFromJson(String priorityFromJson) throws IllegalValueException {
        if (isNull(priorityFromJson)) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priorityFromJson)) {
            throw new IllegalValueException(RecurringFrequency.MESSAGE_CONSTRAINTS);
        }
        return new Priority(priorityFromJson);
    }

    private RecurringFrequency getRecurringFrequencyFromJson(String recurringFrequencyFromJson)
        throws IllegalValueException {
        if (isNull(recurringFrequencyFromJson)) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, RecurringFrequency.class.getSimpleName()));
        }
        if (!RecurringFrequency.isValidRecurringFrequency(recurringFrequencyFromJson)) {
            throw new IllegalValueException(RecurringFrequency.MESSAGE_CONSTRAINTS);
        }
        return new RecurringFrequency(recurringFrequencyFromJson);
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
