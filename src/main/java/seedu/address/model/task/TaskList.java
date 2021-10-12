package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setTasks(TaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.setAll(tasks);
    }

    /**
     * Deletes a task in records.
     *
     * @param task task to be deleted.
     */
    public Task delete(Task task) {
        internalList.remove(task);
        return task;
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

    /**
     * Returns a sorted taskList based on the given SortTaskCriterion
     *
     * @param toSort A SortTaskCriterion
     * @return ObservableList of sorted tasks
     */
    public ObservableList<Task> sortTask(SortTaskCriterion toSort) {
        requireNonNull(toSort);
        boolean isAscending = toSort.getAscending();
        switch (toSort.getParam()) {
        case "desc":
            return sortByDesc(isAscending);
        case "date":
            return sortByDate(isAscending);
        case "added":
            return sortByAdded(isAscending);
        case "group":
            return sortByGroup(isAscending);
        default:
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Task> sortByDesc(boolean isAscending) {
        ArrayList<Task> modifiableTaskList = new ArrayList<>(internalList);
        modifiableTaskList.sort((t1, t2) -> isAscending ? t1.compareDescription(t2) : t2.compareDescription(t1));
        return FXCollections.observableArrayList(modifiableTaskList);
    }

    private ObservableList<Task> sortByDate(boolean isAscending) {
        ArrayList<Task> modifiableTaskList = new ArrayList<>(internalList);
        modifiableTaskList.sort((t1, t2) -> isAscending ? t1.compareDate(t2) : t2.compareDate(t1));
        return FXCollections.observableArrayList(modifiableTaskList);
    }

    private ObservableList<Task> sortByAdded(boolean isAscending) {
        ArrayList<Task> modifiableTaskList = new ArrayList<>(internalList);
        if (!isAscending) {
            Collections.reverse(modifiableTaskList);
        }
        return FXCollections.observableArrayList(modifiableTaskList);
    }

    private ObservableList<Task> sortByGroup(boolean isAscending) {
        ArrayList<Task> modifiableTaskList = new ArrayList<>(internalList);
        modifiableTaskList.sort((t1, t2) -> isAscending ? t1.compareGroup(t2) : t2.compareGroup(t1));
        return FXCollections.observableArrayList(modifiableTaskList);
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
