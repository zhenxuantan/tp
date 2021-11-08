package sweebook.logic.commands;

import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.logic.commands.DoneTaskCommand.MESSAGE_ALREADY_DONE;
import static sweebook.logic.commands.DoneTaskCommand.MESSAGE_SUCCESS;
import static sweebook.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static sweebook.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import java.util.List;

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
    private Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), getTypicalTaskRecords());
    private ObservableList<Task> taskList = model.getTasks();

    private Model modelCopy = new ModelManager(new ContactList(), new UserPrefs(), new TaskRecordsStub(taskList));
    private ObservableList<Task> taskListCopy = modelCopy.getTasks();
    private Index lastTaskIndex = Index.fromOneBased(taskListCopy.size());

    // valid task index [1...total_number_of_tasks]
    @Test
    public void execute_validIndex_success() {
        DoneTaskCommand doneFirstTaskCommand = new DoneTaskCommand(INDEX_FIRST_TASK); //Boundary Value
        Task referenceFirstTask = taskListCopy.get(INDEX_FIRST_TASK.getZeroBased());
        Task expectedFirstTask = new Task(referenceFirstTask.getDescription(), referenceFirstTask.getGroup(),
            referenceFirstTask.getDate(), referenceFirstTask.getTaskType(), referenceFirstTask.getRecurringFrequency(),
            referenceFirstTask.getPriority());
        expectedFirstTask.markAsDone();
        String expectedDoneFirstTaskMessage = String.format(MESSAGE_SUCCESS, expectedFirstTask);
        ModelManager expectedDoneFirstTaskModel = new ModelManager(modelCopy.getContactList(), new UserPrefs(),
            modelCopy.getTaskList());

        assertCommandSuccess(doneFirstTaskCommand, modelCopy, expectedDoneFirstTaskMessage, expectedDoneFirstTaskModel);

        DoneTaskCommand doneLastTaskCommand = new DoneTaskCommand(lastTaskIndex); //Boundary value
        Task referenceLastTask = modelCopy.getTasks().get(lastTaskIndex.getZeroBased());
        Task expectedLastTask = new Task(referenceLastTask.getDescription(), referenceLastTask.getGroup(),
            referenceLastTask.getDate(), referenceLastTask.getTaskType(), referenceLastTask.getRecurringFrequency(),
            referenceLastTask.getPriority());
        expectedLastTask.markAsDone();
        String expectedDoneLastTaskMessage = String.format(MESSAGE_SUCCESS, expectedLastTask);
        ModelManager expectedDoneLastTaskModel = new ModelManager(modelCopy.getContactList(), new UserPrefs(),
            modelCopy.getTaskList());

        assertCommandSuccess(doneLastTaskCommand, modelCopy, expectedDoneLastTaskMessage, expectedDoneLastTaskModel);

        DoneTaskCommand doneSecondTaskCommand = new DoneTaskCommand(INDEX_SECOND_TASK);
        Task referenceSecondTask = modelCopy.getTasks().get(INDEX_SECOND_TASK.getZeroBased());
        Task expectedSecondTask = new Task(referenceSecondTask.getDescription(), referenceSecondTask.getGroup(),
            referenceSecondTask.getDate(), referenceSecondTask.getTaskType(),
            referenceSecondTask.getRecurringFrequency(), referenceSecondTask.getPriority());
        expectedSecondTask.markAsDone();
        String expectedDoneSecondTaskMessage = String.format(MESSAGE_SUCCESS, expectedSecondTask);
        ModelManager expectedDoneSecondTaskModel = new ModelManager(modelCopy.getContactList(), new UserPrefs(),
            modelCopy.getTaskList());

        assertCommandSuccess(doneSecondTaskCommand, modelCopy, expectedDoneSecondTaskMessage,
            expectedDoneSecondTaskModel);
    }

    // invalid task index [total_number_of_tasks + 1 ... MAX_INT]
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(lastTaskIndex.getOneBased() + 1);
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(doneTaskCommand, modelCopy, Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    // task from given task index is already marked as done
    @Test
    public void execute_alreadyDone_throwsCommandException() {
        Task doneTask = modelCopy.getTasks().get(INDEX_FIRST_TASK.getZeroBased());
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_TASK);
        doneTask.markAsDone();
        CommandTestUtil.assertCommandFailure(doneTaskCommand, modelCopy, String.format(MESSAGE_ALREADY_DONE,
            doneTask.toString()));
    }

    private class TaskRecordsStub implements ReadOnlyTaskRecords {
        private final TaskList records = new TaskList();
        public TaskRecordsStub(List<Task> tasks) {
            for (int i = 0; i < tasks.size(); i++) {
                Task toBeCopied = tasks.get(i);
                Task copy = new Task(toBeCopied.getDescription(), toBeCopied.getGroup(), toBeCopied.getDate(),
                    toBeCopied.getTaskType(), toBeCopied.getRecurringFrequency(), toBeCopied.getPriority());
                records.add(copy);
            }
        }
        @Override
        public ObservableList<Task> getTaskList() {
            return records.asUnmodifiableObservableList();
        }

    }

}

