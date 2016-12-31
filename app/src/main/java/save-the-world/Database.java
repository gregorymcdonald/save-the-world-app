package com.savetheworld;

import org.json.simple.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

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

    /**
     * Singleton instance constructor.
     */
    private Database(){
        conversations = new ArrayList<ConversationRecord>();
    }

    /**
     * Update the local copy of the database from the remote. Overrides all local changes that have not been pushed.
     * @return A Database instance.
     */
    public static Database getInstance(){
        return instance;
    }

    /**
     * Update the local copy of the database from the remote. Overrides all local changes that have not been pushed.
     */
    public void pull(){
        System.out.println("Copying database from remote...");
        conversations = new ArrayList<ConversationRecord>();

        // Read all conversations from Firebase
        System.out.println("Reading conversations...");
        String conversationsUrl = FIREBASE_URL + "/conversations.json";
        String conversationsJsonString = readDataFromFirebase(conversationsUrl);
        JSONObject conversationsJsonObject = JSONUtilities.createJSONObject(conversationsJsonString);

        Set<String> conversationKeys = (Set<String>) conversationsJsonObject.keySet();
        int numConversations = conversationKeys.size();
        System.out.println(numConversations + " conversation(s) found.");

        for(String conversationKey : conversationKeys){
            conversations.add(JSONUtilities.convertJSONToConversationRecord(conversationKey, (JSONObject)conversationsJsonObject.get(conversationKey)));
        }
    }

    /**
     * Update the remote copy of the database from the local changes. Overrides all data stored remotely.
     */
    public void push(){
        System.out.println("Overriding remote database with local copy...");
        String conversationsURL = FIREBASE_URL + "/conversations.json";

        // Override remote conversations
        JSONObject conversationsJsonObject = new JSONObject();
        for(ConversationRecord conversation : conversations) {
            String conversationId = conversation.getId();
            JSONObject conversationJSONObject = JSONUtilities.createJSONObject(conversation.toJSONString());
            if(conversationId != null && conversationId.length() > 0){
                conversationsJsonObject.put(conversation.getId(), conversationJSONObject);
            } else {
                conversationsJsonObject.put("NO_ID_ERROR", conversationJSONObject);
            }
        }

        String result = writeDataToFirebase(conversationsURL, conversationsJsonObject.toJSONString());
        System.out.println(result);
    }

    /**
     * Get a single conversation from the local copy of the database.
     * @param participantPhoneNumber A String containing a phone number of one participant in the conversation.
     * @param otherParticipantPhoneNumber A String containing a phone number of the other participant in the conversation.
     * @return A ConversationRecord corresponding to the conversation between the two input phone numbers, null if a matching conversation cannot be found.
     */
    public ConversationRecord getConversation(String participantPhoneNumber, String otherParticipantPhoneNumber){
        for(ConversationRecord conversationRecord : conversations){
            if((conversationRecord.participant1.equals(participantPhoneNumber) || conversationRecord.participant2.equals(participantPhoneNumber))
                && (conversationRecord.participant1.equals(otherParticipantPhoneNumber) || conversationRecord.participant2.equals(otherParticipantPhoneNumber))){
                return new ConversationRecord(conversationRecord);
            }
        }
        return null;
    }

    /**
     * Get a list of all conversation(s) stored in the local copy of the database.
     * @return A list of all conversation(s) stored in the local database, represented as ConversationRecord(s).
     */
    public List<ConversationRecord> getAllConversations(){
        return new ArrayList<ConversationRecord>(conversations);
    }

    /**
     * Save a single conversation to the local copy of the database.
     * @param conversationRecord The ConversationRecord to save.
     */
    public void saveConversation(ConversationRecord conversationRecord){
        // XXX write me
    }

    /**
     * Reads data from the remote Firebase database.
     * @param urlPath The URL path to read data from, must include the protocol (HTTPS) and end with ".json"
     * @return A String containing the result of the request, null if request failed.
     */
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

    /**
     * Write data to the remote Firebase database. The data must already have a value in the remote for the write to succeed.
     * @param urlPath The URL path to write data to, must include the protocol (HTTPS) and end with ".json"
     * @return A String containing the result of the request, null if request failed.
     */
    private String writeDataToFirebase(String urlPath, String content){
        if(!urlPath.startsWith("https")){
            System.err.println("Writes to Firebase must use HTTPS.");
            return null;
        } else if (!urlPath.endsWith(".json")){
            System.err.println("Writes to Firebase must target json.");
            return null;
        }

        // Attempt the read
        try{
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(content);
            out.flush();
            out.close();

            // Read result
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
        } finally {

        }
        return null;
    }
}