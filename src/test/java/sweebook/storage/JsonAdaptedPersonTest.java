package sweebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sweebook.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static sweebook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import sweebook.commons.exceptions.IllegalValueException;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Social;
import sweebook.model.person.social.Telegram;
import sweebook.testutil.TypicalPersons;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_GROUP = "cs1234";
    private static final String INVALID_USERNAME = "-foo";

    private static final String VALID_NAME = TypicalPersons.BENSON.getName().toString();
    private static final String VALID_PHONE = TypicalPersons.BENSON.getPhone().toString();
    private static final String VALID_EMAIL = TypicalPersons.BENSON.getEmail().toString();
    private static final List<JsonAdaptedGroup> VALID_GROUPS = TypicalPersons.BENSON.getGroups().stream()
            .map(JsonAdaptedGroup::new)
            .collect(Collectors.toList());
    private static final String VALID_TELEGRAM = TypicalPersons.BENSON.getTelegram().username;
    private static final String VALID_GITHUB = TypicalPersons.BENSON.getGitHub().username;

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(TypicalPersons.BENSON);
        assertEquals(TypicalPersons.BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME,
                VALID_GROUPS, VALID_PHONE, VALID_EMAIL, VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null,
                VALID_GROUPS, VALID_PHONE, VALID_EMAIL, VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME,
                VALID_GROUPS, INVALID_PHONE, VALID_EMAIL, VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME,
                VALID_GROUPS, null, VALID_EMAIL, VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME,
                VALID_GROUPS, VALID_PHONE, INVALID_EMAIL, VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_GROUPS, VALID_PHONE, null,
                VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGroup_throwsIllegalValueException() {
        List<JsonAdaptedGroup> invalidGroups = new ArrayList<>();
        invalidGroups.add(new JsonAdaptedGroup(INVALID_GROUP));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, invalidGroups, VALID_PHONE, VALID_EMAIL,
                VALID_TELEGRAM, VALID_GITHUB);
        String expectedMessage = Group.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_GROUPS, VALID_PHONE, VALID_EMAIL,
                null, VALID_GITHUB);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Telegram.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME,
                VALID_GROUPS, VALID_PHONE, VALID_EMAIL, INVALID_USERNAME, VALID_GITHUB);
        String expectedMessage = Social.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGithub_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_GROUPS, VALID_PHONE, VALID_EMAIL,
                VALID_TELEGRAM, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GitHub.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGithub_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME,
                VALID_GROUPS, VALID_PHONE, VALID_EMAIL, VALID_TELEGRAM, INVALID_USERNAME);
        String expectedMessage = Social.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
