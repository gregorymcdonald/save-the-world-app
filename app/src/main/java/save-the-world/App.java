package com.savetheworld;

import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import java.io.IOException;

public class App {
    // The phone number from which messages are sent.
    private static final String TWILIO_PHONE_NUMBER = "+15162102347";

    // XXX: Developer phone number(s), for testing. Remove once development is complete.
    private static final String ADAM_PHONE_NUMBER = "+15163533154";
    private static final String GREG_PHONE_NUMBER = "+14692379287";
    private static final String KIERAN_PHONE_NUMBER = "+17136208645";

    public static void main(String[] args) {
        // Pull db when app launches
        Database db = Database.getInstance();
        db.pull();
        // ConversationRecord result = db.getConversation(TWILIO_PHONE_NUMBER, GREG_PHONE_NUMBER);
        // System.out.println(result.toJSONString());

        // ConversationRecord test = new ConversationRecord(TWILIO_PHONE_NUMBER, ADAM_PHONE_NUMBER, null);
        // test.addMessage(new MessageRecord(ADAM_PHONE_NUMBER, TWILIO_PHONE_NUMBER, "Hi Adam!", new Date()));
        // db.saveConversation(test);
        // db.push();

        // Test messaging
        //  Messenger messenger = Messenger.getInstance();
        //  messenger.sendSMS(GREG_PHONE_NUMBER, "Testing 1, 2, 3.");

        // Test parsing
        // ArrayList <Contact> contacts = Parser.parseFile();
        // System.out.println(contacts.get(0).getFirstName());
      
        //Retreive the most recent IFC CSV from Google Drive
        try {
          GoogleDriveManager.downloadMostRecentCSV();
        } catch (IOException e) {
          System.out.println("Failure to download IFC CSV...");
          System.out.println(e);
        }

        //launch Application GUI
        Application.launch(GUI.class, args);
    }

}