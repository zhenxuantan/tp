package sweebook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import sweebook.commons.core.Config;
import sweebook.commons.core.LogsCenter;
import sweebook.commons.core.Version;
import sweebook.commons.exceptions.DataConversionException;
import sweebook.commons.util.ConfigUtil;
import sweebook.commons.util.StringUtil;
import sweebook.logic.Logic;
import sweebook.logic.LogicManager;
import sweebook.model.ContactList;
import sweebook.model.Model;
import sweebook.model.ModelManager;
import sweebook.model.ReadOnlyContactList;
import sweebook.model.ReadOnlyTaskRecords;
import sweebook.model.ReadOnlyUserPrefs;
import sweebook.model.TaskRecords;
import sweebook.model.UserPrefs;
import sweebook.model.util.SampleDataUtil;
import sweebook.storage.ContactListStorage;
import sweebook.storage.JsonContactListStorage;
import sweebook.storage.JsonTaskRecordsStorage;
import sweebook.storage.JsonUserPrefsStorage;
import sweebook.storage.Storage;
import sweebook.storage.StorageManager;
import sweebook.storage.TaskRecordsStorage;
import sweebook.storage.UserPrefsStorage;
import sweebook.ui.Ui;
import sweebook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing SWEe-Book ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ContactListStorage contactListStorage = new JsonContactListStorage(userPrefs.getContactListFilePath());
        TaskRecordsStorage taskRecordsStorage = new JsonTaskRecordsStorage(userPrefs.getTaskRecordsFilePath());
        storage = new StorageManager(contactListStorage, userPrefsStorage, taskRecordsStorage);

        initLogging(config);

        ArrayList<String> warnings = new ArrayList<>();

        model = initModelManager(storage, userPrefs, warnings);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, warnings);

    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s contact list, task records,
     * and {@code userPrefs}. <br>
     * The data from the sample contact list or task records will be used instead if {@code storage}'s
     * contact list or task records are not found, or an empty contact list or task records will be used
     * instead if errors occur when reading {@code storage}'s contact list or task records.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs, ArrayList<String> warnings) {
        Optional<ReadOnlyContactList> contactListOptional;
        Optional<ReadOnlyTaskRecords> taskRecordsOptional;
        ReadOnlyContactList initialData;
        ReadOnlyTaskRecords initialTasks;
        try {
            contactListOptional = storage.readContactList();
            if (!contactListOptional.isPresent()) {
                String message = "Contact list data file not found. Will be starting with a sample contact list";
                warnings.add(message);
                logger.info(message);
            }
            initialData = contactListOptional.orElseGet(SampleDataUtil::getSampleContactList);
        } catch (DataConversionException e) {
            String message = "Contact list data file is not in the correct format. "
                + "Will be starting with an empty contact list";
            warnings.add(message);
            logger.warning(message);
            initialData = new ContactList();
        } catch (IOException e) {
            String message = "Problem while reading from the contact list data file. "
                + "Will be starting with an empty contact list";
            warnings.add(message);
            logger.warning(message);
            initialData = new ContactList();
        }

        try {
            taskRecordsOptional = storage.readTaskRecords();
            if (!taskRecordsOptional.isPresent()) {
                String message = "Task list data file not found. Will be starting with a sample task list";
                warnings.add(message);
                logger.info(message);
            }
            initialTasks = taskRecordsOptional.orElseGet(SampleDataUtil::getSampleTaskRecords);
        } catch (DataConversionException e) {
            String message = "Task list data file is not in the correct format. "
                + "Will be starting with an empty task list";
            warnings.add(message);
            logger.warning(message);
            initialTasks = new TaskRecords();
        } catch (IOException e) {
            String message = "Problem while reading from the task list data file. "
                + "Will be starting with an empty task list";
            warnings.add(message);
            logger.warning(message);
            initialTasks = new TaskRecords();
        }

        return new ModelManager(initialData, userPrefs, initialTasks);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty SWEe-book");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting SWEe-Book " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping SWEe-Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
