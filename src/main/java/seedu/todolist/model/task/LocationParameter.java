package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's location parameter in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocationParameter(String)}
 */
public class LocationParameter {
    public static final String MESSAGE_LOCATION_PARAMETER_CONSTRAINTS = "Task's location parameter should be spaces or alphanumeric characters";
    public static final String LOCATION_PARAMETER_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String locationParameter;
    
    /**
     * Validates given location parameter.
     *
     * @throws IllegalValueException if given location parameter string is invalid.
     */
    public LocationParameter(String location) throws IllegalValueException {
        assert location != null;
        location = location.trim();
        if (!isValidLocationParameter(location)) {
            throw new IllegalValueException(MESSAGE_LOCATION_PARAMETER_CONSTRAINTS);
        }
        this.locationParameter = location;
    }

    /**
     * Returns true if a given string is a valid task's location parameter.
     */
    public static boolean isValidLocationParameter(String test) {
        return test.matches(LOCATION_PARAMETER_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return locationParameter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationParameter // instanceof handles nulls
                && this.locationParameter.equals(((LocationParameter) other).locationParameter)); // state check
    }

    @Override
    public int hashCode() {
        return locationParameter.hashCode();
    }

}
