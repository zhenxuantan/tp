package sweebook.model.task;

import sweebook.model.group.Group;

public class Todo extends Task {
    private final String symbol = "[T]";

    public Todo(Description description, Group group, Date date, TaskType type,
                RecurringFrequency recurringFrequency, Priority priority) {
        super(description, group, date, type, recurringFrequency, priority);
    }

    @Override
    public String toString() {
        if (date == null) {
            return this.symbol + super.toString();
        } else {
            return this.symbol + super.toString() + " (finish at: " + date.toString() + ")";
        }
    }
}
