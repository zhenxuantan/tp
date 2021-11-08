package sweebook.logic.commands;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.Assert.assertThrows;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;
import static sweebook.testutil.TypicalTasks.FIRST_DEADLINE;
import static sweebook.testutil.TypicalTasks.FIRST_EVENT;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.UserPrefs;
import sweebook.model.task.FilterTaskPredicate;
import sweebook.model.task.Task;

/**
 * Contains integration tests (interaction with the Model).
 */
public class FilterTasksCommandTest {

    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterTasksCommand(null));
    }

    @Test
    public void execute_validDate_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = FIRST_DEADLINE;
        FilterTaskPredicate criterion = new FilterTaskPredicate("date/" + toFilter.getDate().getString());
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validType_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = FIRST_EVENT;
        FilterTaskPredicate criterion = new FilterTaskPredicate("type/" + toFilter.getTaskType().toString());
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validGroup_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = FIRST_EVENT;
        FilterTaskPredicate criterion = new FilterTaskPredicate("g/" + toFilter.getGroup().toString());
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validDesc_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = FIRST_EVENT;
        FilterTaskPredicate criterion = new FilterTaskPredicate("d/" + toFilter.getDescription().toString());
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPty_success() throws CommandException {
        ModelManager expectedModel =
                new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
        Task toFilter = FIRST_EVENT;
        FilterTaskPredicate criterion = new FilterTaskPredicate("pty/" + toFilter.getPriority().toString());
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        String expectedMessage = command.execute(expectedModel).getFeedbackToUser();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDate_failure() {
        Task toFilter = FIRST_EVENT;
        String invalidDate = "date/" + toFilter.getDate().toString() + "invalid date";
        FilterTaskPredicate criterion = new FilterTaskPredicate(invalidDate);
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        CommandTestUtil.assertCommandFailure(command, model, MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_invalidPty_failure() {
        Task toFilter = FIRST_EVENT;
        String invalidPty = "pty/" + toFilter.getPriority().toString() + "invalid pty";
        FilterTaskPredicate criterion = new FilterTaskPredicate(invalidPty);
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        CommandTestUtil.assertCommandFailure(command, model, MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_invalidGroup_failure() {
        Task toFilter = FIRST_EVENT;
        String invalidGroup = "g/" + toFilter.getGroup().toString() + "invalid group";
        FilterTaskPredicate criterion = new FilterTaskPredicate(invalidGroup);
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        CommandTestUtil.assertCommandFailure(command, model, MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_invalidCriterionSuffix_failure() {
        Task toFilter = FIRST_EVENT;
        String invalidSuffix = "y/" + toFilter.getPriority().toString() + "invalid pty";
        FilterTaskPredicate criterion = new FilterTaskPredicate(invalidSuffix);
        FilterTasksCommand command = new FilterTasksCommand(criterion);
        CommandTestUtil.assertCommandFailure(command, model, MESSAGE_INVALID_COMMAND_FORMAT);
    }

}
