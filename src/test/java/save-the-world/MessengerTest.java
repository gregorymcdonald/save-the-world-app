package com.savetheworld;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MessengerTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessengerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MessengerTest.class);
    }

    /**
     * Test whether getInstance returns a valid instance
     */
    public void testGetInstance() {
        Messenger instance = Messenger.getInstance();
        assertNotNull("Messenger singleton instance should not be null.", instance);
    }

    /**
     * Rigourous Test :-)
     */
    public void testSmsSuccess() {
        String validPhoneNumber = "+14692379287";
        String validMessageText = "This is some valid message text.";

        Messenger instance = Messenger.getInstance();
        boolean sendSmsResult = instance.sendSMS(validPhoneNumber, validMessageText);
        assertTrue("Message should SUCCEED with valid phone number and valid message text.", sendSmsResult);
    }

    public void testSmsFailure() {
        String validPhoneNumber = "+14692379287";
        String validMessageText = "This is some valid message text.";
        String invalidPhoneNumber = "469237928";
        String invalidMessageText = "";

        Messenger instance = Messenger.getInstance();
        boolean sendSmsResult1 = instance.sendSMS(invalidPhoneNumber, invalidMessageText);
        assertFalse("Message should FAIL with invalid phone number and invalid message.", sendSmsResult1);

        boolean sendSmsResult2 = instance.sendSMS(validPhoneNumber, invalidMessageText);
        assertFalse("Message should FAIL with valid phone number and invalid message.", sendSmsResult2);

        boolean sendSmsResult3 = instance.sendSMS(invalidPhoneNumber, validMessageText);
        assertFalse("Message should FAIL with invalid phone number and valid message.", sendSmsResult3);
    }
}
