package net.jaskar.mycontacts.datamodel;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty notes;

    public Contact() {
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        phoneNumber = new SimpleStringProperty();
        notes = new SimpleStringProperty();
    }

    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        this(
                new SimpleStringProperty(firstName),
                new SimpleStringProperty(lastName),
                new SimpleStringProperty(phoneNumber),
                new SimpleStringProperty(notes)
        );
    }

    private Contact(SimpleStringProperty firstName, SimpleStringProperty lastName,
                   SimpleStringProperty phoneNumber, SimpleStringProperty notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
