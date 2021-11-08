package sweebook.model.task;

import static sweebook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sweebook.commons.util.CollectionUtil;

public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list.
     */
    public void add(Task toAdd) {
        requireAllNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setTasks(TaskList replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        CollectionUtil.requireAllNonNull(tasks);
        internalList.setAll(tasks);
    }

    /**
     * Deletes a task in the task list.
     *
     * @param task task to be deleted.
     */
    public Task delete(Task task) {
        internalList.remove(task);
        return task;
    }

    /**
     * Updates all recurring tasks' dates.
     */
    public void updateRecurringTasksDates() {
        for (Task t : internalList) {
            if (t.getRecurringFrequency().isRecurring()) {
                t.updateRecurringTaskDate();
            }
        }
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     */
    public void setTask(Task target, Task editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);
        int index = internalList.indexOf(target);

        internalList.set(index, editedTask);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TaskList) {
            return internalList.equals(((TaskList) other).internalList);
        } else {
            return false;
        }
    }

}
