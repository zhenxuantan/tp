package sweebook.model.group;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

/**
 * This class encapsulates a group. A group can be CS2103T or CS2101.
 */
public class Group {
    public static final String MESSAGE_CONSTRAINTS =
            "Group can be either CS2101 or CS2103T, and it should not be blank";
    public static final String[] VALID_GROUPS = {"CS2103T", "CS2101"};
    public final String group;

    /**
     * Constructs an {@code Group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        group = group.toUpperCase();
        checkArgument(isValidGroup(group), MESSAGE_CONSTRAINTS);
        this.group = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        for (String validGroup : VALID_GROUPS) {
            if (test.equalsIgnoreCase(validGroup)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return group;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && group.equalsIgnoreCase(((Group) other).group)); // state check
    }

    @Override
    public int hashCode() {
        return group.hashCode();
    }

    public int compareTo(Group otherGroup) {
        return this.group.toLowerCase().compareTo(otherGroup.group.toLowerCase());
    }
}
