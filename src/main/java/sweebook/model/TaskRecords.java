package sweebook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import sweebook.model.task.Task;
import sweebook.model.task.TaskList;

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

    public void updateRecurringTasks() {
        records.updateRecurringTasksDates();
    }

    /**
     * Returns deleted task.
     * @param task task to be deleted.
     * @return deleted Task
     */
    public Task deleteTask(Task task) {
        return records.delete(task);
    }

    /**
     * Update given task as done.
     * @param task
     */
    public void doneTask(Task task) {
        Task original = task;
        Task doneTask = task;
        doneTask.markAsDone();
        this.records.setTask(original, doneTask);
    }

    /**
     * Update given done task as not done.
     * @param task
     */
    public void undoDoneTask(Task task) {
        Task original = task;
        Task undoDoneTask = task;
        undoDoneTask.markAsUndone();
        this.records.setTask(original, undoDoneTask);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the task list.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        records.setTask(target, editedTask);
    }

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " tasks";
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return records.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TaskRecords // instanceof handles nulls
            && records.equals(((TaskRecords) other).records));
    }
}
