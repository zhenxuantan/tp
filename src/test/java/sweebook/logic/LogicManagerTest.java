package sweebook.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sweebook.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static sweebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static sweebook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static sweebook.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sweebook.logic.commands.AddCommand;
import sweebook.logic.commands.CommandResult;
import sweebook.logic.commands.ListCommand;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.person.Person;
import sweebook.storage.JsonContactListStorage;
import sweebook.storage.JsonTaskRecordsStorage;
import sweebook.storage.JsonUserPrefsStorage;
import sweebook.storage.StorageManager;
import sweebook.testutil.PersonBuilder;
import sweebook.testutil.TypicalPersons;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonContactListStorage contactListStorage =
                new JsonContactListStorage(temporaryFolder.resolve("contactList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonTaskRecordsStorage taskRecordsStorage =
                new JsonTaskRecordsStorage(temporaryFolder.resolve("taskRecords.json"));
        StorageManager storage = new StorageManager(contactListStorage, userPrefsStorage, taskRecordsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonContactListIoExceptionThrowingStub
        JsonContactListStorage contactListStorage =
                new JsonContactListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionContactList.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonTaskRecordsStorage taskRecordsStorage =
                new JsonTaskRecordsStorage(temporaryFolder.resolve("taskRecords.json"));
        StorageManager storage = new StorageManager(contactListStorage, userPrefsStorage, taskRecordsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GROUP_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY;
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getContactList(), new UserPrefs(), new TaskRecords());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonContactListIoExceptionThrowingStub extends JsonContactListStorage {
        private JsonContactListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveContactList(ReadOnlyContactList contactList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
