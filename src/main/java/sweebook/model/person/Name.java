package sweebook.model.person;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the contact list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphabetical letters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z][a-zA-Z ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    /**
     * A less strct equivalent relation for names, where names can be equal where
     * 1. Case-sensitivity is ignored.
     * 2. Whitespaces between words in their names are ignored.
     * @param other The other name.
     * @return Returns true if the above condition is met.
     */
    public boolean equalsIgnoreCaseAndWhiteSpaces(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        String[] otherNameSplit = otherName.fullName.split(" ");
        String[] thisNameSplit = fullName.split(" ");

        if (otherNameSplit.length != thisNameSplit.length) {
            return false;
        }

        for (int i = 0; i < thisNameSplit.length; i++) {
            if (!(thisNameSplit[i].trim().equalsIgnoreCase(otherNameSplit[i].trim()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
