package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;

import sweebook.model.ContactList;
import sweebook.model.Model;

/**
 * Clears the contact list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Contact list has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContactList(new ContactList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
