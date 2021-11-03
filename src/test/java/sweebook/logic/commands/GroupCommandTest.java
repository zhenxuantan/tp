package sweebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static sweebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static sweebook.testutil.TypicalPersons.ALICE;
import static sweebook.testutil.TypicalPersons.BENSON;
import static sweebook.testutil.TypicalPersons.CARL;
import static sweebook.testutil.TypicalPersons.DANIEL;
import static sweebook.testutil.TypicalPersons.ELLE;
import static sweebook.testutil.TypicalPersons.FIONA;
import static sweebook.testutil.TypicalPersons.GEORGE;
import static sweebook.testutil.TypicalPersons.getTypicalContactList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.group.Group;
import sweebook.model.group.GroupPredicate;
import sweebook.model.person.Person;

public class GroupCommandTest {
    @Test
    public void equals() {
        GroupPredicate firstPredicate =
                new GroupPredicate(new Group("CS2103T"));
        GroupPredicate secondPredicate =
                new GroupPredicate(new Group("CS2101"));

        GroupCommand groupFirstCommand = new GroupCommand(firstPredicate);
        GroupCommand groupSecondCommand = new GroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(groupFirstCommand.equals(groupFirstCommand));

        // same values -> returns true
        GroupCommand groupFirstCommandCopy = new GroupCommand(firstPredicate);
        assertTrue(groupFirstCommand.equals(groupFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(groupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(groupFirstCommand.equals(groupSecondCommand));
    }

    @Test
    public void execute_validGroup_multiplePersonsFound() {
        Model expectedModel = new ModelManager(getTypicalContactList(), new UserPrefs(), new TaskRecords());
        Model actualModel = new ModelManager(getTypicalContactList(), new UserPrefs(), new TaskRecords());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        GroupPredicate predicate = new GroupPredicate(new Group("CS2101"));
        GroupCommand command = new GroupCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, actualModel, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE), actualModel.getFilteredPersonList());
    }

    @Test
    public void execute_validGroup_noPersonsFound() {
        ContactList ab = new ContactList();
        List<Person> personsInOnlyCs2103T = new ArrayList<>(Arrays.asList(GEORGE, FIONA, BENSON));
        for (Person person : personsInOnlyCs2103T) {
            ab.addPerson(person);
        }
        Model actualModel = new ModelManager(ab, new UserPrefs(), new TaskRecords());
        Model expectedModel = new ModelManager(ab, new UserPrefs(), new TaskRecords());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        GroupPredicate predicate = new GroupPredicate(new Group("CS2101"));
        GroupCommand command = new GroupCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, actualModel, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), actualModel.getFilteredPersonList());
    }
}
