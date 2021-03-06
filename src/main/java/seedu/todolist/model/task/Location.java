package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

//@@author A0153736B
/**
 * Represents a Task's location parameter in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_LOCATION_PARAMETER_CONSTRAINTS = "Task's location parameter should be spaces or alphanumeric characters";
    public static final String LOCATION_PARAMETER_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String location;
    
    /**
     * Validates given location parameter.
     *
     * @throws IllegalValueException if given location parameter string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        if (location != null) {
            String trimmedLocation = location.trim();
            if (!isValidLocation(trimmedLocation)) {
                throw new IllegalValueException(MESSAGE_LOCATION_PARAMETER_CONSTRAINTS);
            }
            this.location = trimmedLocation;
        }
        else {
        	this.location = location;
        }
    }

    /**
     * Returns true if a given string is a valid task's location parameter.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_PARAMETER_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return location;
    }
}
