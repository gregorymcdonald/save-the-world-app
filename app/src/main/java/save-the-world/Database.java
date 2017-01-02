package com.savetheworld;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Database {

    // Singleton instance
    private static Database instance = new Database();

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
        String conversationsUrl = FirebaseUtilities.FIREBASE_URL + "/conversations.json";
        String conversationsJsonString = FirebaseUtilities.readDataFromFirebase(conversationsUrl);
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
        String conversationsURL = FirebaseUtilities.FIREBASE_URL + "/conversations.json";

        // Override remote conversations
        JSONObject conversationsJsonObject = new JSONObject();
        for(ConversationRecord conversation : conversations) {
            String conversationId = conversation.getId();
            JSONObject conversationJSONObject = JSONUtilities.createJSONObject(conversation.toJSONString());
            if(conversationId != null && conversationId.length() > 0){
                conversationsJsonObject.put(conversation.getId(), conversationJSONObject);
            } else {
                conversationsJsonObject.put("ERROR_NO_CONVERSATION_ID", conversationJSONObject);
            }
        }

        String conversationsWriteResult = FirebaseUtilities.writeDataToFirebase(conversationsURL, conversationsJsonObject.toJSONString());
        System.out.println(conversationsWriteResult);

        // Set the "lastUpdated" field of the remoteDatabase to the current time
        String lastUpdatedURL = FirebaseUtilities.FIREBASE_URL + "/lastUpdated.json";
        String currentTimeInMilliseconds = "" + System.currentTimeMillis();
        String lastUpdatedWriteResult = FirebaseUtilities.writeDataToFirebase(lastUpdatedURL, currentTimeInMilliseconds);
        System.out.println("Remote database now last updated at time: " + lastUpdatedWriteResult);
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
        if(conversationRecord == null){
            return;
        }

        // Assign unique ID to the conversation if it does not have one
        String conversationId = conversationRecord.getId();
        if(conversationId == null || conversationId.length() <= 0){
            conversationRecord.setId(FirebaseUtilities.generateUniqueId());
        }

        // Assign unique ID(s) to the conversation messages if it does not have one
        List<MessageRecord> conversationMessages = conversationRecord.getMessages();
        for(MessageRecord message : conversationMessages){
            String messageId = message.getId();
            if(messageId == null || messageId.length() <= 0){
                conversationRecord.removeMessage(message);
                message.setId(FirebaseUtilities.generateUniqueId());
                conversationRecord.addMessage(message);
            }
        }

        conversations.add(conversationRecord);
    }
}