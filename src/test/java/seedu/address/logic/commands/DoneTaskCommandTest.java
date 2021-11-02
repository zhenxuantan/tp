package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DoneTaskCommand}.
 */
public class DoneTaskCommandTest {
    //TaskRecords testTaskRecords = new TaskRecords();
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void execute_validIndex_success() {
        Task doneTask = model.getTasks().get(INDEX_FIRST_PERSON.getZeroBased());
        Task forTest = new Task(doneTask.getDescription(), doneTask.getGroup(), doneTask.getDate(),
            doneTask.getTaskType(), doneTask.getRecurringFrequency(), doneTask.getPriority());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);

        Task expectedTask = new Task(doneTask.getDescription(), doneTask.getGroup(), doneTask.getDate(),
            doneTask.getTaskType(), doneTask.getRecurringFrequency(), doneTask.getPriority());
        expectedTask.markAsDone();
        String expectedMessage = String.format(DoneTaskCommand.MESSAGE_SUCCESS, expectedTask);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getTaskList());

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
        model.undoDoneTask(doneTask);
        System.out.println(doneTask.toString());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTasks().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        assertCommandFailure(doneTaskCommand, model, Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    @Test
    public void execute_alreadyDone_throwsCommandException() {
        Task doneTask = model.getTasks().get(INDEX_FIRST_PERSON.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);
        doneTask.markAsDone();
        assertCommandFailure(doneTaskCommand, model, String.format(DoneTaskCommand.MESSAGE_ALREADY_DONE,
            doneTask.toString()));
    }

}

