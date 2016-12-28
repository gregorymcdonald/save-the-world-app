package com.savetheworld;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class App {
    // The phone number from which messages are sent.
    private static final String TWILIO_PHONE_NUMBER = "+15162102347";

    // XXX: Developer phone number(s), for testing. Remove once development is complete.
    private static final String ADAM_PHONE_NUMBER = "+15163533154";
    private static final String GREG_PHONE_NUMBER = "+14692379287";
    private static final String KIERAN_PHONE_NUMBER = "+17136208645";

    public static void main(String[] args) {
        // Test Firebase
        JSONObject result = Database.getInstance().getConversation(TWILIO_PHONE_NUMBER, GREG_PHONE_NUMBER);
        System.out.println(result);

        // Test messaging
        //  Messenger messenger = Messenger.getInstance();
        //  messenger.sendSMS(GREG_PHONE_NUMBER, "Testing 1, 2, 3.");

        // Test parsing
        // ArrayList <Contact> contacts = Parser.parseFile();
        // System.out.println(contacts.get(0).getFirstName());
    }
}