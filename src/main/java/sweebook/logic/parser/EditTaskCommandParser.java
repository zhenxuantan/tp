package sweebook.logic.parser;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.EditTaskCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DATE;
import static sweebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static sweebook.logic.parser.CliSyntax.PREFIX_RECURRING_FREQUENCY;
import static sweebook.logic.parser.CliSyntax.PREFIX_TASKTYPE;

import sweebook.commons.core.index.Index;
import sweebook.logic.commands.EditTaskCommand;
import sweebook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_GROUP,
            PREFIX_TASKTYPE, PREFIX_DATE, PREFIX_RECURRING_FREQUENCY, PREFIX_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        EditTaskCommand.EditTaskDescriptor editPersonDescriptor = new EditTaskCommand.EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPersonDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (!argMultimap.getAllValues(PREFIX_GROUP).isEmpty()) {
            editPersonDescriptor.setGroup(ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get()));
        }
        if (argMultimap.getValue(PREFIX_TASKTYPE).isPresent()) {
            editPersonDescriptor.setTaskType(ParserUtil.parseTaskType(argMultimap.getValue(PREFIX_TASKTYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editPersonDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_RECURRING_FREQUENCY).isPresent()) {
            editPersonDescriptor.setRecurringFrequency(
                    ParserUtil.parseRecurringFrequency(argMultimap.getValue(PREFIX_RECURRING_FREQUENCY).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editPersonDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editPersonDescriptor);
    }
}
