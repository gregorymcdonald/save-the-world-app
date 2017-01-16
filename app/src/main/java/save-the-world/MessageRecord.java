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
    public boolean read;

    public MessageRecord(String to, String from, String body) {
        this(to, from, body, new Date(), false);
    }

    public MessageRecord(String to, String from, String body, boolean read) {
        this(to, from, body, new Date(), read);
    }

    public MessageRecord(String id, String to, String from, String body, boolean read) {
        this(id, to, from, body, new Date(), read);
    }

    public MessageRecord(String to, String from, String body, Date timestamp, boolean read) {
        super();
        this.to = to;
        this.from = from;
        this.body = body;
        this.timestamp = timestamp;
        this.read = read;
    }

    public MessageRecord(String id, String to, String from, String body, Date timestamp, boolean read) {
        super(id);
        this.to = to;
        this.from = from;
        this.body = body;
        this.timestamp = timestamp;
        this.read = read;
    }

    public MessageRecord(MessageRecord messageRecord){
        super(messageRecord.getId());
        this.to = messageRecord.to;
        this.from = messageRecord.from;
        this.body = messageRecord.body;
        this.timestamp = messageRecord.timestamp;
        this.read = read;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof MessageRecord)){
            return false;
        }

        MessageRecord argument = (MessageRecord) o;
        return this.to.equals(argument.to) && this.from.equals(argument.from) 
            && this.body.equals(argument.body) && this.timestamp.equals(argument.timestamp);
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
        json.put("read", this.read);
        return json.toJSONString();
    }
}