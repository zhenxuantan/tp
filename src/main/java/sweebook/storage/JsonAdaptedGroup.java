package sweebook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import sweebook.commons.exceptions.IllegalValueException;
import sweebook.model.group.Group;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    private final String group;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given {@code group}.
     */
    @JsonCreator
    public JsonAdaptedGroup(String group) {
        this.group = group;
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        group = source.group;
    }

    @JsonValue
    public String getGroup() {
        return group;
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        if (!Group.isValidGroup(group)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(group);
    }

}
