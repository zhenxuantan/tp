package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.FilterTasksCommand.MESSAGE_USAGE;
import static sweebook.logic.commands.FilterTasksCommand.MULTIPLE_FIELD_ERROR_MESSAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DATE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static sweebook.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import java.util.stream.Stream;

import sweebook.logic.commands.FilterTasksCommand;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.task.Date;
import sweebook.model.task.Description;
import sweebook.model.task.FilterTaskPredicate;
import sweebook.model.task.Priority;
import sweebook.model.task.TaskType;

/**
 * Parses input arguments and creates a new FilterTasksCommand object
 */
public class FilterTaskCommandParser implements Parser<FilterTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTasksCommand
     * and returns a FilterTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TASKTYPE, PREFIX_DATE, PREFIX_PRIORITY,
                        PREFIX_DESCRIPTION);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        if (numberOfPrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_TASKTYPE, PREFIX_DATE, PREFIX_PRIORITY,
                PREFIX_DESCRIPTION) > 1) {
            throw new ParseException(String.format(MULTIPLE_FIELD_ERROR_MESSAGE,
                    MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return new FilterTasksCommand(new FilterTaskPredicate(PREFIX_DATE + date.getString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASKTYPE)) {
            TaskType taskType = ParserUtil.parseTaskType(argMultimap.getValue(PREFIX_TASKTYPE).get());
            return new FilterTasksCommand(new FilterTaskPredicate(PREFIX_TASKTYPE + taskType.toString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GROUP)) {
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
            return new FilterTasksCommand(new FilterTaskPredicate(PREFIX_GROUP + group.toString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            return new FilterTasksCommand(new FilterTaskPredicate(PREFIX_PRIORITY + priority.toString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            return new FilterTasksCommand(new FilterTaskPredicate(PREFIX_DESCRIPTION + desc.toString()));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static int numberOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        int count = 0;
        for (Prefix prefix : prefixes) {
            if (arePrefixesPresent(argumentMultimap, prefix)) {
                count++;
            }
        }
        return count;
    }
}
