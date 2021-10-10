package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.DEADLINE1;
import static seedu.address.testutil.TypicalTasks.EVENT1;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.FilterTaskCriterion;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model).
 */
public class FilterTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterTaskCommand(null));
    }

    @Test
    public void execute_validDate_success() {
        Task toFilter = DEADLINE1;
        FilterTaskCriterion criterion = new FilterTaskCriterion("date/" + toFilter.getDate().getString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getTaskList());
        ObservableList<Task> filteredTasks = expectedModel.filterTask(criterion);

        assertEquals(toFilter, filteredTasks.get(0));
    }

    @Test
    public void execute_validType_success() {
        Task toFilter = EVENT1;
        FilterTaskCriterion criterion = new FilterTaskCriterion("type/" + toFilter.getTaskType().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getTaskList());
        ObservableList<Task> filteredTasks = expectedModel.filterTask(criterion);

        assertEquals(toFilter, filteredTasks.get(0));
    }

    @Test
    public void execute_validGroup_success() {
        Task toFilter = EVENT1;
        FilterTaskCriterion criterion = new FilterTaskCriterion("g/" + toFilter.getGroup().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getTaskList());
        ObservableList<Task> filteredTasks = expectedModel.filterTask(criterion);

        assertEquals(toFilter, filteredTasks.get(0));
    }
}
