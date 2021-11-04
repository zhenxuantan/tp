package sweebook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sweebook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static sweebook.logic.parser.ParserUtil.parseEmail;
import static sweebook.logic.parser.ParserUtil.parseGroup;
import static sweebook.logic.parser.ParserUtil.parseGroups;
import static sweebook.logic.parser.ParserUtil.parseIndex;
import static sweebook.logic.parser.ParserUtil.parseName;
import static sweebook.logic.parser.ParserUtil.parsePhone;
import static sweebook.logic.parser.ParserUtil.parseUsername;
import static sweebook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Phone;
import sweebook.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_GROUP = "CS1234";
    private static final String INVALID_USERNAME = "-foo";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_GROUP_1 = "CS2101";
    private static final String VALID_GROUP_2 = "CS2103T";
    private static final String VALID_USERNAME_WITHOUT_AT = "rachel"; // AT refers to the symbol '@'
    private static final String VALID_USERNAME_WITH_AT = "@rachel";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseGroup(null));
    }

    @Test
    public void parseGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseGroup(INVALID_GROUP));
    }

    @Test
    public void parseGroup_validValueWithoutWhitespace_returnsGroup() throws Exception {
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, parseGroup(VALID_GROUP_1));
    }

    @Test
    public void parseGroup_validValueWithWhitespace_returnsTrimmedGroup() throws Exception {
        String groupWithWhitespace = WHITESPACE + VALID_GROUP_1 + WHITESPACE;
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, parseGroup(groupWithWhitespace));
    }

    @Test
    public void parseGroups_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseGroups(null));
    }

    @Test
    public void parseGroups_collectionWithInvalidGroups_throwsParseException() {
        assertThrows(ParseException.class, () -> parseGroups(Arrays.asList(VALID_GROUP_1, INVALID_TAG)));
    }

    @Test
    public void parseGroups_collectionWithValidGroups_returnsGroupSet() throws Exception {
        Set<Group> actualGroupSet = parseGroups(Arrays.asList(VALID_GROUP_1, VALID_GROUP_2));
        Set<Group> expectedGroupSet = new HashSet<>(Arrays.asList(new Group(VALID_GROUP_1),
                new Group(VALID_GROUP_2)));

        assertEquals(expectedGroupSet, actualGroupSet);
    }

    @Test
    public void parseUsername_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parseUsername((String) null));
    }

    @Test
    public void parseUsername_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parseUsername(INVALID_USERNAME));
    }

    @Test
    public void parseUsername_validValueWithAtSymbol_returnsValueWithoutSymbol() throws Exception {
        String expectedUsername = VALID_USERNAME_WITHOUT_AT;
        String actualUsername = parseUsername(VALID_USERNAME_WITH_AT);
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void parseUsername_validValueWithoutAtSymbol_returnsValueWithoutSymbol() throws Exception {
        String expectedUsername = VALID_USERNAME_WITHOUT_AT;
        String actualUsername = parseUsername(VALID_USERNAME_WITHOUT_AT);
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void parseUsername_validValueWithWhitespace_returnsTrimmedUsername() throws Exception {
        String usernameWithWhitespace = WHITESPACE + VALID_USERNAME_WITHOUT_AT + WHITESPACE;
        String actualUsername = parseUsername(VALID_USERNAME_WITHOUT_AT);
        assertEquals(VALID_USERNAME_WITHOUT_AT, actualUsername);
    }
}
