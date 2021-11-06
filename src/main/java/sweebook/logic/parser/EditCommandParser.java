package sweebook.logic.parser;

import static java.util.Objects.requireNonNull;
import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.EditCommand.EditPersonDescriptor;
import static sweebook.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static sweebook.logic.commands.EditCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sweebook.logic.parser.CliSyntax.PREFIX_GITHUB;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_NAME;
import static sweebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static sweebook.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import sweebook.commons.core.index.Index;
import sweebook.logic.commands.EditCommand;
import sweebook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TELEGRAM, PREFIX_GITHUB);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (!argMultimap.getAllValues(PREFIX_GROUP).isEmpty()) {
            editPersonDescriptor.setGroups(ParserUtil.parseGroups(argMultimap.getAllValues(PREFIX_GROUP)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }
        if (argMultimap.getValue(PREFIX_GITHUB).isPresent()) {
            editPersonDescriptor.setGitHub(ParserUtil.parseGitHub(argMultimap.getValue(PREFIX_GITHUB).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
}
