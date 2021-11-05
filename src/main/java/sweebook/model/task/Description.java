package sweebook.model.task;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Description can take any value, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String description;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Description) {
            return description.equals(((Description) obj).description);
        } else {
            return false;
        }
    }

    public int compareTo(Description otherDesc) {
        return description.toLowerCase().compareTo(otherDesc.description.toLowerCase());
    }

    public boolean contains(Description desc) {
        return description.toLowerCase().contains(desc.toString().toLowerCase());
    }

}
