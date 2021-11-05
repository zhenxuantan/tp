package sweebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import sweebook.commons.exceptions.DataConversionException;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.ReadOnlyUserPrefs;
import sweebook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ContactListStorage, UserPrefsStorage, TaskRecordsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getContactListFilePath();

    @Override
    Optional<ReadOnlyContactList> readContactList() throws DataConversionException, IOException;

    @Override
    void saveContactList(ReadOnlyContactList contactList) throws IOException;

    @Override
    Path getTaskRecordsFilePath();

    @Override
    Optional<ReadOnlyTaskRecords> readTaskRecords() throws DataConversionException, IOException;

    @Override
    void saveTaskRecords(ReadOnlyTaskRecords taskRecords) throws IOException;

}
