package sweebook.model.person.social;

import sweebook.commons.util.AppUtil;
import sweebook.commons.util.CollectionUtil;

/**
 * This class encapsulates a URL to an online profile.
 */
public abstract class Social {
    public static final String MESSAGE_CONSTRAINTS = "Usernames cannot be blank,"
        + " and may only contain alphanumeric characters or single hyphens/underscores,"
        + " and cannot begin or end with a hyphen/underscores.";
    // Adapted from https://github.com/regexhq/regex-username
    private static final String VALIDATION_REGEX = "^([a-zA-Z\\d]+[-_])*[a-zA-Z\\d]+$";
    public final String username;
    private final String baseUrl; // the base url that is to be prepended to the username, that gives the user profile.

    /**
     * Constructor for the Social class.
     *
     * @param baseurl The base url of the online platform.
     * @param username The username of the user on that platform.
     */
    public Social(String baseurl, String username) {
        CollectionUtil.requireAllNonNull(baseurl, username);
        AppUtil.checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        this.baseUrl = baseurl;
        this.username = username;
    }

    /**
     * Checks if the username is valid. Note that this is just a baseline check,
     * and may not fufill requirement of all platforms.
     *
     * @param test The username to be checked.
     * @return True if the username is valid.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    /**
     * Returns the profile URL.
     *
     * @return the profile URL.
     */
    public String toUrl() {
        return baseUrl + username;
    }

    @Override
    public String toString() {
        return "@" + username;
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
