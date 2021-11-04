package sweebook.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import sweebook.commons.core.GuiSettings;
import sweebook.commons.core.LogsCenter;
import sweebook.logic.commands.Command;
import sweebook.logic.commands.CommandResult;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.logic.parser.SweeBookParser;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.Model;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.person.Person;
import sweebook.model.task.Task;
import sweebook.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SweeBookParser sweeBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        sweeBookParser = new SweeBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = sweeBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveContactList(model.getContactList());
            storage.saveTaskRecords(model.getTaskList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyContactList getContactList() {
        return model.getContactList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    public ReadOnlyTaskRecords getTaskRecords() {
        return model.getTaskList();
    }

    public ObservableList<Task> getFilteredTaskList() {
        return model.getTasks();
    }

    @Override
    public Path getContactListFilePath() {
        return model.getContactListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
