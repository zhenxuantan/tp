package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;
import static sweebook.testutil.TypicalTasks.FIRST_DEADLINE;
import static sweebook.testutil.TypicalTasks.FIRST_EVENT;
import static sweebook.testutil.TypicalTasks.FIRST_TODO;
import static sweebook.testutil.TypicalTasks.SECOND_DEADLINE;
import static sweebook.testutil.TypicalTasks.SECOND_TODO;
import static sweebook.testutil.TypicalTasks.SECOND_TODO_WITH_CAPS;
import static sweebook.testutil.TypicalTasks.THIRD_TODO;

import org.junit.jupiter.api.Test;

import sweebook.model.group.Group;
import sweebook.testutil.TaskBuilder;

class TaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null, null, null,
            null, null, null));
    }

    @Test
    public void constructor_dateIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(new Description("deadline"), new Group("CS2101"),
            null, new TaskType("deadline"), new RecurringFrequency("none"), new Priority("low")));
        assertThrows(NullPointerException.class, () -> new Task(new Description("event"), new Group("CS2101"),
            null, new TaskType("event"), new RecurringFrequency("none"), new Priority("low")));
        assertNull(new Task(new Description("todo"), new Group("CS2101"),
            null, new TaskType("todo"), new RecurringFrequency("none"), new Priority("low")).getDate());
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidName1 = "";
        String invalidName2 = "extreme";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidName1));
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidName2));
    }

    @Test
    void getStatusIcon() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        // tasks that are not done
        assertEquals("[ ]", deadline1.getStatusIcon());
        assertEquals("[ ]", event1.getStatusIcon());
        assertEquals("[ ]", todo1.getStatusIcon());

        deadline1.markAsDone();
        event1.markAsDone();
        todo1.markAsDone();

        // tasks that are done
        assertEquals("[X]", deadline1.getStatusIcon());
        assertEquals("[X]", event1.getStatusIcon());
        assertEquals("[X]", todo1.getStatusIcon());
    }

    @Test
    void getDescription() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals(new Description("Deadline 1"), deadline1.getDescription());
        assertEquals(new Description("event 1"), event1.getDescription());
        assertEquals(new Description("todo 1"), todo1.getDescription());
    }

    @Test
    void getGroup() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals(new Group("cs2101"), deadline1.getGroup());
        assertEquals(new Group("cs2103t"), event1.getGroup());
        assertEquals(new Group("cs2101"), todo1.getGroup());
    }

    @Test
    void getDate() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();
        Task todo3 = new TaskBuilder(THIRD_TODO).build();

        assertEquals(new Date("2021-12-03"), deadline1.getDate());
        assertEquals(new Date("2022-02-03"), event1.getDate());
        assertEquals(new Date("2022-10-29"), todo1.getDate());
        assertNull(todo3.getDate());
    }

    @Test
    void getTaskType() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals(new TaskType("deadline"), deadline1.getTaskType());
        assertEquals(new TaskType("event"), event1.getTaskType());
        assertEquals(new TaskType("todo"), todo1.getTaskType());
    }

    @Test
    void getRecurringFrequency() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();
        Task deadline2 = new TaskBuilder(SECOND_DEADLINE).build();

        assertEquals(new RecurringFrequency("none"), deadline1.getRecurringFrequency());
        assertEquals(new RecurringFrequency("year"), event1.getRecurringFrequency());
        assertEquals(new RecurringFrequency("week"), todo1.getRecurringFrequency());
        assertEquals(new RecurringFrequency("month"), deadline2.getRecurringFrequency());
    }

    @Test
    void getPriority() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals(new Priority("med"), deadline1.getPriority());
        assertEquals(new Priority("low"), event1.getPriority());
        assertEquals(new Priority("high"), todo1.getPriority());
    }

    @Test
    void getPriorityIcon() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals("!!", deadline1.getPriorityIcon());
        assertEquals("!", event1.getPriorityIcon());
        assertEquals("!!!", todo1.getPriorityIcon());
    }

    @Test
    void isDone() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        // tasks that are not done
        assertFalse(deadline1.isDone);
        assertFalse(event1.isDone);
        assertFalse(todo1.isDone);

        deadline1.markAsDone();
        event1.markAsDone();
        todo1.markAsDone();

        // tasks that are done
        assertTrue(deadline1.isDone);
        assertTrue(event1.isDone);
        assertTrue(todo1.isDone);
    }

    @Test
    void markAsDone() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        // tasks that are not done
        assertFalse(deadline1.isDone());
        assertFalse(event1.isDone());
        assertFalse(todo1.isDone());

        deadline1.markAsDone();
        event1.markAsDone();
        todo1.markAsDone();

        // tasks that are done
        assertTrue(deadline1.isDone());
        assertTrue(event1.isDone());
        assertTrue(todo1.isDone());
    }

    @Test
    void markAsUndone() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        //mark tasks as done first
        deadline1.markAsDone();
        event1.markAsDone();
        todo1.markAsDone();

        assertTrue(deadline1.isDone());
        assertTrue(event1.isDone());
        assertTrue(todo1.isDone());

        //mark tasks as undone
        deadline1.markAsUndone();
        event1.markAsUndone();
        todo1.markAsUndone();

        assertFalse(deadline1.isDone());
        assertFalse(event1.isDone());
        assertFalse(todo1.isDone());
    }

    @Test
    void testToString() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertEquals("[ ] Deadline 1", deadline1.toString());
        assertEquals("[ ] event 1", event1.toString());
        assertEquals("[ ] todo 1", todo1.toString());

        //mark tasks as done
        deadline1.markAsDone();
        event1.markAsDone();
        todo1.markAsDone();

        assertEquals("[X] Deadline 1", deadline1.toString());
        assertEquals("[X] event 1", event1.toString());
        assertEquals("[X] todo 1", todo1.toString());
    }

    @Test
    void compareDescription() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();
        Task todo2 = new TaskBuilder(SECOND_TODO).build();
        Task todo2Caps = new TaskBuilder(SECOND_TODO_WITH_CAPS).build();

        //test comparison between lowercase alphabets
        assertTrue(deadline1.compareDescription(event1) < 0);
        assertTrue(todo2.compareDescription(event1) > 0);

        //test whether case is ignored
        assertEquals(0, todo2.compareDescription(todo2Caps));

        //test comparison between numbers
        assertTrue(todo1.compareDescription(todo2Caps) < 0);
    }

    @Test
    void compareBefore() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo2 = new TaskBuilder(SECOND_TODO).build();

        //compare between dates
        assertTrue(deadline1.compareBefore(event1) < 0);

        //compare between date and null (todo)
        assertTrue(todo2.compareBefore(event1) > 0);
        assertTrue(event1.compareBefore(todo2) < 0);

    }

    @Test
    void compareAfter() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo2 = new TaskBuilder(SECOND_TODO).build();

        //compare between dates
        assertTrue(deadline1.compareAfter(event1) > 0);

        //compare between date and null (todo)
        assertTrue(todo2.compareAfter(event1) < 0);
        assertTrue(event1.compareAfter(todo2) > 0);
    }

    @Test
    void comparePriority() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task deadline2 = new TaskBuilder(SECOND_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertTrue(deadline2.comparePriority(event1) > 0);
        assertTrue(event1.comparePriority(todo1) < 0);
        assertEquals(0, deadline1.comparePriority(deadline2));
    }

    @Test
    void compareGroup() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertTrue(deadline1.compareGroup(event1) < 0);
        assertEquals(0, deadline1.compareGroup(todo1));
        assertTrue(event1.compareGroup(todo1) > 0);
    }

    @Test
    void testEquals() {
        Task deadline1 = new TaskBuilder(FIRST_DEADLINE).build();
        Task event1 = new TaskBuilder(FIRST_EVENT).build();
        Task todo1 = new TaskBuilder(FIRST_TODO).build();

        assertNotEquals(deadline1, event1);
        assertNotEquals(todo1, event1);

        assertEquals(new TaskBuilder(FIRST_DEADLINE).build(), deadline1);
        assertEquals(new TaskBuilder(FIRST_TODO).build(), todo1);
    }

}
