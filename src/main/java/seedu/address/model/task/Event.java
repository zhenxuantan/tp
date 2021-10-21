package seedu.address.model.task;

import seedu.address.model.group.Group;

public class Event extends Task {
    private final String symbol = "[E]";

    public Event(Description description, Group group, Date date, TaskType type,
                 RecurringFrequency recurringFrequency) {
        super(description, group, date, type, recurringFrequency);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (at: " + date.toString() + ")";
    }
}
