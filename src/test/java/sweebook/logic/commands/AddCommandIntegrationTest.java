package sweebook.logic.commands;

import static sweebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.person.Person;
import sweebook.testutil.PersonBuilder;
import sweebook.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalContactList(), new UserPrefs(), new TaskRecords());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getContactList(), new UserPrefs(), new TaskRecords());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getContactList().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                String.format(Person.MESSAGE_DUPLICATE_PERSON, "name", personInList.getName()));
    }

}
