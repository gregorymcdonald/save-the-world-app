package com.savetheworld;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class ConversationRecord extends Record {

    public String participant1;
    public String participant2;
    private List<MessageRecord> messages;

    public ConversationRecord(String id, String participant1, String participant2, List<MessageRecord> messages) {
        super(id);
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.messages = new ArrayList<MessageRecord>(messages);
    }

    public ConversationRecord(ConversationRecord conversationRecord) {
        super(conversationRecord.getId());
        this.participant1 = conversationRecord.participant1;
        this.participant2 = conversationRecord.participant2;
        this.messages = new ArrayList<MessageRecord>(conversationRecord.messages);
    }

    public void addMessage(MessageRecord messageRecord){
        messages.add(messageRecord);
    }

    public List<MessageRecord> getAllMessages(){
        Collections.sort(messages);
        return new ArrayList<MessageRecord>(messages);
    }

    /**
     * Returns a string representation of this ConversationRecord in JSON format.
     * @return A String representation of this ConversationRecord in JSON format.
      */
    public String toJSONString(){
        JSONObject json = new JSONObject();
        json.put("participant1", this.participant1);
        json.put("participant2", this.participant2);

        JSONObject messagesJSONObject = new JSONObject();
        for(MessageRecord message : messages) {
            String messageId = message.getId();
            JSONObject messageJSONObject = JSONUtilities.createJSONObject(message.toJSONString());
            if(messageId != null && messageId.length() > 0){
                messagesJSONObject.put(message.getId(), messageJSONObject);
            } else {
                messagesJSONObject.put("NO_ID_ERROR", messageJSONObject);
            }
        }
        json.put("messages", messagesJSONObject);

        return json.toJSONString();
    }
}