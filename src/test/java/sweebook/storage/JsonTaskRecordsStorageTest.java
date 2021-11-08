package sweebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static sweebook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sweebook.commons.exceptions.DataConversionException;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.TaskRecords;
import sweebook.testutil.TypicalTasks;

public class JsonTaskRecordsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskRecordsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaskRecords_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskRecords(null));
    }

    private java.util.Optional<ReadOnlyTaskRecords> readTaskRecords(String filePath) throws Exception {
        return new JsonTaskRecordsStorage(Paths.get(filePath)).readTaskRecords(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskRecords("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTaskRecords("notJsonFormatTaskRecords.json"));
    }

    @Test
    public void readTaskRecords_invalidTaskTaskRecords_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTaskRecords("invalidTaskTaskRecords.json"));
    }

    @Test
    public void readTaskRecords_invalidAndValidTaskTaskRecords_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTaskRecords("invalidAndValidTaskTaskRecords.json"));
    }

    @Test
    public void readAndSaveTaskRecords_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskRecords.json");
        TaskRecords original = TypicalTasks.getTypicalTaskRecords();
        JsonTaskRecordsStorage jsonTaskRecordsStorage = new JsonTaskRecordsStorage(filePath);

        // Save in new file and read back
        jsonTaskRecordsStorage.saveTaskRecords(original, filePath);
        ReadOnlyTaskRecords readBack = jsonTaskRecordsStorage.readTaskRecords(filePath).get();
        assertEquals(original, new TaskRecords(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(TypicalTasks.SECOND_EVENT);
        original.deleteTask(TypicalTasks.FIRST_DEADLINE);
        jsonTaskRecordsStorage.saveTaskRecords(original, filePath);
        readBack = jsonTaskRecordsStorage.readTaskRecords(filePath).get();
        assertEquals(original, new TaskRecords(readBack));

        // Save and read without specifying file path
        original.addTask(TypicalTasks.SECOND_TODO);
        jsonTaskRecordsStorage.saveTaskRecords(original); // file path not specified
        readBack = jsonTaskRecordsStorage.readTaskRecords().get(); // file path not specified
        assertEquals(original, new TaskRecords(readBack));
    }
}
