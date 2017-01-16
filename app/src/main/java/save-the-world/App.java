package com.savetheworld;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.json.simple.JSONObject;

import java.util.*;

public class App {
    // The phone number from which messages are sent.
    private static final String TWILIO_PHONE_NUMBER = "+15162102347";

    // XXX: Developer phone number(s), for testing. Remove once development is complete.
    private static final String ADAM_PHONE_NUMBER = "+15163533154";
    private static final String GREG_PHONE_NUMBER = "+14692379287";
    private static final String GREG_PHONE_NUMBER_WITHOUT_EXTENSION = "4692379287";
    private static final String KIERAN_PHONE_NUMBER = "+17136208645";

    public static void main(String[] args) {
        // Test Contact Saving
        Database db = Database.getInstance();
        db.pull();
        ContactRecord result = db.getContact(GREG_PHONE_NUMBER_WITHOUT_EXTENSION);
        System.out.println(result.toJSONString());
        Map contactInformationMap = new HashMap<String, String>();
        contactInformationMap.put(ContactRecord.EID_COL, "ae22675");
        contactInformationMap.put(ContactRecord.FIRST_NAME, "Adam");
        contactInformationMap.put(ContactRecord.LAST_NAME, "Estrin");
        contactInformationMap.put(ContactRecord.PHONE_NUMBER_COL, "5163533154");
        ContactRecord test = new ContactRecord(contactInformationMap);
        db.saveContact(test);

        // Test Conversation Saving
        // Database db = Database.getInstance();
        // db.pull();
        // ConversationRecord result = db.getConversation(TWILIO_PHONE_NUMBER, GREG_PHONE_NUMBER);
        // System.out.println(result.toJSONString());

        // ConversationRecord test = new ConversationRecord(TWILIO_PHONE_NUMBER, ADAM_PHONE_NUMBER, null);
        // test.addMessage(new MessageRecord(ADAM_PHONE_NUMBER, TWILIO_PHONE_NUMBER, "Hi Adam!", new Date(), false));
        // db.saveConversation(test);
        // db.push();

        // Test messaging
        //  Messenger messenger = Messenger.getInstance();
        //  messenger.sendSMS(GREG_PHONE_NUMBER, "Testing 1, 2, 3.");

        // Test parsing
        // ArrayList <Contact> contacts = Parser.parseFile();
        // System.out.println(contacts.get(0).getFirstName());
    }
}