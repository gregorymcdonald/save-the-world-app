package com.savetheworld;

public class Contact {
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
    public Contact() {
        _eid = NULL;
        _firstName = NULL;
        _lastName = NULL;
        _phoneNumber = NULL;
        _email = NULL;
        _major = NULL;
        _hometown = NULL;
        _housing = NULL;
        _highschool = NULL;
        _leadershipPos = NULL;
        _isLegacy = NULL;
        _legacyInfo = NULL;
        _recruitmentStatus = NULL;
    }
    
    // Primary constructor; should always be used
    public Contact(String eid, String firstName, String lastName, String phoneNumber, String email, String major, 
                   String hometown, String housing, String highschool, String leadershipPos, boolean isLegacy, 
                   String legacyInfo, Status recruitmentStatus) {
        _eid = eid;
        _firstName = firstName;
        _lastName = lastName;
        _phoneNumber = phoneNumber;
        _email = email;
        _major = major;
        _hometown = hometown;
        _housing = housing;
        _highschool = highschool;
        _leadershipPos = leadershipPos;
        _isLegacy = isLegacy;
        _legacyInfo = legacyInfo;
        _recruitmentStatus = recruitmentStatus;
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
        _email = email;
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