package sweebook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sweebook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sweebook.model.person.Person;
import sweebook.model.person.exceptions.DuplicatePersonException;
import sweebook.testutil.PersonBuilder;
import sweebook.testutil.TypicalPersons;

public class ContactListTest {

    private final ContactList contactList = new ContactList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), contactList.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contactList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyContactList_replacesData() {
        ContactList newData = TypicalPersons.getTypicalContactList();
        contactList.resetData(newData);
        assertEquals(newData, contactList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        ContactListStub newData = new ContactListStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> contactList.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contactList.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInContactList_returnsFalse() {
        assertFalse(contactList.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInContactList_returnsTrue() {
        contactList.addPerson(TypicalPersons.ALICE);
        assertTrue(contactList.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInContactList_returnsTrue() {
        contactList.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).build();
        assertTrue(contactList.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> contactList.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyContactList whose persons list can violate interface constraints.
     */
    private static class ContactListStub implements ReadOnlyContactList {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ContactListStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
