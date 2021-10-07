package seedu.address.logic.parser;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.FilterTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.*;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class FilterTaskCommandParser {

    public FilterTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TASKTYPE, PREFIX_DATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTaskCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)){
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return new FilterTaskCommand(new FilterTaskCriterion(PREFIX_DATE + date.getString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASKTYPE)){
            TaskType taskType = ParserUtil.parseTaskType(argMultimap.getValue(PREFIX_TASKTYPE).get());
            return new FilterTaskCommand(new FilterTaskCriterion(PREFIX_TASKTYPE + taskType.toString()));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GROUP)){
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
            return new FilterTaskCommand(new FilterTaskCriterion(PREFIX_GROUP + group.toString()));
        }
        throw new ParseException(FilterTaskCriterion.MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
