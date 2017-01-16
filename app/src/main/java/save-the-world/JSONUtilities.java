package com.savetheworld;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class JSONUtilities {

    /**
     * Parse a JSONObject from a string containing JSON.
     * @param json A String containing JSON.
     * @return A JSONObject that that shares the same data as the input JSON.
     */
    public static JSONObject createJSONObject(String json){
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

    /**
     * Converts a JSONObject to a ContactRecord.
     * @param firebaseId The unique ID assigned to the Firebase JSON object.
     * @param json A JSONObject representing the ContactRecord.
     * @return A ContactRecord containing the data in the input JSON.
     */
    public static ContactRecord convertJSONToContactRecord(String firebaseId, JSONObject json){
        String eid = (String) json.get("eid");
        String firstName = (String) json.get("first_name");
        String lastName = (String) json.get("last_name");
        String phoneNumber = (String) json.get("phone_number");

        Map contactInformationMap = new HashMap<String, String>();
        contactInformationMap.put(ContactRecord.EID_COL, eid);
        contactInformationMap.put(ContactRecord.FIRST_NAME, firstName);
        contactInformationMap.put(ContactRecord.LAST_NAME, lastName);
        contactInformationMap.put(ContactRecord.PHONE_NUMBER_COL, phoneNumber);

        return new ContactRecord(contactInformationMap);
    }

    /**
     * Converts a JSONObject to a ConversationRecord.
     * @param firebaseId The unique ID assigned to the Firebase JSON object.
     * @param json A JSONObject representing the ConversationRecord.
     * @return A ConversationRecord containing the data in the input JSON.
     */
    public static ConversationRecord convertJSONToConversationRecord(String firebaseId, JSONObject json){
        String participant1 = (String) json.get("participant1");
        String participant2 = (String) json.get("participant2");
        JSONObject messagesJsonObject = (JSONObject) json.get("messages");

        List<MessageRecord> conversationMessages = new ArrayList<MessageRecord>();
        Set<String> messageKeys = (Set<String>) messagesJsonObject.keySet();
        int numMessages = messageKeys.size();
        System.out.println(numMessages + " message(s) found.");

        for(String messageKey : messageKeys){
            conversationMessages.add(convertJSONToMessageRecord(messageKey, (JSONObject)messagesJsonObject.get(messageKey)));
        }

        return new ConversationRecord(firebaseId, participant1, participant2, conversationMessages);
    }

    /**
     * Converts a JSONObject to a MessageRecord.
     * @param firebaseId The unique ID assigned to the Firebase JSON object.
     * @param json A JSONObject representing the MessageReord.
     * @return A MessageRecord containing the data in the input JSON.
     */
    public static MessageRecord convertJSONToMessageRecord(String firebaseId, JSONObject json){
        String to = (String) json.get("to");
        String from = (String) json.get("from");
        String body = (String) json.get("body");
        Long timestamp = (Long) json.get("timestamp");
        Boolean read = (json.get("read") != null) ? ((Boolean) json.get("read")) : false;

        return new MessageRecord(firebaseId, to, from, body, new Date(timestamp), read);
    }
}