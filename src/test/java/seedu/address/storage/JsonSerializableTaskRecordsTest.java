package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TaskRecords;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableTaskRecordsTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaskRecordsTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTaskTaskRecords.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskTaskRecords.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableTaskRecords dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
            JsonSerializableTaskRecords.class).get();
        TaskRecords taskRecordsFromFile = dataFromFile.toModelType();
        TaskRecords typicalTaskTaskRecords = TypicalTasks.getTypicalTaskRecords();
        assertEquals(taskRecordsFromFile, typicalTaskTaskRecords);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskRecords dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
            JsonSerializableTaskRecords.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
