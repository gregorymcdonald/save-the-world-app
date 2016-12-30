package com.savetheworld;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test (s) for the Database class.
 */
public class DatabaseTest extends TestCase {
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
    public void testGetInstance() {
        Database instance = Database.getInstance();
        assertNotNull("Database singleton instance should not be null.", instance);
    }
}
