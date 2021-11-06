package sweebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.logic.commands.CommandTestUtil.DESC_DEADLINE;
import static sweebook.logic.commands.CommandTestUtil.DESC_EVENT;
import static sweebook.logic.commands.CommandTestUtil.VALID_DATE_1;
import static sweebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEADLINE;
import static sweebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EVENT;
import static sweebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.logic.commands.CommandTestUtil.showTaskAtIndex;
import static sweebook.logic.commands.EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS;
import static sweebook.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static sweebook.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import sweebook.commons.core.Messages;
import sweebook.commons.core.index.Index;
import sweebook.logic.commands.EditTaskCommand.EditTaskDescriptor;
import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.task.Task;
import sweebook.testutil.EditTaskDescriptorBuilder;
import sweebook.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(new ContactList(), new UserPrefs(), getTypicalTaskRecords());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel =
                new ModelManager(new ContactList(), new UserPrefs(), new TaskRecords(model.getTaskList()));
        expectedModel.setTask(model.getTasks().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getTasks().size());
        Task lastTask = model.getTasks().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withDescription(VALID_DESCRIPTION_EVENT).withDate(VALID_DATE_1).build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_EVENT)
                .withDate(VALID_DATE_1).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel =
                new ModelManager(new ContactList(), new UserPrefs(), new TaskRecords(model.getTaskList()));
        expectedModel.setTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getTasks().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel =
                new ModelManager(new ContactList(), new UserPrefs(), new TaskRecords(model.getTaskList()));

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getTasks().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withDescription(VALID_DESCRIPTION_DEADLINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEADLINE).build());

        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel =
                new ModelManager(new ContactList(), new UserPrefs(), new TaskRecords(model.getTaskList()));
        expectedModel.setTask(model.getTasks().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTasks().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_DEADLINE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of contact list
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of contact list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskList().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEADLINE).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_DEADLINE);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_DEADLINE);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_DEADLINE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_EVENT)));
    }

}
