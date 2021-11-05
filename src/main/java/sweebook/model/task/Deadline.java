package sweebook.model.task;

import sweebook.model.group.Group;

public class Deadline extends Task {
    private final String symbol = "[D]";

    public Deadline(Description description, Group group, Date date, TaskType type,
                    RecurringFrequency recurringFrequency, Priority priority) {
        super(description, group, date, type, recurringFrequency, priority);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (by: " + date.toString() + ")";
    }
}
