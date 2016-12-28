package com.savetheworld;

import java.util.Date;

import java.lang.NullPointerException;
import java.lang.ClassCastException;

public class MessageRecord implements Comparable<MessageRecord> {

    public String to;
    public String from;
    public String body;
    // NOTE: this timestamp should be treated as immutable
    public Date timestamp;

    public MessageRecord(String to, String from, String body) {
        this(to, from, body, new Date());
    }

    public MessageRecord(String to, String from, String body, Date timestamp) {
        this.to = to;
        this.from = from;
        this.body = body;
        this.timestamp = timestamp;
    }

    public MessageRecord(MessageRecord messageRecord){
        this.to = messageRecord.to;
        this.from = messageRecord.from;
        this.body = messageRecord.body;
        this.timestamp = messageRecord.timestamp;
    }

    public int compareTo(MessageRecord messageRecord){
        if(messageRecord == null){
            throw new NullPointerException("Specified object is null in comparison of MessageRecord(s).");
        } else if (!(messageRecord instanceof MessageRecord)) {
            throw new ClassCastException("Specified object's type prevents it from being compared to a MessageRecord.");
        }

        return this.timestamp.compareTo(messageRecord.timestamp);
    }
}