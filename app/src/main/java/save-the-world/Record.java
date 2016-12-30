package com.savetheworld;

public abstract class Record {

    private String id;

    public Record(String id) {
        this.id = id;
    }

    protected void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}