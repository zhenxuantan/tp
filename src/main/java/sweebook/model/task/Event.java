package sweebook.model.task;

import sweebook.model.group.Group;

public class Event extends Task {
    private final String symbol = "[E]";

    public Event(Description description, Group group, Date date, TaskType type,
                 RecurringFrequency recurringFrequency, Priority priority) {
        super(description, group, date, type, recurringFrequency, priority);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (at: " + date.toString() + ")";
    }
}
