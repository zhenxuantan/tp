package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.SortTaskComparator;

/**
 * Contains integration tests (interaction with the Model).
 */
public class SortTasksCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortTasksCommand(null));
    }

    @Test
    public void execute_validSortTaskCriterion_success() throws CommandException {
        ModelManager expectedModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());
        SortTaskComparator criterion = new SortTaskComparator("desc", "d");
        SortTasksCommand command = new SortTasksCommand(criterion);
        String expectedMessage = command.execute(model).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
