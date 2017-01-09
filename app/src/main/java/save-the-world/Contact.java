package com.savetheworld;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;

public class Contact extends RecursiveTreeObject<Contact>{

    // CSV Column Name Constants
    public static final String NAME_COL = "Name";
    public static final String EMAIL_COL = "Email Address";
    public static final String HOME_TOWN_COL = "Home Town";
    public static final String HIGH_SCHOOL_COL = "High School";
    public static final String PHONE_NUMBER_COL = "Cellphone Number";
    public static final String HOUSING_COL = "Fall Housing Plans";
    public static final String CLASSIFICATION_COL = "Recruitment Status";
    public static final String MAJOR_COL = "Expected Major";
    public static final String POSITIONS_COL = "Leadership Positions Held";
    public static final String LEGACY_COL = "Legacy INFORMATION";
    public static final String EID_COL = "Eid";

    //Mao Constants for First and Last Name entries 
    public static final String FIRST_NAME = "first-name";
    public static final String LAST_NAME = "last-name";

    // Private variables for PNM's information
    private final StringProperty _eid;
    public final StringProperty firstName;
    public final StringProperty lastName;
    public final StringProperty phoneNumber;
    private final StringProperty email;
    private final StringProperty _major;
    private final StringProperty _hometown;
    private final StringProperty _housing;
    private final StringProperty _highschool;
    private final StringProperty _leadershipPos;
    private final StringProperty _classification;
    
    // Optional Legacy Info
    private boolean _isLegacy;
    private final StringProperty _legacyInfo;
    
    // Recruitment Status Info
    public enum Status { 
        // TODO: Clean-up names a bit?
        NONE, CONTACTED, BEEN_TO_EVENT, BID_READY,
        BID_OUT, ACCEPTED, DECLINED, DEFFERED
    }
    private Status _recruitmentStatus;
    
    // Should not use this constructor other than to test
    public Contact() {
        _eid = null;
        firstName = null;
        lastName = null;
        phoneNumber = null;
        email = null;
        _major = null;
        _hometown = null;
        _housing = null;
        _highschool = null;
        _leadershipPos = null;
        _isLegacy = false;
        _legacyInfo = null;
        _recruitmentStatus = null;
        _classification = null;
    }
    
    // Constructor used for recreation from Firebase
    public Contact(Map <String, Object> record) {
        _eid = new SimpleStringProperty(record.get("eid"));
        firstName = new SimpleStringProperty(record.get("firstName"));
        lastName = new SimpleStringProperty(record.get("lastName"));
        phoneNumber = new SimpleStringProperty(record.get("phoneNumber"));
        email = new SimpleStringProperty(record.get("email"));
        _major = new SimpleStringProperty(record.get("major"));
        _hometown = new SimpleStringProperty(record.get("hometown"));
        _housing = new SimpleStringProperty(record.get("housing"));
        _highschool = new SimpleStringProperty(record.get("highschool"));
        _leadershipPos = new SimpleStringProperty(record.get("leadershipPos"));
        _isLegacy = new SimpleStringProperty(record.get("isLegacy"));
        _legacyInfo = new SimpleStringProperty(record.get("legacyInfo"));
        _classification = new SimpleStringProperty(record.get("classification"));
        _recruitmentStatus = new SimpleStringProperty(record.get("recruitmentStatus"));
    }
    
    // Primary constructor; should always be used
    public Contact(Map <String, String> record) {
        this._eid = new SimpleStringProperty(record.get(EID_COL));
        this.firstName = new SimpleStringProperty(record.get(FIRST_NAME));
        this.lastName = new SimpleStringProperty(record.get(LAST_NAME));
        this.phoneNumber = new SimpleStringProperty(record.get(PHONE_NUMBER_COL));
        this.email = new SimpleStringProperty(record.get(EMAIL_COL));
        this._major = new SimpleStringProperty(record.get(MAJOR_COL));
        this._hometown = new SimpleStringProperty(record.get(HOME_TOWN_COL));
        this._housing = new SimpleStringProperty(record.get(HOUSING_COL));
        this._highschool = new SimpleStringProperty(record.get(HIGH_SCHOOL_COL));
        this._leadershipPos = new SimpleStringProperty(record.get(POSITIONS_COL));
        this._legacyInfo = new SimpleStringProperty(record.get(LEGACY_COL));
        this._classification = new SimpleStringProperty(record.get(CLASSIFICATION_COL));
    }
    
    // Begin massive list of getters and setters
    // Get/Set EID
    public String getEid() {
        return _eid.get();
    }
    
    public void setEid(String newEid) {
        _eid.set(newEid);
    }
    
    
    // Get/Set First Name
    public String getFirstName() {
        return firstName.get();
    }
    
    public void setFirstName(String newFirstName) {
        firstName.set(newFirstName);
    }
    
    
    // Get/Set Last Name
    public String getLastName() {
        return lastName.get();
    }
    
    public void setLastName(String newLastName) {
        lastName.set(newLastName);
    }
    
    
    // Get/Set Phone Number
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    
    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber.set(newPhoneNumber);
    }
    
    
    // Get/Set email address
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String newEmail) {
        email.set(newEmail);
    }
    
    
    // Get/Set Major
    public String getMajor() {
        return _major.get();
    }
    
    public void setMajor(String newMajor) {
        _major.set(newMajor);
    }
    
    
    // Get/Set Recruitment Status
    public Status getRecruitmentStatus() {
        return _recruitmentStatus;
    }
    
    public void setRecruitmentStatus(Status newRecruitmentStatus) {
        _recruitmentStatus = newRecruitmentStatus;
    }
    
    
    // Get/Set Housing
    public String getHousing() {
        return _housing.get();
    }
    
    public void setHousing(String newHousing) {
        _housing.set(newHousing);
    }
    
    
    // Get/Set Hometown
    public String getHometown() {
        return _hometown.get();
    }
    
    public void setHometown(String newHometown) {
        _hometown.set(newHometown);
    }
    
    
    // Get/Set Highschool
    public String getHighschool() {
        return _highschool.get();
    }
    
    public void setHighschool(String newHighschool) {
        _highschool.set(newHighschool);
    }
    
    
    // Get/Set Leadership Positions
    public String getLeadershipPositions() {
        return _leadershipPos.get();
    }
    
    public void setLeadershipPositions(String newLeadershipPos) {
        _leadershipPos.set(newLeadershipPos);
    }
    
    
    // Get/Set is legacy
    public boolean getIsLegacy() {
        return _isLegacy;
    }
    
    public void setisLegacy(boolean isLegacy) {
        _isLegacy = isLegacy;
    }
    
    
    // Get/Set Legacy Information
    public String getLegacyInformation() {
        return _legacyInfo.get();
    }
    
    public void setLegacyInformation(String newLegacyInfo) {
        _legacyInfo.set(newLegacyInfo);
    }
}