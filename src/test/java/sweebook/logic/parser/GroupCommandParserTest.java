package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.GroupCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.GroupCommand;
import sweebook.model.group.Group;
import sweebook.model.group.GroupPredicate;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGroupCommand() {
        // no leading and trailing whitespaces
        GroupCommand expectedGroupCommand =
                new GroupCommand(new GroupPredicate(new Group("CS2101")));
        assertParseSuccess(parser, "CS2101", expectedGroupCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2101 \n \t ", expectedGroupCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "CS2103", Group.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "CS210A", Group.MESSAGE_CONSTRAINTS);
    }

}
