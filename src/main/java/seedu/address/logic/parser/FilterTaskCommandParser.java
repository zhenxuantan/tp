package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.FilterTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.task.Date;
import seedu.address.model.task.FilterTaskPredicate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskType;

/**
 * Parses input arguments and creates a new FilterTasksCommand object
 */
public class FilterTaskCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTasksCommand
     * and returns a FilterTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TASKTYPE, PREFIX_DATE, PREFIX_PRIORITY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTasksCommand.MESSAGE_USAGE));
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
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTasksCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
