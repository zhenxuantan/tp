package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.Group;

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
     * @param index index of task to be deleted.
     */
    public Task delete(int index) {
        Task removedTask = internalList.remove(index);
        return removedTask;
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
     *
     * @param toFilter A FilterTaskCriterion
     * @return ObservableList of filtered tasks
     */
    public ObservableList<Task> filterTask(FilterTaskCriterion toFilter) {
        requireNonNull(toFilter);
        ObservableList<Task> filteredTaskList = FXCollections.observableArrayList();
        Character firstChar = toFilter.toString().charAt(0);
        switch(firstChar) {
        case 'd':
            Date date = new Date(toFilter.toString().substring(5));
            filteredTaskList = filterByDate(date);
            break;
        case 't':
            TaskType taskType = new TaskType(toFilter.toString().substring(5));
            filteredTaskList = filterByType(taskType);
            break;
        case 'g':
            Group group = new Group(toFilter.toString().substring(2));
            filteredTaskList = filterByGroup(group);
            break;
        default:
            break;
        }
        for (Task task : filteredTaskList) {
            System.out.println(task.toString());
        }
        return filteredTaskList;
    }

    private ObservableList<Task> filterByGroup(Group group) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if (task.getGroup().equals(group)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    private ObservableList<Task> filterByType(TaskType taskType) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if (task.getTaskType().equals(taskType)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    private ObservableList<Task> filterByDate(Date date) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if (task.getDate().compare(date) == 0) {
                filteredList.add(task);
            }
        }
        return filteredList;
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
