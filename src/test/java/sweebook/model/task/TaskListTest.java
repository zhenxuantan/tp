package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;
import static sweebook.testutil.TypicalTasks.FIRST_DEADLINE;
import static sweebook.testutil.TypicalTasks.FIRST_EVENT;

import org.junit.jupiter.api.Test;

import sweebook.testutil.TaskBuilder;
import sweebook.testutil.TypicalTasks;

class TaskListTest {

    @Test
    public void setTasks_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskList().setTasks((TaskList) null));
    }

    @Test
    void setTasks() {
        TaskList taskList1 = new TaskList();
        TaskList taskList2 = new TaskList();
        for (Task task: TypicalTasks.getTypicalTasks()) {
            Task task1 = new TaskBuilder(task).build();
            Task task2 = new TaskBuilder(task).build();
            taskList1.add(task1);
            taskList2.add(task2);
        }

        TaskList taskList3 = new TaskList();
        taskList3.setTasks(taskList2);

        assertEquals(taskList1, taskList3);
    }

    @Test
    void updateRecurringTasksDates() {
        Task noneTest = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("none").build();
        Task weekTest = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("week").build();
        Task monthTest = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("month").build();
        Task yearTest = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("year").build();

        Task noneTestCopy = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("none").build();
        Task weekTestCopy = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("week").build();
        Task monthTestCopy = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("month").build();
        Task yearTestCopy = new TaskBuilder().withDate("1900-01-01").withRecurringFrequency("year").build();

        TaskList taskList = new TaskList();
        taskList.add(noneTest);
        taskList.add(weekTest);
        taskList.add(monthTest);
        taskList.add(yearTest);

        taskList.updateRecurringTasksDates();

        assertEquals(noneTestCopy, noneTest);
        assertTrue(weekTestCopy.compareBefore(weekTest) < 0);
        assertTrue(monthTestCopy.compareBefore(monthTest) < 0);
        assertTrue(yearTestCopy.compareBefore(yearTest) < 0);
    }

    @Test
    void iterator() {
        TaskList taskList = new TaskList();
        taskList.add(FIRST_DEADLINE);
        taskList.add(FIRST_EVENT);

        assertTrue(taskList.iterator().hasNext());
        assertEquals(FIRST_DEADLINE, taskList.iterator().next());
        taskList.delete(FIRST_DEADLINE);

        assertTrue(taskList.iterator().hasNext());
        assertEquals(FIRST_EVENT, taskList.iterator().next());
        taskList.delete(FIRST_EVENT);

        assertFalse(taskList.iterator().hasNext());
    }

}

