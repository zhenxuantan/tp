package sweebook.ui;

import static java.util.Objects.isNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import sweebook.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label taskType;
    @FXML
    private Label done;
    @FXML
    private Label date;
    @FXML
    private Label group;
    @FXML
    private Label recurringFrequency;
    @FXML
    private Label priority;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().toString());
        taskType.setText(task.getTaskType().toString());
        done.setText(task.getStatusIcon());
        date.setText(isNull(task.getDate()) ? "" : task.getDate().toString());
        group.setText(task.getGroup().toString());
        recurringFrequency.setText(task.getRecurringFrequency().display());
        priority.setText(task.getPriorityIcon());
        if (task.getPriorityIcon().equals("!")) {
            priority.setStyle("-fx-text-fill: white; -fx-background-color: green");
        } else if (task.getPriorityIcon().equals("!!")) {
            priority.setStyle("-fx-text-fill: black; -fx-background-color: orange");
        } else {
            priority.setStyle("-fx-text-fill: white; -fx-background-color: maroon");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
