package sweebook.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import sweebook.commons.core.GuiSettings;
import sweebook.model.person.Person;
import sweebook.model.task.SortTaskComparator;
import sweebook.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the contact list file path.
     */
    Path getContactListFilePath();

    /**
     * Sets the contact list file path.
     */
    void setContactListFilePath(Path contactListFilePath);

    /**
     * Replaces contact list data with the data in {@code contactList}.
     */
    void setContactList(ReadOnlyContactList contactList);

    /** Returns the ContactList */
    ReadOnlyContactList getContactList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the contact list.
     */
    boolean hasPerson(Person person);

    /**
     * Returns the message constraint that comes from {@code person} having the same equality
     * (as defined in Person#isSamePerson) with another person in the contact list.
     * PRECONDITION: there is another person in the contact list that returns true with {@code person} when called by
     * Person#isSamePerson.
     */
    String getSamePersonConstraintMessage(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the contact list.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the contact list.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the contact list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the contact list.
     */
    void setPerson(Person target, Person editedPerson);

    ReadOnlyTaskRecords getTaskList();

    void addTask(Task toAdd);

    Task deleteTask(Task task);

    void setTask(Task target, Task editedTask);

    void doneTask(Task task);

    void undoDoneTask(Task task);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates TaskList according to the specified SortTaskComparator
     */
    void updateSortedTaskList(SortTaskComparator comparator);

    ObservableList<Task> getTasks();

    void updateFilteredTaskList(Predicate<Task> predicate);
}
