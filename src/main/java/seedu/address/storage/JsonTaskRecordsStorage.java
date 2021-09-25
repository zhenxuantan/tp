package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTaskRecords;

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

    public void saveTaskRecords(ReadOnlyTaskRecords taskRecords, Path filePath) throws IOException {
        requireNonNull(taskRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskRecords(taskRecords), filePath);
    }
}
