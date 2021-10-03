package seedu.address.model.task;

public class Deadline extends Task{
    private final String symbol = "[D]";

    public Deadline(Description description, Group group, Date date, TaskType type) {
        super(description, group, type, date);
    }

    @Override
    public String toString() {
        return this.symbol + super.toString() + " (by: " + date.toString() +")";
    }
}
