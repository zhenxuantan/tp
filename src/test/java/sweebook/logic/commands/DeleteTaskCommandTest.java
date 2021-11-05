package sweebook.logic.commands;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import sweebook.commons.core.index.Index;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.UserPrefs;
import sweebook.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void execute_validIndex_success() {
        Task toDelete = model.getTasks().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, toDelete);

        ModelManager expectedModel = new ModelManager(model.getContactList(), new UserPrefs(), model.getTaskList());
        expectedModel.deleteTask(toDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTasks().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteTaskCommand, model, MESSAGE_INVALID_TASK_INDEX);
    }

}
