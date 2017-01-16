package com.savetheworld;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * Unit test (s) for the Database class.
 */
public class DatabaseTest extends TestCase {
    // Example phone numbers.
    private static final String TWILIO_PHONE_NUMBER = "+15162102347";
    private static final String ADAM_PHONE_NUMBER = "+15163533154";
    private static final String GREG_PHONE_NUMBER = "+14692379287";
    private static final String KIERAN_PHONE_NUMBER = "+17136208645";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    /**
     * Test if getInstance returns a valid, non-null instance.
     */
    public void test_getInstance() {
        Database instance = Database.getInstance();
        assertNotNull("Database singleton instance should not be null.", instance);
    }

    /**
     * Test that clear removes local copy of database.
     */
    public void test_clear() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        assertTrue("After clear database should be empty.", db.getAllConversations().size() == 0);
        db.pull();
        db.clear();
        assertTrue("After pull and clear database should be empty.", db.getAllConversations().size() == 0);
    }

    /**
     * Tests that pull gets some data from remote. 
     */
    public void test_pull() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        db.pull();
        assertTrue("After pull database should not be empty.", db.getAllConversations().size() > 0);
    }

    /**
     * Test if push actual changes the remote.
     */
    public void test_push() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        db.pull();
        int numConversationsBeforePush = db.getAllConversations().size();
        ConversationRecord test = new ConversationRecord(TWILIO_PHONE_NUMBER, ADAM_PHONE_NUMBER, null);
        test.addMessage(new MessageRecord(ADAM_PHONE_NUMBER, TWILIO_PHONE_NUMBER, "Hi Adam!", new Date(), false));
        db.saveConversation(test);
        db.push();
        db.clear();
        db.pull();
        assertTrue("After push should commit changes to the remote.", db.getAllConversations().size() > numConversationsBeforePush);
    }

    /**
     * Test getAllContacts
     */
    public void test_getAllContacts() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        assertNotNull("Contacts list should not be null before pulling.", db.getAllContacts());
        assertTrue("Contacts list should be empty after clear", db.getAllContacts().size() == 0);
        db.pull();
        assertNotNull("Contacts list should not be null after pulling.", db.getAllContacts());
        assertTrue("Contacts list should contain at least 1 conversation after pulling.", db.getAllContacts().size() > 0);
    }

    /**
     * Test saveContact
     */
    public void test_saveContact() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        db.pull();

        Map contactInformationMap = new HashMap<String, String>();
        contactInformationMap.put(ContactRecord.EID_COL, "ae22675");
        contactInformationMap.put(ContactRecord.FIRST_NAME, "Adam");
        contactInformationMap.put(ContactRecord.LAST_NAME, "Estrin");
        contactInformationMap.put(ContactRecord.PHONE_NUMBER_COL, "5163533154");
        ContactRecord test = new ContactRecord(contactInformationMap);
        db.saveContact(test);
    }

    /**
     * Test getAllConversations
     */
    public void test_getAllConversations() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        assertNotNull("Conversations list should not be null before pulling.", db.getAllConversations());
        db.pull();
        assertNotNull("Conversations list should not be null after pulling.", db.getAllConversations());
        assertTrue("Conversations list should contain at least 1 conversation after pulling.", db.getAllConversations().size() > 0);
    }

    /**
     * Test if saveConversation and getConversation work properly together.
     */
    public void test_getConversation_saveConversation() {
        Database db = Database.getInstance();
        db.enableTestMode();
        db.clear();
        ConversationRecord test = new ConversationRecord(TWILIO_PHONE_NUMBER, ADAM_PHONE_NUMBER, null);
        test.addMessage(new MessageRecord(ADAM_PHONE_NUMBER, TWILIO_PHONE_NUMBER, "Hi Adam!", new Date(), false));
        db.saveConversation(test);
        ConversationRecord result = db.getConversation(test.participant1, test.participant2);
        assertEquals("Saved conversation should be equal to retrieved conversation.", test, result);
    }
}
