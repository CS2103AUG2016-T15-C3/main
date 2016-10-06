package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.person.*;
import seedu.task.model.tag.Tag;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
