package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.SortTasksCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static sweebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import sweebook.logic.commands.SortTasksCommand;
import sweebook.model.task.SortTaskComparator;

class SortTasksCommandParserTest {

    private final SortTasksCommandParser parser = new SortTasksCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortTasksCommand() {

        SortTasksCommand expectedSortTasksCommand =
            new SortTasksCommand(new SortTaskComparator("desc", "d"));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " param/desc o/d", expectedSortTasksCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n param/desc \n \t o/d  \t", expectedSortTasksCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "param/notAParam o/d", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "param/pty o/a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }
}
