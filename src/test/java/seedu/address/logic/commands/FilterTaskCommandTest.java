package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.DEADLINE1;
import static seedu.address.testutil.TypicalTasks.EVENT1;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.FilterTaskPredicate;
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
    public void execute_validDate_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = DEADLINE1;
        FilterTaskPredicate criterion = new FilterTaskPredicate("date/" + toFilter.getDate().getString());
        FilterTaskCommand command = new FilterTaskCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validType_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = EVENT1;
        FilterTaskPredicate criterion = new FilterTaskPredicate("type/" + toFilter.getTaskType().toString());
        FilterTaskCommand command = new FilterTaskCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validGroup_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = EVENT1;
        FilterTaskPredicate criterion = new FilterTaskPredicate("g/" + toFilter.getGroup().toString());
        FilterTaskCommand command = new FilterTaskCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
