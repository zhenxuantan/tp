package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.FilterTaskCriterion;
import seedu.address.model.task.Task;

public interface ReadOnlyTaskRecords {
    /**
     * Returns an unmodifiable view of the task list.
     */
    ObservableList<Task> getTaskList();
}
