package sweebook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import sweebook.commons.exceptions.IllegalValueException;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.TaskRecords;
import sweebook.model.task.Task;

/**
 * An Immutable TaskRecords that is serializable to JSON format.
 */
@JsonRootName(value = "taskrecords")
class JsonSerializableTaskRecords {
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskRecords} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTaskRecords(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyTaskRecords} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableATaskRecords}.
     */
    public JsonSerializableTaskRecords(ReadOnlyTaskRecords source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TaskRecords into the model's {@code TaskRecords} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaskRecords toModelType() throws IllegalValueException {
        TaskRecords taskRecords = new TaskRecords();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            taskRecords.addTask(task);
        }
        return taskRecords;
    }

}
