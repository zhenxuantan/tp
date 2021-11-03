package sweebook.testutil;

import sweebook.logic.commands.EditTaskCommand.EditTaskDescriptor;
import sweebook.model.group.Group;
import sweebook.model.task.Date;
import sweebook.model.task.Description;
import sweebook.model.task.Priority;
import sweebook.model.task.RecurringFrequency;
import sweebook.model.task.Task;
import sweebook.model.task.TaskType;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setPriority(task.getPriority());
        descriptor.setDescription(task.getDescription());
        descriptor.setTaskType(task.getTaskType());
        descriptor.setDate(task.getDate());
        descriptor.setRecurringFrequency(task.getRecurringFrequency());
        descriptor.setGroup(task.getGroup());
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code TaskType} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskType(String taskType) {
        descriptor.setTaskType(new TaskType(taskType));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code RecurringFrequency} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withRecurringFrequency(String freq) {
        descriptor.setRecurringFrequency(new RecurringFrequency(freq));
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withGroup(String group) {
        descriptor.setGroup(new Group(group));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
