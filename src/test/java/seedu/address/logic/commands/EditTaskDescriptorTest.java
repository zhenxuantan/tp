package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECURRING_FREQUENCY_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TYPE_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_TODO);
        assertTrue(DESC_TODO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TODO.equals(DESC_TODO));

        // null -> returns false
        assertFalse(DESC_TODO.equals(null));

        // different types -> returns false
        assertFalse(DESC_TODO.equals(5));

        // different values -> returns false
        assertFalse(DESC_TODO.equals(DESC_DEADLINE));

        // different description -> return false
        EditTaskDescriptor editedTodo =
                new EditTaskDescriptorBuilder(DESC_TODO).withDescription(VALID_DESCRIPTION_DEADLINE).build();
        assertFalse(DESC_TODO.equals(editedTodo));

        // different group -> return false
        editedTodo = new EditTaskDescriptorBuilder(DESC_TODO).withGroup(VALID_GROUP_CS2103T).build();
        assertFalse(DESC_TODO.equals(editedTodo));

        // different priority -> return false
        editedTodo = new EditTaskDescriptorBuilder(DESC_TODO).withPriority(VALID_PRIORITY_HIGH).build();
        assertFalse(DESC_TODO.equals(editedTodo));

        // different recurring frequency -> return false
        editedTodo = new EditTaskDescriptorBuilder(DESC_TODO)
                .withRecurringFrequency(VALID_RECURRING_FREQUENCY_YEAR).build();
        assertFalse(DESC_TODO.equals(editedTodo));

        // different date -> return false
        editedTodo =
                new EditTaskDescriptorBuilder(DESC_TODO).withDate(VALID_DATE_3).build();
        assertFalse(DESC_TODO.equals(editedTodo));

        // different task type -> return false
        editedTodo =
                new EditTaskDescriptorBuilder(DESC_TODO).withTaskType(VALID_TASK_TYPE_EVENT).build();
        assertFalse(DESC_TODO.equals(editedTodo));
    }

}
