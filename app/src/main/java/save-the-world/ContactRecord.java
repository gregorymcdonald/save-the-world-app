package com.savetheworld;

import java.util.Map;
import org.json.simple.JSONObject;

public class ContactRecord extends Record {

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
    private String _eid;
    private String _firstName;
    private String _lastName;
    private String _phoneNumber;
    private String _email;
    private String _major;
    private String _hometown;
    private String _housing;
    private String _highschool;
    private String _leadershipPos;
    private String _classification;
    
    // Optional Legacy Info
    private boolean _isLegacy;
    private String _legacyInfo;
    
    // Recruitment Status Info
    public enum Status { 
        // TODO: Clean-up names a bit?
        NONE, CONTACTED, BEEN_TO_EVENT, BID_READY,
        BID_OUT, ACCEPTED, DECLINED, DEFFERED
    }
    private Status _recruitmentStatus;
    
    // Should not use this constructor other than to test
    public ContactRecord() {
        _eid = null;
        _firstName = null;
        _lastName = null;
        _phoneNumber = null;
        _email = null;
        _major = null;
        _hometown = null;
        _housing = null;
        _highschool = null;
        _leadershipPos = null;
        _isLegacy = false;
        _legacyInfo = null;
        _recruitmentStatus = null;
    }
    
    // Primary constructor; should always be used
    public ContactRecord(Map <String, String> record) {
        _eid = record.get(EID_COL);
        _firstName = record.get(FIRST_NAME);
        _lastName = record.get(LAST_NAME);
        _phoneNumber = record.get(PHONE_NUMBER_COL);
        _email = record.get(EMAIL_COL);
        _major = record.get(MAJOR_COL);
        _hometown = record.get(HOME_TOWN_COL);
        _housing = record.get(HOUSING_COL);
        _highschool = record.get(HIGH_SCHOOL_COL);
        _leadershipPos = record.get(POSITIONS_COL);
        _legacyInfo = record.get(LEGACY_COL);
        _classification = record.get(CLASSIFICATION_COL);
    }
    
    /**
     * Returns a string representation of this ContactRecord in JSON format.
     * @return A String representation of this ContactRecord in JSON format.
      */
    @Override
    public String toJSONString(){
        // XXX Greg 1/16/2017 : Inlude other fields if necessary.

        JSONObject json = new JSONObject();
        json.put("eid", this._eid);
        json.put("first_name", this._firstName);
        json.put("last_name", this._lastName);
        json.put("phone_number", this._phoneNumber);
        return json.toJSONString();
    }

    // Begin massive list of getters and setters
    // Get/Set EID
    public String getEid() {
        return _eid;
    }
    
    public void setEid(String newEid) {
        _eid = newEid;
    }
    
    
    // Get/Set First Name
    public String getFirstName() {
        return _firstName;
    }
    
    public void setFirstName(String newFirstName) {
        _firstName = newFirstName;
    }
    
    
    // Get/Set Last Name
    public String getLastName() {
        return _lastName;
    }
    
    public void setLastName(String newLastName) {
        _lastName = newLastName;
    }
    
    
    // Get/Set Phone Number
    public String getPhoneNumber() {
        return _phoneNumber;
    }
    
    public void setPhoneNumber(String newPhoneNumber) {
        _phoneNumber = newPhoneNumber;
    }
    
    
    // Get/Set email address
    public String getEmail() {
        return _email;
    }
    
    public void setEmail(String newEmail) {
        _email = newEmail;
    }
    
    
    // Get/Set Major
    public String getMajor() {
        return _major;
    }
    
    public void setMajor(String newMajor) {
        _major = newMajor;
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
        return _housing;
    }
    
    public void setHousing(String newHousing) {
        _housing = newHousing;
    }
    
    
    // Get/Set Hometown
    public String getHometown() {
        return _hometown;
    }
    
    public void setHometown(String newHometown) {
        _hometown = newHometown;
    }
    
    
    // Get/Set Highschool
    public String getHighschool() {
        return _highschool;
    }
    
    public void setHighschool(String newHighschool) {
        _highschool = newHighschool;
    }
    
    
    // Get/Set Leadership Positions
    public String getLeadershipPositions() {
        return _leadershipPos;
    }
    
    public void setLeadershipPositions(String newLeadershipPos) {
        _leadershipPos = newLeadershipPos;
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
        return _legacyInfo;
    }
    
    public void setLegacyInformation(String newLegacyInfo) {
        _legacyInfo = newLegacyInfo;
    }
}