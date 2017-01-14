package com.savetheworld;

import javafx.beans.property.SimpleStringProperty;

public class ContactTableViewModel {
    public final SimpleStringProperty firstName = new SimpleStringProperty();
    public final SimpleStringProperty lastName = new SimpleStringProperty();
    public final SimpleStringProperty phoneNumber = new SimpleStringProperty();

     // Get/Set First Name
    public String getFirstName() {
        return firstName.get();
    }
    
     // Get/Set Last Name
    public String getLastName() {
        return lastName.get();
    }
    
    
    // Get/Set Phone Number
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    
}