package seedu.address.model.task;

public class Event extends Task {
    private final String symbol = "[E]";

    public Event(Description description, Group group, Date date, TaskType type) {
        super(description, group, type, date);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (at: " + date.toString() +")";
    }
}
