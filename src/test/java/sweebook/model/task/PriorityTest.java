package sweebook.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PriorityTest {

    private static final Priority LOW = new Priority("low");
    private static final Priority MED = new Priority("med");
    private static final Priority HIGH = new Priority("high");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidName1 = "";
        String invalidName2 = "extreme";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidName1));
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidName2));
    }

    @Test
    public void isValidPriority() {
        assertTrue(Priority.isValidPriority("low"));
        assertTrue(Priority.isValidPriority("med"));
        assertTrue(Priority.isValidPriority("high"));

        assertFalse(Priority.isValidPriority(""));
        assertFalse(Priority.isValidPriority("extreme"));
    }

    @Test
    void compareTo() {
        assertTrue(LOW.compareTo(MED) < 0);
        assertEquals(0, MED.compareTo(new Priority("med")));
        assertTrue(HIGH.compareTo(MED) > 0);
    }

    @Test
    void testEquals() {
        assertEquals(new Priority("low"), LOW);
        assertEquals(new Priority("med"), MED);
        assertEquals(new Priority("high"), HIGH);

        assertNotEquals(LOW, MED);
        assertNotEquals(MED, HIGH);
        assertNotEquals(LOW, HIGH);
    }

    @Test
    void getPriorityIcon() {
        assertEquals("!", LOW.getPriorityIcon());
        assertEquals("!!", MED.getPriorityIcon());
        assertEquals("!!!", HIGH.getPriorityIcon());
    }
}
