package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static sweebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sweebook.logic.parser.CliSyntax.PREFIX_GITHUB;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_NAME;
import static sweebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static sweebook.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.person.Person;

/**
 * Adds a person to the contact list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the contact list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GROUP + "GROUP1 "
            + "[" + PREFIX_GROUP + "GROUP2] "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_TELEGRAM + "TELEGRAM "
            + PREFIX_GITHUB + "GITHUB\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_GROUP + "CS2103T "
            + PREFIX_GROUP + "CS2101 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TELEGRAM + "johndoe "
            + PREFIX_GITHUB + "johndoe ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(model.getSamePersonConstraintMessage(toAdd));
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
