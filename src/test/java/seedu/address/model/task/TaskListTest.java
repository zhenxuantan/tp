package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalTasks.DEADLINE1;
import static seedu.address.testutil.TypicalTasks.DEADLINE2;
import static seedu.address.testutil.TypicalTasks.EVENT1;
import static seedu.address.testutil.TypicalTasks.TODO1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.testutil.TypicalTasks;

class TaskListTest {
    private final ArrayList<Task> descAscending = new ArrayList<>(Arrays.asList(DEADLINE1, DEADLINE2, EVENT1, TODO1));
    private final ArrayList<Task> descDescending = new ArrayList<>(Arrays.asList(TODO1, EVENT1, DEADLINE2, DEADLINE1));
    private final ArrayList<Task> dateAscending = new ArrayList<>(Arrays.asList(DEADLINE1, EVENT1, DEADLINE2, TODO1));
    private final ArrayList<Task> dateDescending = new ArrayList<>(Arrays.asList(TODO1, DEADLINE2, EVENT1, DEADLINE1));
    private final ArrayList<Task> addedAscending = new ArrayList<>(Arrays.asList(DEADLINE1, EVENT1, TODO1, DEADLINE2));
    private final ArrayList<Task> addedDescending = new ArrayList<>(Arrays.asList(DEADLINE2, TODO1, EVENT1, DEADLINE1));
    private final ArrayList<Task> groupAscending = new ArrayList<>(Arrays.asList(DEADLINE1, TODO1, EVENT1, DEADLINE2));
    private final ArrayList<Task> groupDescending = new ArrayList<>(Arrays.asList(EVENT1, DEADLINE2, DEADLINE1, TODO1));

    @Test
    void sortTask() {
        TaskList actual = new TaskList();
        actual.setTasks(TypicalTasks.getTypicalTasks());
        BiFunction<String, String, SortTaskCriterion> srt = SortTaskCriterion::new;
        Function<ArrayList<Task>, ObservableList<Task>> ol = FXCollections::observableArrayList;
        assertEquals(actual.sortTask(srt.apply("desc", "a")), ol.apply(descAscending));
        assertEquals(actual.sortTask(srt.apply("desc", "d")), ol.apply(descDescending));
        assertEquals(actual.sortTask(srt.apply("date", "a")), ol.apply(dateAscending));
        assertEquals(actual.sortTask(srt.apply("date", "d")), ol.apply(dateDescending));
        assertEquals(actual.sortTask(srt.apply("added", "a")), ol.apply(addedAscending));
        assertEquals(actual.sortTask(srt.apply("added", "d")), ol.apply(addedDescending));
        assertEquals(actual.sortTask(srt.apply("group", "a")), ol.apply(groupAscending));
        assertEquals(actual.sortTask(srt.apply("group", "d")), ol.apply(groupDescending));
    }

}
