package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.logic.commands.AddCommand.MESSAGE_USAGE;
import static sweebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sweebook.logic.parser.CliSyntax.PREFIX_GITHUB;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_NAME;
import static sweebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static sweebook.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Set;
import java.util.stream.Stream;

import sweebook.logic.commands.AddCommand;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Telegram;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TELEGRAM, PREFIX_GITHUB);

        if (!arePrefixesPresent
                (argMultimap, PREFIX_NAME, PREFIX_GROUP, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TELEGRAM, PREFIX_GITHUB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Group> groups = ParserUtil.parseGroups(argMultimap.getAllValues(PREFIX_GROUP));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Telegram tele = ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get());
        GitHub git = ParserUtil.parseGitHub(argMultimap.getValue(PREFIX_GITHUB).get());

        Person person = new Person(name, groups, phone, email, tele, git);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
