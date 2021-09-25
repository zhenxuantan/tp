package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

public class TaskRecords implements ReadOnlyTaskRecords {
    private final TaskList records;

    {
        records = new TaskList();
    }
    public TaskRecords() {}

    public TaskRecords(ReadOnlyTaskRecords existingRecords) {
        this();
        resetData(existingRecords);
    }

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

    public String toString() {
        return records.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return records.asUnmodifiableObservableList();
    }
}
