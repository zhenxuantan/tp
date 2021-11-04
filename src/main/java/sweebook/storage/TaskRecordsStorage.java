package sweebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import sweebook.commons.exceptions.DataConversionException;
import sweebook.model.ReadOnlyTaskRecords;

public interface TaskRecordsStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getTaskRecordsFilePath();

    /**
     * Returns ContactList data as a {@link ReadOnlyTaskRecords}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskRecords> readTaskRecords() throws DataConversionException, IOException;

    Optional<ReadOnlyTaskRecords> readTaskRecords(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskRecords} to the storage.
     * @param taskRecords cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskRecords(ReadOnlyTaskRecords taskRecords) throws IOException;

    void saveTaskRecords(ReadOnlyTaskRecords taskRecords, Path filePath) throws IOException;
}
