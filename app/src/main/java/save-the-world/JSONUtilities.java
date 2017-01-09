package com.savetheworld;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

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

        return new MessageRecord(firebaseId, to, from, body, new Date(timestamp));
    }
    
    /**
     * Converts a JSONObject to a Contact.
     * @param firebaseID The unique ID assigned to the Firebase JSON object for the given Contact
     * @param json A JSONObject representing the Contact
     * @return A Contact object containing the data in the input JSON
     */
    public static Contact convertJSONToContact(String firebaseId, JSONObject json){
        Map <String, Object> record = new Map<>();
        record.put("eid", (StringProperty) json.get("eid"));
        record.put("firstName", (StringProperty) json.get("firstName"));
        record.put("lastName", (StringProperty) json.get("lastName"));
        record.put("phoneNumber", (StringProperty) json.get("phoneNumber"));
        record.put("email", (StringProperty) json.get("email"));
        record.put("major", (StringProperty) json.get("major"));
        record.put("hometown", (StringProperty) json.get("hometown"));
        record.put("housing", (StringProperty) json.get("housing"));
        record.put("highschool", (StringProperty) json.get("highschool"));
        record.put("leadershipPos", (StringProperty) json.get("leadershipPos"));
        record.put("classification", (StringProperty) json.get("classification"));
        record.put("legacyInfo", (StringProperty) json.get("legacyInfo"));
        record.put("isLegacy", (Boolean) json.get("isLegacy"));
        record.put("recruitmentStatus", (Status) json.get("recruitmentStatus"));
        
        return new Contact(record);
    }
}