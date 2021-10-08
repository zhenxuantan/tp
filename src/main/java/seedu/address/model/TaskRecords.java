package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

public class TaskRecords implements ReadOnlyTaskRecords {
    private final TaskList records;

    {
        records = new TaskList();
    }
    public TaskRecords() {}

    /**
     * Self function to get current Task records.
     * @param existingRecords Current TaskRecords.
     */
    public TaskRecords(ReadOnlyTaskRecords existingRecords) {
        this();
        resetData(existingRecords);
    }

    /**
     * Resets Task List to the newData provided.
     * @param newData Data to change to.
     */
    public void resetData(ReadOnlyTaskRecords newData) {
        requireNonNull(newData);
        setTasks(newData.getTaskList());
    }

    public void setTasks(List<Task> tasks) {
        this.records.setTasks(tasks);
    }

    public void addTask(Task toAdd) {
        records.add(toAdd);
    }

    /**
     * Returns deleted task.
     * @param index index of task to be deleted.
     * @return deleted Task
     */
    public Task deleteTask(int index) {
        Task toDelete = records.delete(index);
        return toDelete;
    }

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return records.asUnmodifiableObservableList();
    }
}
