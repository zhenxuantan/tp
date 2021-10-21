package seedu.address.model.task;

import seedu.address.model.group.Group;

public class Todo extends Task {
    private final String symbol = "[T]";

    public Todo(Description description, Group group, Date date, TaskType type,
                RecurringFrequency recurringFrequency, Priority priority) {
        super(description, group, date, type, recurringFrequency, priority);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (finish at: " + date.toString() + ")";
    }
}
