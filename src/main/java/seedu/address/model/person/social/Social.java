package seedu.address.model.person.social;

import static java.util.Objects.requireNonNull;

/**
 * This class encapsulates a URL to an online profile.
 */
public abstract class Social {
    private String baseUrl; // the base url that is to be prepended to the username, that gives the user profile.
    public String username;

    /**
     * Constructor for the Social class.
     *
     * @param baseurl The base url of the online platform.
     * @param username The username of the user on that platform.
     */
    public Social(String baseurl, String username) {
        requireNonNull(baseurl, username);
        this.baseUrl = baseurl;
        this.username = username;
    }


    @Override
    public String toString() {
        return baseUrl + username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Social // instanceof handles nulls
                && username.equals(((Social) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
