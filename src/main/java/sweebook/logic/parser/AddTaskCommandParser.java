package sweebook.logic.parser;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.AddTaskCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DATE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static sweebook.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static sweebook.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import java.util.stream.Stream;

import sweebook.logic.commands.AddTaskCommand;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.task.Date;
import sweebook.model.task.Deadline;
import sweebook.model.task.Description;
import sweebook.model.task.Event;
import sweebook.model.task.Priority;
import sweebook.model.task.RecurringFrequency;
import sweebook.model.task.Task;
import sweebook.model.task.TaskType;
import sweebook.model.task.Todo;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_GROUP, PREFIX_TASKTYPE,
                    PREFIX_DATE, PREFIX_RECURRING_FREQUENCY, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_GROUP, PREFIX_TASKTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        TaskType taskType = ParserUtil.parseTaskType(argMultimap.getValue(PREFIX_TASKTYPE).get());

        if (taskType.taskType.equals("deadline") || taskType.taskType.equals("event")) {
            if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(null));
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).orElse("med"));

        if (taskType.taskType.equals("todo")
            && !arePrefixesPresent(argMultimap, PREFIX_DATE)
            && arePrefixesPresent(argMultimap, PREFIX_RECURRING_FREQUENCY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTaskCommand.MESSAGE_RECURRING_FREQ_NO_DATE));
        }

        RecurringFrequency recurringFrequency =
            ParserUtil.parseRecurringFrequency(argMultimap.getValue(PREFIX_RECURRING_FREQUENCY).orElse("none"));

        Task toAdd;
        switch (taskType.toString()) {
        case "todo":
            toAdd = new Todo(description, group, date, taskType, recurringFrequency, priority);
            break;
        case "event":
            toAdd = new Event(description, group, date, taskType, recurringFrequency, priority);
            break;
        case "deadline":
            requireNonNull(date);
            toAdd = new Deadline(description, group, date, taskType, recurringFrequency, priority);
            break;
        default:
            throw new ParseException(TaskType.MESSAGE_CONSTRAINTS);
        }
        return new AddTaskCommand(toAdd);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
