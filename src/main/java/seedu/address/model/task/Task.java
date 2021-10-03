package seedu.address.model.task;

public class Task {
    protected Description description;
    protected Group group;
    protected TaskType type;
    protected Date date;
    protected boolean isDone;

    /**
     * Constructor for Task.
     * @param description Description of task
     * @param group Group of task
     * @param type Type of task (Deadline, Event, Todo)
     * @param date Date of task
     */
    public Task(Description description, Group group, TaskType type, Date date) {
        this.description = description;
        this.group = group;
        this.type = type;
        this.date = date;
        this.isDone = false;
    }

    /**
     * Returns X if isDone true.
     *
     * @return "X" if isDone true.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    public Description getDescription() {
        return description;
    }

    public Group getGroup() {
        return group;
    }

    public Date getDate() {
        return date;
    }
    public TaskType getTaskType() {
        return type;
    }

    /**
     * Marks task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return String.format("%1$s %2$s", getStatusIcon(), description.toString());
    }
}
