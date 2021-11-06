package sweebook.logic.commands;

import static sweebook.logic.commands.ClearCommand.MESSAGE_SUCCESS;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;

import org.junit.jupiter.api.Test;

import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyContactList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyContactList_success() {
        Model model = new ModelManager(getTypicalContactList(), new UserPrefs(), new TaskRecords());
        Model expectedModel = new ModelManager(getTypicalContactList(), new UserPrefs(), new TaskRecords());
        expectedModel.setContactList(new ContactList());

        assertCommandSuccess(new ClearCommand(), model, MESSAGE_SUCCESS, expectedModel);
    }

}
