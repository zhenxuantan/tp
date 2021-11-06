package sweebook.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import sweebook.commons.core.GuiSettings;
import sweebook.logic.commands.CommandResult;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.Model;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.person.Person;
import sweebook.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ContactList.
     *
     * @see Model#getContactList()
     */
    ReadOnlyContactList getContactList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the contact list file path.
     */
    Path getContactListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableList<Task> getFilteredTaskList();
}
