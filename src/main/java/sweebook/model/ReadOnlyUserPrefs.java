package sweebook.model;

import java.nio.file.Path;

import sweebook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getContactListFilePath();

    Path getTaskRecordsFilePath();

}
