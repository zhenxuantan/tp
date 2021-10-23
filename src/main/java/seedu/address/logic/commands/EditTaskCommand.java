package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKTYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.RecurringFrequency;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;

public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "editTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the specified task "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values. To remove recurring frequencies,"
            + " specify \"" + PREFIX_RECURRING_FREQUENCY + "none\".\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_TASKTYPE + "TASKTYPE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_RECURRING_FREQUENCY + "RECURRING_FREQUENCY] "
            + "[" + PREFIX_PRIORITY + "PRIORITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "OP2 rehearsal "
            + PREFIX_GROUP + "CS2101 "
            + PREFIX_TASKTYPE + "Event "
            + PREFIX_DATE + "2021-11-11 ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_MISSING_DATE = "Date is compulsory for deadlines and events, "
            + "or if the task is recurring.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the person in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit,
                                         EditTaskDescriptor editTaskDescriptor) throws CommandException {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        Group updatedGroup = editTaskDescriptor.getGroup().orElse(taskToEdit.getGroup());
        TaskType updatedTaskType = editTaskDescriptor.getTaskType().orElse(taskToEdit.getTaskType());
        RecurringFrequency updatedRecurringFrequency = editTaskDescriptor.getRecurringFrequency()
                .orElse(taskToEdit.getRecurringFrequency());
        Date updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        Priority updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());

        // date is compulsory if the task type is deadline or event, OR if the task is recurring
        if ((updatedTaskType.taskType.equals("deadline")
                || updatedTaskType.taskType.equals("event")
                || updatedRecurringFrequency.isRecurring())
                && isNull(updatedDate)) {
            throw new CommandException(MESSAGE_MISSING_DATE);
        }

        Task updatedTask = new Task(updatedDescription, updatedGroup, updatedDate,
                updatedTaskType, updatedRecurringFrequency, updatedPriority);

        if (taskToEdit.isDone()) {
            updatedTask.markAsDone();
        }

        return updatedTask;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getTasks();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        Task personToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(personToEdit, editTaskDescriptor);

        model.setTask(personToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Description description;
        private Group group;
        private TaskType taskType;
        private Date date;
        private RecurringFrequency recurringFrequency;
        private Priority priority;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setDescription(toCopy.description);
            setGroup(toCopy.group);
            setTaskType(toCopy.taskType);
            setDate(toCopy.date);
            setRecurringFrequency(toCopy.recurringFrequency);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description,
                    group, taskType, date, recurringFrequency, priority);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public Optional<TaskType> getTaskType() {
            return Optional.ofNullable(taskType);
        }

        public void setTaskType(TaskType type) {
            this.taskType = type;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<RecurringFrequency> getRecurringFrequency() {
            return Optional.ofNullable(recurringFrequency);
        }

        public void setRecurringFrequency(RecurringFrequency recurringFrequency) {
            this.recurringFrequency = recurringFrequency;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getGroup().equals(e.getGroup())
                    && getTaskType().equals(e.getTaskType())
                    && getDate().equals(e.getDate())
                    && getRecurringFrequency().equals(e.getRecurringFrequency())
                    && getPriority().equals(e.getPriority());
        }
    }
}
