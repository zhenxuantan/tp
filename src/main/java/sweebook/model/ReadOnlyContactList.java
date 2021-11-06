package sweebook.model;

import javafx.collections.ObservableList;
import sweebook.model.person.Person;

/**
 * Unmodifiable view of a contact list
 */
public interface ReadOnlyContactList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
