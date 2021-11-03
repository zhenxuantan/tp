package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static sweebook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static sweebook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static sweebook.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.AddCommand;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.testutil.PersonBuilder;
import sweebook.testutil.TypicalPersons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(TypicalPersons.BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

        // multiple groups - last groups accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                        + GROUP_DESC_BOB + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple telegrams - last telegram accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_AMY
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

        // multiple githubs - last github accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB
                + GITHUB_DESC_AMY + GITHUB_DESC_BOB, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing group prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_GROUP_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing telegram prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + VALID_TELEGRAM_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing github prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + VALID_GITHUB_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_GROUP_BOB + VALID_TELEGRAM_BOB + VALID_GITHUB_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_GROUP_DESC + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Group.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_GROUP_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + GROUP_DESC_BOB + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
