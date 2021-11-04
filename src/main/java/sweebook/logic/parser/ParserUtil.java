package sweebook.logic.parser;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sweebook.commons.core.index.Index;
import sweebook.commons.util.StringUtil;
import sweebook.logic.parser.exceptions.ParseException;
import sweebook.model.group.Group;
import sweebook.model.person.Email;
import sweebook.model.person.Name;
import sweebook.model.person.Phone;
import sweebook.model.person.social.GitHub;
import sweebook.model.person.social.Social;
import sweebook.model.person.social.Telegram;
import sweebook.model.task.Date;
import sweebook.model.task.Description;
import sweebook.model.task.Priority;
import sweebook.model.task.RecurringFrequency;
import sweebook.model.task.TaskType;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String group} into an {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroup(trimmedGroup)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }

    /**
     * Parses {@code Collection<String> groups} into a {@code Set<Group>}.
     * Used for SWEe-Book.
     */
    public static Set<Group> parseGroups(Collection<String> groups) throws ParseException {
        requireNonNull(groups);
        if (groups.size() == 0) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        final Set<Group> groupSet = new HashSet<>();
        List<String> groupList = new ArrayList<>(groups);
        if (groupList.size() > 2) { // we only take the last 2 group arguments if there are more than 2
            groupList = groupList.subList(groupList.size() - 2, groupList.size());
        }
        for (String groupName : groupList) {
            groupSet.add(parseGroup(groupName));
        }

        assert groupSet.size() <= Group.VALID_GROUPS.length;
        return groupSet;
    }

    /**
     * Parses a {@code String taskType} into an {@code TaskType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskType} is invalid.
     */
    public static TaskType parseTaskType(String taskType) throws ParseException {
        requireNonNull(taskType);
        String trimmedTaskType = taskType.trim();
        if (!TaskType.isValidTaskType(trimmedTaskType)) {
            throw new ParseException(TaskType.MESSAGE_CONSTRAINTS);
        }
        return new TaskType(trimmedTaskType);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        if (isNull(date)) {
            return null;
        }
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a username of the form "username" or "@username".
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param username The username.
     * @return The cleaned username.
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static String parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty()) {
            throw new ParseException(Social.MESSAGE_CONSTRAINTS);
        }
        if (trimmedUsername.charAt(0) == '@') { // removes any prepended '@' if it is present
            trimmedUsername = trimmedUsername.substring(1);
        }

        if (trimmedUsername.isEmpty() || !Social.isValidUsername(trimmedUsername)) {
            throw new ParseException(Social.MESSAGE_CONSTRAINTS);
        }
        return trimmedUsername;
    }

    /**
     * Returns a Telegram object that corresponds to the username.
     *
     * @param username The Telegram username.
     * @return The Telegram object.
     */
    public static Telegram parseTelegram(String username) throws ParseException {
        return new Telegram(parseUsername(username));
    }

    /**
     * Returns a GitHub object that corresponds to the username.
     *
     * @param username The GitHub username.
     * @return The GitHub object.
     */
    public static GitHub parseGitHub(String username) throws ParseException {
        return new GitHub(parseUsername(username));
    }

    /**
     * Parses a {@code String recurringFrequency} into an {@code recurringFrequency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recurringFrequency} is invalid.
     */
    public static RecurringFrequency parseRecurringFrequency(String recurringFrequency) throws ParseException {
        requireNonNull(recurringFrequency);
        String trimmedRecurringFrequency = recurringFrequency.trim();
        if (!RecurringFrequency.isValidRecurringFrequency(trimmedRecurringFrequency)) {
            throw new ParseException(RecurringFrequency.MESSAGE_CONSTRAINTS);
        }
        return new RecurringFrequency(trimmedRecurringFrequency);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        if (priority == null) {
            return new Priority("med");
        }

        if (!Priority.isValidPriority(priority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        return new Priority(priority);
    }
}
