package sweebook.model;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import sweebook.commons.core.GuiSettings;
import sweebook.commons.core.LogsCenter;
import sweebook.model.person.Person;
import sweebook.model.task.SortTaskComparator;
import sweebook.model.task.Task;

/**
 * Represents the in-memory model of the SWEe-book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ContactList contactList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTaskList;
    private final SortedList<Task> sortedTaskList;
    private final TaskRecords tasks;

    /**
     * Initializes a ModelManager with the given contactList and userPrefs.
     */
    public ModelManager(ReadOnlyContactList contactList, ReadOnlyUserPrefs userPrefs, ReadOnlyTaskRecords tasks) {
        super();
        requireAllNonNull(contactList, userPrefs, tasks);

        logger.fine("Initializing with contact list: " + contactList + " and user prefs " + userPrefs);

        this.contactList = new ContactList(contactList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.tasks = new TaskRecords(tasks);
        this.tasks.updateRecurringTasks();
        filteredPersons = new FilteredList<>(this.contactList.getPersonList());
        filteredTaskList = new FilteredList<>(this.tasks.getTaskList());
        sortedTaskList = new SortedList<>(filteredTaskList);
    }

    public ModelManager() {
        this(new ContactList(), new UserPrefs(), new TaskRecords());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getContactListFilePath() {
        return userPrefs.getContactListFilePath();
    }

    @Override
    public void setContactListFilePath(Path contactListFilePath) {
        requireNonNull(contactListFilePath);
        userPrefs.setContactListFilePath(contactListFilePath);
    }

    //=========== ContactList ================================================================================

    @Override
    public void setContactList(ReadOnlyContactList contactList) {
        this.contactList.resetData(contactList);
    }

    @Override
    public ReadOnlyContactList getContactList() {
        return contactList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return contactList.hasPerson(person);
    }

    @Override
    public String getSamePersonConstraintMessage(Person person) {
        requireNonNull(person);
        return contactList.getSamePersonConstraintMessage(person);
    }

    @Override
    public void deletePerson(Person target) {
        contactList.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        contactList.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        contactList.setPerson(target, editedPerson);
    }

    //=========== TaskRecords ================================================================================

    public void setTaskRecords(ReadOnlyTaskRecords records) {
        this.tasks.resetData(records);
    }

    @Override
    public ReadOnlyTaskRecords getTaskList() {
        return tasks;
    }

    @Override
    public void addTask(Task toAdd) {
        tasks.addTask(toAdd);
    }

    @Override
    public Task deleteTask(Task task) {
        return tasks.deleteTask(task);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        tasks.setTask(target, editedTask);
    }

    @Override
    public void doneTask(Task task) {
        tasks.doneTask(task);
    }

    @Override
    public void undoDoneTask(Task task) {
        tasks.undoDoneTask(task);
    }


    //=========== Task List Accessors =============================================================

    @Override
    public ObservableList<Task> getTasks() {
        return sortedTaskList;
    }

    @Override
    public void updateSortedTaskList(SortTaskComparator comparator) {
        requireNonNull(comparator);
        filteredTaskList.setPredicate(null);
        sortedTaskList.setComparator(comparator);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        sortedTaskList.setComparator(null);
        filteredTaskList.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedContactList}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return contactList.equals(other.contactList)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
