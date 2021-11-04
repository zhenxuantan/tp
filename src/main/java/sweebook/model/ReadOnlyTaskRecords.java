package sweebook.model;

import javafx.collections.ObservableList;
import sweebook.model.task.Task;

public interface ReadOnlyTaskRecords {
    /**
     * Returns an unmodifiable view of the task list.
     */
    ObservableList<Task> getTaskList();
}
