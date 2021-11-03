package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.GroupCommand.MESSAGE_USAGE;

import sweebook.logic.commands.GroupCommand;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.group.GroupPredicate;

/**
 * Parses input arguments and creates a new GroupCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns a GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Group group = ParserUtil.parseGroup(args);

        return new GroupCommand(new GroupPredicate(group));
    }
}
