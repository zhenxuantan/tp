package sweebook.logic.commands;

import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.Assert.assertThrows;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.UserPrefs;
import sweebook.model.task.SortTaskComparator;

/**
 * Contains integration tests (interaction with the Model).
 */
public class SortTasksCommandTest {

    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortTasksCommand(null));
    }

    @Test
    public void execute_validSortTaskCriterion_success() throws CommandException {
        ModelManager expectedModel =
            new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        SortTaskComparator criterion = new SortTaskComparator("desc", "d");
        SortTasksCommand command = new SortTasksCommand(criterion);
        String expectedMessage = command.execute(model).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
