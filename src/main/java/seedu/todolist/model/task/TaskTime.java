package seedu.todolist.model.task;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.parser.TimeParser;

//@@author A0138601M
/**
 * Represents a Task's time in the to do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime implements Comparable<TaskTime> {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = "Task time should be in 24-hr format or AM/PM format";
    public static final String MESSAGE_TIME_INVALID = "Task time provided is invalid!";

    //format: 24-hr
    public static final String TIME_VALIDATION_REGEX_2 = "(\\p{Digit}){1,2}:(\\p{Digit}){2}";
    //format: AM/PM
    public static final String TIME_VALIDATION_REGEX_1 = "(\\p{Digit}){1,2}(:(\\p{Digit}){2})?\\s?[AaPp][Mm]";
    public static final String TIME_VALIDATION_REGEX_FORMAT = TIME_VALIDATION_REGEX_1 + "|" + TIME_VALIDATION_REGEX_2;

    public static final String TIME_DISPLAY_FORMAT = "h:mma";

    private LocalTime time;
    
    public TaskTime() {
        
    }
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        String trimmedTime = time.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        
        try {
            this.time = TimeParser.parseTime(trimmedTime);
        } catch (DateTimeException dateTimeException) {
            throw new IllegalValueException(MESSAGE_TIME_INVALID);
        }
    }
    
    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX_FORMAT);
    }
    
    /**
     * Returns true if this time is earlier than given time.
     */
    public boolean isBefore(TaskTime other) {
        return this.time.isBefore(other.getTime());
    }

    public LocalTime getTime() {
        return this.time;
    }
    
    /**
     * Return the current time
     */
    public static TaskTime now() {
        TaskTime now = new TaskTime();
        now.time = LocalTime.now();
        return now;
    }
    
    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern(TIME_DISPLAY_FORMAT));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.time.equals(((TaskTime) other).time)); // state check
    }
    
    /**
     * Returns true if both TaskTimes are equal.
     * Use this method when both TaskTimes could be null
     */
    public static boolean isEquals(TaskTime time, TaskTime other) {
        if (time == null && other == null) {
            //both are null, they are equal
            return true;
        }
        
        if (time != null) {
            return time.equals(other);
        } else {
            // if date is null, other cannot be null.
            // thus, they are not equal
            return false;
        }
    }
    
    @Override 
    public int compareTo(TaskTime time) {
        if (time == null) return -1;
        
        if (this.equals(time)) {
            return 0;
        } else if (this.isBefore(time)) {
            return -1;
        } else {
            return 1;
        }
    }
}
