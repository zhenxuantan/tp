package seedu.address.model.task;

import seedu.address.model.group.Group;

public class Todo extends Task {
    private final String symbol = "[T]";

    public Todo(Description description, Group group, TaskType type, Date date) {
        super(description, group, type, date);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString();
    }
}
