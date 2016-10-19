package seedu.todolist.logic.parser;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.*;
import seedu.todolist.model.task.TaskDate;
import seedu.todolist.model.task.TaskTime;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>(.)+)"
                    + "((\\bfrom\\b|\\bby\\b)(?<interval>(.)+?)){1}"
                    + "((\\bat\\b)(?<location>(.)+?))?" 
                    + "((\\bremarks\\b)(?<remarks>(.)+?))?");
    
    public static final int INTERVAL_COMPONENT_TOTAL = 2;
    public static final int INTERVAL_COMPONENT_FROM = 0;
    public static final int INTERVAL_COMPONENT_TO = 1;
    
    public static final int DETAILED_INTERVAL_COMPONENT_TOTAL = 4;
    public static final int INTERVAL_COMPONENT_STARTDATE = 0;
    public static final int INTERVAL_COMPONENT_STARTTIME = 1;
    public static final int INTERVAL_COMPONENT_ENDDATE = 2;
    public static final int INTERVAL_COMPONENT_ENDTIME = 3;
    
    public static final int DATETIME_COMPONENT_TOTAL = 2; 
    public static final int DATETIME_COMPONENT_DATE = 0;
    public static final int DATETIME_COMPONENT_TIME = 1;

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return parseAddCommand(
                    matcher.group("name"),
                    parseInterval(matcher.group("interval")),
                    matcher.group("location"),
                    matcher.group("remarks")
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private AddCommand parseAddCommand(String name, String[] interval, String location, String remarks) throws IllegalValueException {
        return new AddCommand(
                name, 
                interval[INTERVAL_COMPONENT_STARTDATE], 
                interval[INTERVAL_COMPONENT_STARTTIME], 
                interval[INTERVAL_COMPONENT_ENDDATE], 
                interval[INTERVAL_COMPONENT_ENDTIME],
                location,
                remarks);
    }
    
    /**
     * Extracts the new task's start date and time, and end date and time from the add command's interval arguments string.
     * Returns String[startDate, startTime, endDate, endTime].
     */
    private String[] parseInterval(String interval) {
        String[] intervalComponents = interval.split("to");
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        if (intervalComponents.length < INTERVAL_COMPONENT_TOTAL) {
            startDate = null;
            startTime = null;
            endDate = parseDatetime(intervalComponents[INTERVAL_COMPONENT_FROM])[DATETIME_COMPONENT_DATE];
            endTime = parseDatetime(intervalComponents[INTERVAL_COMPONENT_FROM])[DATETIME_COMPONENT_TIME];
        } 
        else {
            startDate = parseDatetime(intervalComponents[INTERVAL_COMPONENT_FROM])[DATETIME_COMPONENT_DATE];
            startTime = parseDatetime(intervalComponents[INTERVAL_COMPONENT_FROM])[DATETIME_COMPONENT_TIME];
            endDate = parseDatetime(intervalComponents[INTERVAL_COMPONENT_TO])[DATETIME_COMPONENT_DATE];
            endTime = parseDatetime(intervalComponents[INTERVAL_COMPONENT_TO])[DATETIME_COMPONENT_TIME];
            
            //if only one date is provided, both startDate and endDate will be the same
            if (startDate == null) {
                startDate = endDate;
            }
            if (endDate == null) {
                endDate = startDate;
            }
        }
            
        String[] detailedIntervalComponents = new String[DETAILED_INTERVAL_COMPONENT_TOTAL];
        detailedIntervalComponents[INTERVAL_COMPONENT_STARTDATE] = startDate;
        detailedIntervalComponents[INTERVAL_COMPONENT_STARTTIME] = startTime;
        detailedIntervalComponents[INTERVAL_COMPONENT_ENDDATE] = endDate;
        detailedIntervalComponents[INTERVAL_COMPONENT_ENDTIME] = endTime;
        
        return detailedIntervalComponents;
    }
    
    /**
     * Extracts the new task's date and time from the add command's datetime arguments string.
     * Returns String[date, time];
     */
    private String[] parseDatetime(String datetime) {
        String[] dateAndTime = new String[DATETIME_COMPONENT_TOTAL];
        
        //find date
        Pattern datePattern = Pattern.compile(TaskDate.DATE_VALIDATION_REGEX_FORMAT);
        Matcher dateMatcher = datePattern.matcher(datetime);
        if (dateMatcher.find()) {
            dateAndTime[DATETIME_COMPONENT_DATE] = dateMatcher.group();
        }
        
        //find time
        Pattern timePattern = Pattern.compile(TaskTime.TIME_VALIDATION_REGEX_FORMAT);
        Matcher timeMatcher = timePattern.matcher(datetime);
        if (timeMatcher.find()) {
            dateAndTime[DATETIME_COMPONENT_TIME] = timeMatcher.group();
        }
        return dateAndTime;
    }

    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {

        int index = Character.getNumericValue(args.charAt(1));
        if(!(index>=0)){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.substring(2));
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        String name = matcher.group("name");
        String[] interval = parseInterval(matcher.group("interval"));
        String location = matcher.group("location");
        String remarks = matcher.group("remarks");

        try {
			return new EditCommand(index, name, interval[0], interval[1], interval[2], interval[3], location, remarks);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
    }
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}