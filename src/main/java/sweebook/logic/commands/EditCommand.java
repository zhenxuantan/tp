package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static sweebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sweebook.logic.parser.CliSyntax.PREFIX_GITHUB;
import static sweebook.logic.parser.CliSyntax.PREFIX_GROUP;
import static sweebook.logic.parser.CliSyntax.PREFIX_NAME;
import static sweebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static sweebook.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import sweebook.commons.core.Messages;
import sweebook.commons.core.index.Index;
import sweebook.commons.util.CollectionUtil;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Telegram;

/**
 * Edits the details of an existing person in the contact list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer and have a value lower than 2,147,483,648) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GROUP + "GROUP1] "
            + "[" + PREFIX_GROUP + "GROUP2] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM] "
            + "[" + PREFIX_GITHUB + "GITHUB]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP + "CS2101 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TELEGRAM + "johndoe1 "
            + PREFIX_GITHUB + "johndoe2";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Set<Group> updatedGroups = editPersonDescriptor.getGroups().orElse(personToEdit.getGroups());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Telegram updatedTele = editPersonDescriptor.getTelegram().orElse(personToEdit.getTelegram());
        GitHub updatedGit = editPersonDescriptor.getGitHub().orElse(personToEdit.getGitHub());

        return new Person(updatedName, updatedGroups, updatedPhone, updatedEmail, updatedTele, updatedGit);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        for (int i = 0; i < model.getContactList().getPersonList().size(); i++) {
            Person curr = model.getContactList().getPersonList().get(i);
            if (lastShownList.contains(curr) && lastShownList.indexOf(curr) == index.getZeroBased()) {
                continue;
            } else if (curr.isSamePerson(editedPerson)) {
                throw new CommandException(curr.getSamePersonConstraintMessage(editedPerson));
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Set<Group> groups;
        private Phone phone;
        private Email email;
        private Telegram tg;
        private GitHub gh;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setGroups(toCopy.groups);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTelegram(toCopy.tg);
            setGitHub(toCopy.gh);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, groups, phone, email, tg, gh);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Set<Group>> getGroups() {
            return (groups != null) ? Optional.of(Collections.unmodifiableSet(groups)) : Optional.empty();
        }

        public void setGroups(Set<Group> groups) {
            this.groups = (groups != null) ? new HashSet<>(groups) : null;
        }

        public Optional<Telegram> getTelegram() {
            return Optional.ofNullable(tg);
        }

        public void setTelegram(Telegram tg) {
            this.tg = tg;
        }

        public Optional<GitHub> getGitHub() {
            return Optional.ofNullable(gh);
        }

        public void setGitHub(GitHub gh) {
            this.gh = gh;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getGroups().equals(e.getGroups())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getTelegram().equals(e.getTelegram())
                    && getGitHub().equals(e.getGitHub());
        }
    }
}
