package sweebook.model.person.social;

/**
 * This class encapsulates a URL to a GitHub Profile.
 */
public class GitHub extends Social {
    public static final String BASE_URL = "https://github.com/";

    /**
     * Constructor for the Github class.
     *
     * @param username The GitHub username.
     */
    public GitHub(String username) {
        super(BASE_URL, username);
    }
}
