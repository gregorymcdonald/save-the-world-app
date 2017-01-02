package com.savetheworld;

import java.util.Date;

import java.lang.NullPointerException;
import java.lang.ClassCastException;

import org.json.simple.JSONObject;

public class MessageRecord extends Record implements Comparable<MessageRecord> {

    public String to;
    public String from;
    public String body;
    // NOTE: this timestamp should be treated as immutable
    public Date timestamp;

    public MessageRecord(String to, String from, String body) {
        this(to, from, body, new Date());
    }

    public MessageRecord(String id, String to, String from, String body) {
        this(id, to, from, body, new Date());
    }

    public MessageRecord(String to, String from, String body, Date timestamp) {
        super();
        this.to = to;
        this.from = from;
        this.body = body;
        this.timestamp = timestamp;
    }

    public MessageRecord(String id, String to, String from, String body, Date timestamp) {
        super(id);
        this.to = to;
        this.from = from;
        this.body = body;
        this.timestamp = timestamp;
    }

    public MessageRecord(MessageRecord messageRecord){
        super(messageRecord.getId());
        this.to = messageRecord.to;
        this.from = messageRecord.from;
        this.body = messageRecord.body;
        this.timestamp = messageRecord.timestamp;
    }   

    @Override
    public int compareTo(MessageRecord messageRecord){
        if(messageRecord == null){
            throw new NullPointerException("Specified object is null in comparison of MessageRecord(s).");
        } else if (!(messageRecord instanceof MessageRecord)) {
            throw new ClassCastException("Specified object's type prevents it from being compared to a MessageRecord.");
        }

        return this.timestamp.compareTo(messageRecord.timestamp);
    }

    /**
     * Returns a string representation of this MessageRecord in JSON format.
     * @return A String representation of this MessageRecord in JSON format.
      */
    @Override
    public String toJSONString(){
        JSONObject json = new JSONObject();
        json.put("body", this.body);
        json.put("to", this.to);
        json.put("from", this.from);
        json.put("timestamp", this.timestamp.getTime());
        return json.toJSONString();
    }
}