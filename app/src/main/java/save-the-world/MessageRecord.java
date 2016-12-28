package com.savetheworld;

import java.util.Date;

public class MessageRecord {

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
}