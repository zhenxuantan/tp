package sweebook.logic.parser;

import static sweebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sweebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sweebook.logic.commands.AddCommand;
import sweebook.logic.commands.AddTaskCommand;
import sweebook.logic.commands.ClearCommand;
import sweebook.logic.commands.Command;
import sweebook.logic.commands.DeleteCommand;
import sweebook.logic.commands.DeleteTaskCommand;
import sweebook.logic.commands.DoneTaskCommand;
import sweebook.logic.commands.EditCommand;
import sweebook.logic.commands.EditTaskCommand;
import sweebook.logic.commands.ExitCommand;
import sweebook.logic.commands.FilterTasksCommand;
import sweebook.logic.commands.FindCommand;
import sweebook.logic.commands.GroupCommand;
import sweebook.logic.commands.HelpCommand;
import sweebook.logic.commands.ListCommand;
import sweebook.logic.commands.ListTasksCommand;
import sweebook.logic.commands.SortTasksCommand;
import sweebook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SweeBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case GroupCommand.COMMAND_WORD:
            return new GroupCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case ListTasksCommand.COMMAND_WORD:
            return new ListTasksCommand();

        case SortTasksCommand.COMMAND_WORD:
            return new SortTasksCommandParser().parse(arguments);

        case FilterTasksCommand.COMMAND_WORD:
            return new FilterTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
