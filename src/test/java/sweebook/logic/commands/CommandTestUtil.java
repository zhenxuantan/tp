package sweebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.logic.parser.CliSyntax.PREFIX_DATE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static sweebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sweebook.logic.parser.CliSyntax.PREFIX_GITHUB;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_NAME;
import static sweebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static sweebook.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static sweebook.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static sweebook.logic.parser.CliSyntax.PREFIX_TASKTYPE;
import static sweebook.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static sweebook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sweebook.commons.core.index.Index;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.person.NameContainsKeywordsPredicate;
import sweebook.model.person.Person;
import sweebook.model.task.FilterTaskPredicate;
import sweebook.model.task.Task;
import sweebook.testutil.EditPersonDescriptorBuilder;
import sweebook.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    // contact list
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_GROUP_AMY_CS2103T = "CS2103T";
    public static final String VALID_GROUP_AMY_CS2101 = "CS2101";
    public static final String VALID_GROUP_BOB = "CS2101";
    public static final String VALID_TELEGRAM_AMY = "amy";
    public static final String VALID_TELEGRAM_BOB = "bob";
    public static final String VALID_GITHUB_AMY = "amy";
    public static final String VALID_GITHUB_BOB = "bob";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String GROUP_DESC_AMY = " " + PREFIX_GROUP + VALID_GROUP_AMY_CS2103T
            + " " + PREFIX_GROUP + VALID_GROUP_AMY_CS2101;
    public static final String GROUP_DESC_BOB = " " + PREFIX_GROUP + VALID_GROUP_BOB;
    public static final String TELEGRAM_DESC_AMY = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_AMY;
    public static final String TELEGRAM_DESC_BOB = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_BOB;
    public static final String GITHUB_DESC_AMY = " " + PREFIX_GITHUB + VALID_GITHUB_AMY;
    public static final String GITHUB_DESC_BOB = " " + PREFIX_GITHUB + VALID_GITHUB_BOB;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "CS2122"; // group must be cs2103t or cs2101

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withGroups(VALID_GROUP_AMY_CS2103T, VALID_GROUP_AMY_CS2101)
                .withTelegram(VALID_TELEGRAM_AMY).withGitHub(VALID_GITHUB_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withGroups(VALID_GROUP_BOB).withTelegram(VALID_TELEGRAM_BOB).withGitHub(VALID_GITHUB_BOB).build();
    }

    // tasks
    public static final String VALID_DESCRIPTION_TODO = "Update DG";
    public static final String VALID_DESCRIPTION_DEADLINE = "v1.3 milestone";
    public static final String VALID_DESCRIPTION_EVENT = "Project meeting";
    public static final String VALID_PRIORITY_LOW = "low";
    public static final String VALID_PRIORITY_MED = "med";
    public static final String VALID_PRIORITY_HIGH = "high";
    public static final String VALID_TASK_TYPE_TODO = "todo";
    public static final String VALID_TASK_TYPE_DEADLINE = "deadline";
    public static final String VALID_TASK_TYPE_EVENT = "event";
    public static final String VALID_DATE_1 = "2021-11-11";
    public static final String VALID_DATE_2 = "2021-11-12";
    public static final String VALID_DATE_3 = "2021-11-13";
    public static final String VALID_RECURRING_FREQUENCY_WEEK = "week";
    public static final String VALID_RECURRING_FREQUENCY_MONTH = "month";
    public static final String VALID_RECURRING_FREQUENCY_YEAR = "year";
    public static final String VALID_GROUP_CS2103T = "CS2103T";
    public static final String VALID_GROUP_CS2101 = "CS2101";

    public static final String DESCRIPTION_DESC_TODO = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TODO;
    public static final String DESCRIPTION_DESC_DEADLINE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DEADLINE;
    public static final String DESCRIPTION_DESC_EVENT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_EVENT;
    public static final String PRIORITY_DESC_TODO = " " + PREFIX_PRIORITY + VALID_PRIORITY_LOW;
    public static final String PRIORITY_DESC_DEADLINE = " " + PREFIX_PRIORITY + VALID_PRIORITY_MED;
    public static final String PRIORITY_DESC_EVENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_HIGH;
    public static final String TASK_TYPE_DESC_TODO = " " + PREFIX_TASKTYPE + VALID_TASK_TYPE_TODO;
    public static final String TASK_TYPE_DESC_DEADLINE = " " + PREFIX_TASKTYPE + VALID_TASK_TYPE_DEADLINE;
    public static final String TASK_TYPE_DESC_EVENT = " " + PREFIX_TASKTYPE + VALID_TASK_TYPE_EVENT;
    public static final String DATE_DESC_TODO = " " + PREFIX_DATE + VALID_DATE_1;
    public static final String DATE_DESC_DEADLINE = " " + PREFIX_DATE + VALID_DATE_2;
    public static final String DATE_DESC_EVENT = " " + PREFIX_DATE + VALID_DATE_3;
    public static final String RECURRING_FREQUENCY_DESC_TODO = " " + PREFIX_RECURRING_FREQUENCY
            + VALID_RECURRING_FREQUENCY_WEEK;
    public static final String RECURRING_FREQUENCY_DESC_DEADLINE = " " + PREFIX_RECURRING_FREQUENCY
            + VALID_RECURRING_FREQUENCY_MONTH;
    public static final String RECURRING_FREQUENCY_DESC_EVENT = " " + PREFIX_RECURRING_FREQUENCY
            + VALID_RECURRING_FREQUENCY_YEAR;
    public static final String GROUP_DESC_TODO = " " + PREFIX_GROUP + VALID_GROUP_CS2101;
    public static final String GROUP_DESC_DEADLINE = " " + PREFIX_GROUP + VALID_GROUP_CS2103T;
    public static final String GROUP_DESC_EVENT = " " + PREFIX_GROUP + VALID_GROUP_CS2101;

    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // empty description not allowed
    // priority is only low, med, or high
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "extreme";
    public static final String INVALID_TASK_TYPE_DESC = " " + PREFIX_TASKTYPE + "aaa"; // should be todo/deadline/event
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2021-20-20"; // invalid month
    // should be only week/month/year
    public static final String INVALID_RECURRING_FREQUENCY_DESC = " " + PREFIX_RECURRING_FREQUENCY + "biweekly";

    public static final EditTaskCommand.EditTaskDescriptor DESC_TODO;
    public static final EditTaskCommand.EditTaskDescriptor DESC_DEADLINE;
    public static final EditTaskCommand.EditTaskDescriptor DESC_EVENT;
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    static {
        DESC_TODO = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TODO).withDate(VALID_DATE_1)
                .withPriority(VALID_PRIORITY_LOW).withTaskType(VALID_TASK_TYPE_TODO).withGroup(VALID_GROUP_CS2101)
                .withRecurringFrequency(VALID_RECURRING_FREQUENCY_WEEK).build();
        DESC_DEADLINE = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEADLINE)
                .withDate(VALID_DATE_2)
                .withPriority(VALID_PRIORITY_MED).withTaskType(VALID_TASK_TYPE_DEADLINE).withGroup(VALID_GROUP_CS2103T)
                .withRecurringFrequency(VALID_RECURRING_FREQUENCY_MONTH).build();
        DESC_EVENT = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_EVENT).withDate(VALID_DATE_3)
                .withPriority(VALID_PRIORITY_HIGH).withTaskType(VALID_TASK_TYPE_EVENT).withGroup(VALID_GROUP_CS2101)
                .withRecurringFrequency(VALID_RECURRING_FREQUENCY_YEAR).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the contact list, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ContactList expectedContactList = new ContactList(actualModel.getContactList());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedContactList, actualModel.getContactList());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s contact list.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show the task at the given {@code targetIndex} in the
     * {@code model}'s task records.
     * TODO: Implement duplicate detection for tasks so this method only shows a unique task
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getTasks().size());

        Task task = model.getTasks().get(targetIndex.getZeroBased());
        model.updateFilteredTaskList(
                new FilterTaskPredicate(PREFIX_DESCRIPTION.getPrefix()
                        + task.getDescription().description));

        assertEquals(1, model.getTasks().size());
    }

}
