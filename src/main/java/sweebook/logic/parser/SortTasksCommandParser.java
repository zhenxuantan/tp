package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.SortTasksCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_ORDER;
import static sweebook.logic.parser.CliSyntax.PREFIX_PARAMETER;

import java.util.stream.Stream;

import sweebook.logic.commands.SortTasksCommand;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.task.SortTaskComparator;

/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTasksCommandParser implements Parser<SortTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortTasksCommand
     * and returns a SortTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARAMETER, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PARAMETER, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String param = argMultimap.getValue(PREFIX_PARAMETER).get().toLowerCase();
        String order = argMultimap.getValue(PREFIX_ORDER).get().toLowerCase();

        if (!SortTaskComparator.isValidComparator(param, order)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

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
