package sweebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import sweebook.commons.core.LogsCenter;
import sweebook.commons.exceptions.DataConversionException;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.ReadOnlyUserPrefs;
import sweebook.model.UserPrefs;

/**
 * Manages storage of ContactList and TaskRecords data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ContactListStorage contactListStorage;
    private UserPrefsStorage userPrefsStorage;
    private TaskRecordsStorage taskRecordsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ContactListStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ContactListStorage contactListStorage, UserPrefsStorage userPrefsStorage,
                          TaskRecordsStorage taskRecordsStorage) {
        super();
        this.contactListStorage = contactListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.taskRecordsStorage = taskRecordsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ContactList methods ==============================

    @Override
    public Path getContactListFilePath() {
        return contactListStorage.getContactListFilePath();
    }

    @Override
    public Optional<ReadOnlyContactList> readContactList() throws DataConversionException, IOException {
        return readContactList(contactListStorage.getContactListFilePath());
    }

    @Override
    public Optional<ReadOnlyContactList> readContactList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return contactListStorage.readContactList(filePath);
    }

    @Override
    public void saveContactList(ReadOnlyContactList contactList) throws IOException {
        saveContactList(contactList, contactListStorage.getContactListFilePath());
    }

    @Override
    public void saveContactList(ReadOnlyContactList contactList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        contactListStorage.saveContactList(contactList, filePath);
    }


    // ================ TaskRecords methods ==============================

    @Override
    public Path getTaskRecordsFilePath() {
        return taskRecordsStorage.getTaskRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskRecords> readTaskRecords() throws DataConversionException, IOException {
        return readTaskRecords(taskRecordsStorage.getTaskRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskRecords> readTaskRecords(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskRecordsStorage.readTaskRecords(filePath);
    }

    @Override
    public void saveTaskRecords(ReadOnlyTaskRecords taskRecords) throws IOException {
        saveTaskRecords(taskRecords, taskRecordsStorage.getTaskRecordsFilePath());
    }

    @Override
    public void saveTaskRecords(ReadOnlyTaskRecords taskRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskRecordsStorage.saveTaskRecords(taskRecords, filePath);
    }

}
