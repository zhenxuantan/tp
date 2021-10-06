package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Group {
    public static final String MESSAGE_CONSTRAINTS =
            "Group can be either CS2101 or CS2103T, and it should not be blank";

    public final String group;

    /**
     * Constructs an {@code Group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_CONSTRAINTS);
        this.group = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        String[] validGroups = {"CS2103T", "CS2101"};
        for (String validGroup : validGroups) {
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

    public boolean equalTo(Group group){
        return this.group.equals(group.toString());
    }
}
