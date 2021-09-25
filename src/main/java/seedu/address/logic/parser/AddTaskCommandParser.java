package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Group;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.Todo;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser {
        /**
         * Parses the given {@code String} of arguments in the context of the AddCommand
         * and returns an AddCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
         */
        public AddTaskCommand parse(String args) throws ParseException {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_GROUP, PREFIX_TASKTYPE, PREFIX_DATE);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_GROUP, PREFIX_TASKTYPE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }


            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
            TaskType taskType = ParserUtil.parseTaskType(argMultimap.getValue(PREFIX_TASKTYPE).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

            Task toAdd;
            switch (taskType.toString()) {
            case "todo":
                toAdd = new Todo(description, group, taskType, date);
                return new AddTaskCommand(toAdd);
            case "event":
                toAdd = new Event(description, group, date, taskType);
                return new AddTaskCommand(toAdd);
            case "deadline":
                toAdd = new Deadline(description, group, date, taskType);
                return new AddTaskCommand(toAdd);
            default:
                throw new ParseException(TaskType.MESSAGE_CONSTRAINTS);
            }
        }

        /**
         * Returns true if none of the prefixes contains empty {@code Optional} values in the given
         * {@code ArgumentMultimap}.
         */
        private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
            return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
        }

    }
