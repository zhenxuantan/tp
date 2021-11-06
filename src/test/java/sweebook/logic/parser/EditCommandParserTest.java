package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static sweebook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static sweebook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static sweebook.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_AMY_CS2101;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_AMY_CS2103T;
import static sweebook.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static sweebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static sweebook.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import sweebook.commons.core.index.Index;
import sweebook.logic.commands.EditCommand;
import sweebook.logic.commands.EditCommand.EditPersonDescriptor;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Phone;
import sweebook.testutil.EditPersonDescriptorBuilder;
import sweebook.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_GROUP_DESC, Group.MESSAGE_CONSTRAINTS); // invalid group

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // valid groups followed by an invalid group.
        assertParseFailure(parser, "1" + GROUP_DESC_AMY + INVALID_GROUP_DESC, Group.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + GROUP_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + TELEGRAM_DESC_AMY + GITHUB_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withGroups(VALID_GROUP_BOB).withGitHub(VALID_GITHUB_BOB).withTelegram(VALID_TELEGRAM_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // group
        userInput = targetIndex.getOneBased() + GROUP_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withGroups(VALID_GROUP_AMY_CS2101,
                VALID_GROUP_AMY_CS2103T).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegram
        userInput = targetIndex.getOneBased() + TELEGRAM_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withTelegram(VALID_TELEGRAM_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // github
        userInput = targetIndex.getOneBased() + GITHUB_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withGitHub(VALID_GITHUB_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // an invalid group followed by 2 valid groups
        userInput = targetIndex.getOneBased() + INVALID_GROUP_DESC + GROUP_DESC_BOB + GROUP_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withGroups(VALID_GROUP_AMY_CS2101,
                VALID_GROUP_AMY_CS2103T).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
