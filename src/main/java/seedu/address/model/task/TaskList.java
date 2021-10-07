package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list.
     * The person must not already exist in the list.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setTasks(seedu.address.model.task.TaskList replacement) {
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
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Task> filterTask(FilterTaskCriterion toFilter) {
        requireNonNull(toFilter);
        ObservableList<Task> filteredTaskList = FXCollections.observableArrayList();
        Character firstChar = toFilter.toString().charAt(0);
        switch(firstChar){
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
        }
        for (Task task : filteredTaskList) {
            System.out.println(task.toString());
        }
        return filteredTaskList;
    }

    private ObservableList<Task> filterByGroup(Group group) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if(task.getGroup().equalTo(group)){
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    private ObservableList<Task> filterByType(TaskType taskType) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if(task.getTaskType().equalTo(taskType)){
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    private ObservableList<Task> filterByDate(Date date) {
        ObservableList<Task> filteredList = FXCollections.observableArrayList();
        for (Task task : internalList) {
            if(task.getDate().compare(date) == 0){
                filteredList.add(task);
            }
        }
        return filteredList;
    }
}
