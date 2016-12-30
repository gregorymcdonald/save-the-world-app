package com.savetheworld;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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
}