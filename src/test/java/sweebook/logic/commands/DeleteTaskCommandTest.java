package sweebook.logic.commands;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static sweebook.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;
import static sweebook.testutil.TypicalTasks.getTypicalTaskRecords;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
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
    private ObservableList<Task> taskList = model.getTasks();
    private Index lastTaskIndex = Index.fromOneBased(taskList.size());

    // valid task index [1...total_number_of_tasks]
    @Test
    public void execute_validIndex_success() {
        Task lastTask = model.getTasks().get(lastTaskIndex.getZeroBased()); //Boundary value
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(lastTaskIndex);
        String deleteLastTaskExpectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, lastTask);

        ModelManager deleteLastTaskExpectedModel = new ModelManager(model.getContactList(),
            new UserPrefs(), model.getTaskList());
        deleteLastTaskExpectedModel.deleteTask(lastTask);
        assertCommandSuccess(deleteTaskCommand, model, deleteLastTaskExpectedMessage, deleteLastTaskExpectedModel);

        Task firstTask = taskList.get(INDEX_FIRST_TASK.getZeroBased()); //Boundary value
        DeleteTaskCommand deleteFirstTaskCommand = new DeleteTaskCommand(INDEX_FIRST_TASK);
        String deleteFirstTaskExpectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, firstTask);

        ModelManager deleteFirstTaskExpectedModel = new ModelManager(model.getContactList(),
            new UserPrefs(), model.getTaskList());
        deleteFirstTaskExpectedModel.deleteTask(firstTask);
        assertCommandSuccess(deleteFirstTaskCommand, model, deleteFirstTaskExpectedMessage,
            deleteFirstTaskExpectedModel);

        Task secondTask = model.getTasks().get(INDEX_SECOND_TASK.getZeroBased());
        DeleteTaskCommand deleteSecondTaskCommand = new DeleteTaskCommand(INDEX_SECOND_TASK);
        String deleteSecondTaskExpectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, secondTask);

        ModelManager deleteSecondTaskExpectedModel = new ModelManager(model.getContactList(),
            new UserPrefs(), model.getTaskList());
        deleteSecondTaskExpectedModel.deleteTask(secondTask);
        assertCommandSuccess(deleteSecondTaskCommand, model, deleteSecondTaskExpectedMessage,
            deleteSecondTaskExpectedModel);
    }

    // invalid index [total_number_of_tasks + 1 ... MAX_INT]
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index moreThanTotalTasksIndex = Index.fromOneBased(lastTaskIndex.getOneBased() + 1); //Boundary value
        DeleteTaskCommand deleteMoreThanTotalTasksCommand = new DeleteTaskCommand(moreThanTotalTasksIndex);

        CommandTestUtil.assertCommandFailure(deleteMoreThanTotalTasksCommand, model, MESSAGE_INVALID_TASK_INDEX);
    }

}
