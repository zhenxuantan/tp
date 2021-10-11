package seedu.address.model.person.social;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void toUrl_validUsername_returnsValidUrl() {
        String username = "alice";
        String expectedUrl = Telegram.BASE_URL + username;
        Telegram actual = new Telegram(username);
        assertEquals(expectedUrl, actual.toUrl());
    }

}
