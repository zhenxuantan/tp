package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskRecords;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupPredicate;
import seedu.address.model.person.Person;

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
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new TaskRecords());
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new TaskRecords());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        GroupPredicate predicate = new GroupPredicate(new Group("CS2101"));
        GroupCommand command = new GroupCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, actualModel, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE), actualModel.getFilteredPersonList());
    }

    @Test
    public void execute_validGroup_noPersonsFound() {
        AddressBook ab = new AddressBook();
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
