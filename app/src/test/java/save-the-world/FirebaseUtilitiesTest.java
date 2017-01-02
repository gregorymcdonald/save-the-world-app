package com.savetheworld;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test(s) for the FirebaseUtilitiesTest class.
 */
public class FirebaseUtilitiesTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FirebaseUtilitiesTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(FirebaseUtilitiesTest.class);
    }

    /**
     * Test if generateUniqueId returns a valid, non-null String.
     */
    public void test_generateUniqueIdNotNull() {
        String uniqueId = FirebaseUtilities.generateUniqueId();
        assertNotNull("Firebase unique id should not be null.", uniqueId);
    }
}
