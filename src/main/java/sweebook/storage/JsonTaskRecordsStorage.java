package sweebook.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import sweebook.commons.core.LogsCenter;
import sweebook.commons.exceptions.DataConversionException;
import sweebook.commons.exceptions.IllegalValueException;
import sweebook.commons.util.FileUtil;
import sweebook.commons.util.JsonUtil;
import sweebook.model.ReadOnlyTaskRecords;

public class JsonTaskRecordsStorage implements TaskRecordsStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonTaskRecordsStorage.class);

    private Path filePath;

    public JsonTaskRecordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskRecordsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskRecords> readTaskRecords() throws DataConversionException {
        return readTaskRecords(filePath);
    }

    /**
     * Read in task records from filepath specified.
     * @param filePath Data file path
     * @return TaskRecords which may be present or not
     * @throws DataConversionException If there is any data conversion error
     */
    public Optional<ReadOnlyTaskRecords> readTaskRecords(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskRecords> jsonTaskRecords = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskRecords.class);
        if (!jsonTaskRecords.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaskRecords.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskRecords(ReadOnlyTaskRecords taskRecords) throws IOException {
        saveTaskRecords(taskRecords, filePath);
    }

    /**
     * Save in task records into local data.
     * @param taskRecords Task records to be saved
     * @param filePath File path of local data file
     * @throws IOException For any error with IO
     */
    public void saveTaskRecords(ReadOnlyTaskRecords taskRecords, Path filePath) throws IOException {
        requireNonNull(taskRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskRecords(taskRecords), filePath);
    }
}
