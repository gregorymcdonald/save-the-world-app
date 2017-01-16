package com.savetheworld;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Database {

    // Singleton instance
    private static Database instance = new Database();

    // Test Mode
    // If enabled, remote is stored at a different location as to not clobber the real data.
    private boolean testMode;

    // All ConversationRecord(s) in the database.
    List<ConversationRecord> conversations;

    // All ContactRecord(s) in the database.
    List<ContactRecord> contacts;

    /**
     * Singleton instance constructor.
     */
    private Database(){
        testMode = false;
        conversations = new ArrayList<ConversationRecord>();
        contacts = new ArrayList<ContactRecord>();
    }

    /**
     * Update the local copy of the database from the remote. Overrides all local changes that have not been pushed.
     * @return A Database instance.
     */
    public static Database getInstance(){
        return instance;
    }

    /**
     * Clear the local copy of the database, all changes will be discarded.
     */
    public void clear(){
        System.out.println("Clearing local database...");
        conversations = new ArrayList<ConversationRecord>();
        contacts = new ArrayList<ContactRecord>();
    }

    /**
     * Update the local copy of the database from the remote. Overrides all local changes that have not been pushed.
     */
    public void pull(){
        clear();
        System.out.println("Copying database from remote...");

        // Read all conversations from Firebase
        System.out.println("Reading conversations...");
        String conversationsUrl;
        if(!testMode){
            conversationsUrl = FirebaseUtilities.FIREBASE_URL + "/conversations.json";
        } else {
            conversationsUrl = FirebaseUtilities.FIREBASE_URL + "/test_conversations.json";
        }
        String conversationsJsonString = FirebaseUtilities.readDataFromFirebase(conversationsUrl);
        JSONObject conversationsJsonObject = JSONUtilities.createJSONObject(conversationsJsonString);
        if(conversationsJsonObject != null){
            Set<String> conversationKeys = (Set<String>) conversationsJsonObject.keySet();
            int numConversations = conversationKeys.size();
            System.out.println(numConversations + " conversation(s) found.");

            for(String conversationKey : conversationKeys){
                conversations.add(JSONUtilities.convertJSONToConversationRecord(conversationKey, (JSONObject)conversationsJsonObject.get(conversationKey)));
            }
        }

        // Read all contact(s) from Firebase
        System.out.println("Reading contacts...");
        String contactsUrl = getContactsFirebaseUrl() + ".json";
        String contactsJsonString = FirebaseUtilities.readDataFromFirebase(contactsUrl);
        JSONObject contactsJsonObject = JSONUtilities.createJSONObject(contactsJsonString);
        Set<String> contactKeys = (Set<String>) contactsJsonObject.keySet();
        int numContacts = contactKeys.size();
        System.out.println(numContacts + " contact(s) found.");
        for(String contactKey : contactKeys){
            contacts.add(JSONUtilities.convertJSONToContactRecord(contactKey, (JSONObject)contactsJsonObject.get(contactKey)));
        }
    }

    /**
     * Update the remote copy of the database from the local changes. Overrides all data stored remotely.
     */
    public void push(){
        System.out.println("Overriding remote database with local copy...");
        String conversationsURL = getConversationsFirebaseUrl() + ".json";

        // Convert list of ConversationRecord(s) into a JSONObject
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

        // Write the conversations JSON object to Firebase
        String conversationsWriteResult = FirebaseUtilities.writeDataToFirebase(conversationsURL, conversationsJsonObject.toJSONString());
        System.out.println(conversationsWriteResult);

        // Set the "lastUpdated" field of the remote database to the current time
        String lastUpdatedURL = FirebaseUtilities.FIREBASE_URL + "/last_updated.json";
        String currentTimeInMilliseconds = "" + System.currentTimeMillis();
        String lastUpdatedWriteResult = FirebaseUtilities.writeDataToFirebase(lastUpdatedURL, currentTimeInMilliseconds);
        System.out.println("Remote database now last updated at time: " + lastUpdatedWriteResult);
    }

    /**
     * Get a single contact from the local copy of the database.
     * @param phoneNumber A String containing a phone number of the contact.
     * @return A ContactRecord with matching phone number.
     */
    public ContactRecord getContact(String phoneNumber){
        for(ContactRecord contactRecord : contacts){
            if(phoneNumber.equals(contactRecord.getPhoneNumber())){
                return contactRecord;
            }
        }
        return null;
    }

    /**
     * Get a list of all contact(s) stored in the local copy of the database.
     * @return A list of all contact(s) stored in the local database, represented as ContactRecord(s).
     */
    public List<ContactRecord> getAllContacts(){
        return new ArrayList<ContactRecord>(contacts);
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
     * Save a single contact to the local and remote copy of the database.
     * @param contactRecord The ContactRecord to save.
     */
    public void saveContact(ContactRecord contactRecord){
        if(contactRecord == null){
            return;
        }
        contacts.remove(contactRecord);

        // Assign unique ID to the conversation if it does not have one
        String contactId = contactRecord.getId();
        if(contactId == null || contactId.length() <= 0){
            contactRecord.setId(FirebaseUtilities.generateUniqueId());
        }

        contacts.add(contactRecord);

        // Save the contact in the remote database
        String contactUrl = getContactsFirebaseUrl() + "/" + contactRecord.getId() + ".json";
        System.out.println("Saving contact: " + contactRecord.toJSONString() + " to " + contactUrl);
        String contactWriteResult = FirebaseUtilities.writeDataToFirebase(contactUrl, contactRecord.toJSONString());
        //System.out.println(contactWriteResult);
    }

    /**
     * Save a single conversation to the local and remote copy of the database.
     * @param conversationRecord The ConversationRecord to save.
     */
    public void saveConversation(ConversationRecord conversationRecord){
        if(conversationRecord == null){
            return;
        }

        conversations.remove(conversationRecord);

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

        // Save the conversation in the remote database
        String conversationURL = getConversationsFirebaseUrl() + "/" + conversationRecord.getId() + ".json";
        String conversationWriteResult = FirebaseUtilities.writeDataToFirebase(conversationURL, conversationRecord.toJSONString());
        //System.out.println(conversationWriteResult);
    }

    /**
     * Save a single message to the local copy of the database.
     * @param messageRecord The MessageRecord to save.
     */
    public void saveMessage(MessageRecord messageRecord){
        if(messageRecord == null){
            return;
        }

        ConversationRecord conv = this.getConversation(messageRecord.to, messageRecord.from);
        if(conv == null) {
            conv = new ConversationRecord(messageRecord.to, messageRecord.from, null);
        }
        conv.addMessage(messageRecord);
        this.saveConversation(conv);
    }

    /**
     * Enable test mode.
     */
    public void enableTestMode(){
        this.testMode = true;
    }

    /**
     * Disable test mode.
     */
    public void disableTestMode(){
        this.testMode = false;
    }

    private String getContactsFirebaseUrl(){
        if(!testMode){
            return FirebaseUtilities.FIREBASE_URL + "/contacts";
        } else {
            return FirebaseUtilities.FIREBASE_URL + "/test_contacts";
        }
    }

    private String getConversationsFirebaseUrl(){
        if(!testMode){
            return FirebaseUtilities.FIREBASE_URL + "/conversations";
        } else {
            return FirebaseUtilities.FIREBASE_URL + "/test_conversations";
        }
    }
}