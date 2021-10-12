package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAMETER;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.SortTaskComparator;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class SortTasksCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARAMETER, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PARAMETER, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTasksCommand.MESSAGE_USAGE));
        }

        String param = argMultimap.getValue(PREFIX_PARAMETER).get().toLowerCase();
        String order = argMultimap.getValue(PREFIX_ORDER).get().toLowerCase();

        return new SortTasksCommand(new SortTaskComparator(param, order));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
