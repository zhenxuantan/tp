package sweebook.model.person.social;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SocialTest {
    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> Social.isValidUsername(null));

        // invalid usernames
        assertFalse(Social.isValidUsername("-foo"));
        assertFalse(Social.isValidUsername("foo-"));
        assertFalse(Social.isValidUsername("foo@foo.com"));

        // valid usernames
        assertTrue(Social.isValidUsername("foo"));
        assertTrue(Social.isValidUsername("foo_foo"));
        assertTrue(Social.isValidUsername("foo-foo"));
    }
}
