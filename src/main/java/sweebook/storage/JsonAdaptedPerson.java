package sweebook.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import sweebook.commons.exceptions.IllegalValueException;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Person;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Social;
import sweebook.model.person.social.Telegram;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();
    private final String phone;
    private final String email;
    private final String telegram;
    private final String github;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("groups") List<JsonAdaptedGroup> groups,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("telegram") String telegram, @JsonProperty("github") String github) {
        this.name = name;
        this.groups.addAll(groups);
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.github = github;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        groups.addAll(source.getGroups().stream()
                .map(JsonAdaptedGroup::new)
                .collect(Collectors.toSet()));
        phone = source.getPhone().value;
        email = source.getEmail().value;
        telegram = source.getTelegram().username;
        github = source.getGitHub().username;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (groups.size() == 0) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        final Set<Group> modelGroups = new HashSet<>();
        for (JsonAdaptedGroup group : groups) {
            modelGroups.add(group.toModelType());
        }
        if (modelGroups.size() > 2) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Social.isValidUsername(telegram)) {
            throw new IllegalValueException(Social.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        if (github == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GitHub.class.getSimpleName()));
        }
        if (!Social.isValidUsername(github)) {
            throw new IllegalValueException(Social.MESSAGE_CONSTRAINTS);
        }
        final GitHub modelGithub = new GitHub(github);

        return new Person(modelName, modelGroups, modelPhone, modelEmail, modelTelegram, modelGithub);
    }

}
