package sweebook.model.person.social;

/**
 * This class encapsulates a URL to a Telegram profile.
 */
public class Telegram extends Social {
    public static final String BASE_URL = "https://t.me/";

    /**
     * Constructor for the Telegram class.
     *
     * @param username The telegram username.
     */
    public Telegram(String username) {
        super(BASE_URL, username);
    }
}
