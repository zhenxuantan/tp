package sweebook.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "CS2104T";
        assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupName));
    }

    @Test
    public void isValidGroup() {
        // null group name
        assertThrows(NullPointerException.class, () -> Group.isValidGroup(null));

        // invalid group name
        assertFalse(Group.isValidGroup("CS2100"));
        assertFalse(Group.isValidGroup("CS2103"));

        // valid group name
        assertTrue(Group.isValidGroup("CS2103T"));
        assertTrue(Group.isValidGroup("CS2101"));

    }

}
