package sweebook.logic.commands;

import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.logic.commands.DoneTaskCommand.MESSAGE_ALREADY_DONE;
import static sweebook.logic.commands.DoneTaskCommand.MESSAGE_SUCCESS;
import static sweebook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import sweebook.commons.core.Messages;
import sweebook.commons.core.index.Index;
import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.task.Task;
import sweebook.model.task.TaskList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DoneTaskCommand}.
 */
public class DoneTaskCommandTest {
    private Task referenceTask = getTypicalTaskRecords().getTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
    private Task forTest = new Task(referenceTask.getDescription(), referenceTask.getGroup(), referenceTask.getDate(),
        referenceTask.getTaskType(), referenceTask.getRecurringFrequency(), referenceTask.getPriority());
    private Model model = new ModelManager(new ContactList(), new UserPrefs(), new TaskRecordsStub(forTest));

    @Test
    public void execute_validIndex_success() {
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);

        Task expectedTask = new Task(referenceTask.getDescription(), referenceTask.getGroup(), referenceTask.getDate(),
            referenceTask.getTaskType(), referenceTask.getRecurringFrequency(), referenceTask.getPriority());
        expectedTask.markAsDone();
        String expectedMessage = String.format(MESSAGE_SUCCESS, expectedTask);
        ModelManager expectedModel = new ModelManager(model.getContactList(), new UserPrefs(), model.getTaskList());

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTasks().size() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    @Test
    public void execute_alreadyDone_throwsCommandException() {
        Task doneTask = model.getTasks().get(INDEX_FIRST_PERSON.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);
        doneTask.markAsDone();
        CommandTestUtil.assertCommandFailure(doneTaskCommand, model, String.format(MESSAGE_ALREADY_DONE,
            doneTask.toString()));
    }

    private class TaskRecordsStub implements ReadOnlyTaskRecords {
        private final TaskList records = new TaskList();
        public TaskRecordsStub(Task task) {
            records.add(task);
        }

        public void doneTask(Task task) {
            Task original = task;
            Task doneTask = task;
            doneTask.markAsDone();
            this.records.setTask(original, doneTask);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return records.asUnmodifiableObservableList();
        }

    }

}

