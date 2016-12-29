package com.savetheworld;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Database {

    // Singleton instance
    private static Database instance = new Database();

    // Firebase URL
    private static final String FIREBASE_URL = "https://save-the-world-c2795.firebaseio.com";

    // All ConversationRecord(s) in the database
    List<ConversationRecord> conversations;

    private Database(){
        refresh();
    }

    public static Database getInstance(){
        return instance;
    }

    public void refresh(){
        System.out.println("Refreshing local database instance from remote...");
        conversations = new ArrayList<ConversationRecord>();

        // Read all conversations from Firebase
        System.out.println("Reading conversations...");
        String conversationsUrl = FIREBASE_URL + "/conversations.json";
        String conversationsJsonString = readDataFromFirebase(conversationsUrl);
        JSONObject conversationsJsonObject = parseJSONObject(conversationsJsonString);

        Set<String> conversationKeys = (Set<String>) conversationsJsonObject.keySet();
        int numConversations = conversationKeys.size();
        System.out.println(numConversations + " conversation(s) found.");

        for(String conversationKey : conversationKeys){
            conversations.add(convertJsonToConversationRecord( (JSONObject) conversationsJsonObject.get(conversationKey)));
        }
    }

    public ConversationRecord getConversation(String participantPhoneNumber, String otherParticipantPhoneNumber){
        for(ConversationRecord conversationRecord : conversations){
            if((conversationRecord.participant1.equals(participantPhoneNumber) || conversationRecord.participant2.equals(participantPhoneNumber))
                && (conversationRecord.participant1.equals(otherParticipantPhoneNumber) || conversationRecord.participant2.equals(otherParticipantPhoneNumber))){
                return new ConversationRecord(conversationRecord);
            }
        }
        return null;
    }

    public List<ConversationRecord> getAllConversations(){
        return new ArrayList<ConversationRecord>(conversations);
    }

    private ConversationRecord convertJsonToConversationRecord(JSONObject json){
        String participant1 = (String) json.get("participant1");
        String participant2 = (String) json.get("participant2");
        JSONObject messagesJsonObject = (JSONObject) json.get("messages");

        List<MessageRecord> conversationMessages = new ArrayList<MessageRecord>();
        Set<String> messageKeys = (Set<String>) messagesJsonObject.keySet();
        int numMessages = messageKeys.size();
        System.out.println(numMessages + " message(s) found.");

        for(String messageKey : messageKeys){
            conversationMessages.add(convertJsonToMessageRecord( (JSONObject) messagesJsonObject.get(messageKey)));
        }

        return new ConversationRecord(participant1, participant2, conversationMessages);
    }

    private MessageRecord convertJsonToMessageRecord(JSONObject json){
        String to = (String) json.get("to");
        String from = (String) json.get("from");
        String body = (String) json.get("body");
        Long timestamp = (Long) json.get("timestamp");

        return new MessageRecord(to, from, body, new Date(timestamp));
    }

    private String readDataFromFirebase(String urlPath){
        if(!urlPath.startsWith("https")){
            System.err.println("Reads from Firebase must use HTTPS.");
            return null;
        } else if (!urlPath.endsWith(".json")){
            System.err.println("Reads from Firebase must return json.");
            return null;
        }

        // Attempt the read
        try{
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            switch (responseCode) {
                case 200:
                case 201:
                    BufferedReader connectionStreamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String connectionResult = "";
                    String line;
                    while ((line = connectionStreamReader.readLine()) != null) {
                        connectionResult += line;
                    }
                    connectionStreamReader.close();
                    return connectionResult;
                default:
                    System.err.println("Connection failed with response code " + responseCode + ".");
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error occurred while get conversation over HTTPS.");
        }
        return null;
    }

    private JSONObject parseJSONObject(String json){
        JSONObject result = null;
        if(json != null && json.length() > 0){
            try {
                result = (JSONObject) (new JSONParser()).parse(json);
            } catch (ParseException pe) {
                System.err.println("JSON Parse exception occurred.");
                pe.printStackTrace();
            }
        }
        return result;
    }

    /* // DEPRECATED BELOW, uses deprecated API
    import com.firebase.client.Firebase;
    import com.firebase.client.FirebaseError;
    import java.util.concurrent.atomic.AtomicBoolean;

    // Firebase
    private static final String FIREBASE_URL = "https://save-the-world-c2795.firebaseio.com/";
    private Firebase firebaseReference;

    private Database(){
        // Database is created in a connected state
        firebaseReference = new Firebase(FIREBASE_URL);
    }

    public void saveConversation(String message){
        final AtomicBoolean done = new AtomicBoolean(false);

        firebaseReference.child("conversations").push().setValue(message, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                done.set(true);
            }
        });

        // Blocks until write completes
        while (!done.get());
    }

    public void connect(){
        firebaseReference.goOnline();
    }

    public void disconnect(){
        firebaseReference.goOffline();
    }
    */
}