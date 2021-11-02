package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskRecords;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DoneTaskCommand}.
 */
public class DoneTaskCommandTest {
    //TaskRecords testTaskRecords = new TaskRecords();
    private Task referenceTask = getTypicalTaskRecords().getTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
    private Task forTest = new Task(referenceTask.getDescription(), referenceTask.getGroup(), referenceTask.getDate(),
        referenceTask.getTaskType(), referenceTask.getRecurringFrequency(), referenceTask.getPriority());
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new TaskRecordsStub(forTest));

    @Test
    public void execute_validIndex_success() {
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_PERSON);

        Task expectedTask = new Task(referenceTask.getDescription(), referenceTask.getGroup(), referenceTask.getDate(),
            referenceTask.getTaskType(), referenceTask.getRecurringFrequency(), referenceTask.getPriority());
        expectedTask.markAsDone();
        String expectedMessage = String.format(DoneTaskCommand.MESSAGE_SUCCESS, expectedTask);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getTaskList());

        assertCommandSuccess(doneTaskCommand, model, expectedMessage, expectedModel);
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

