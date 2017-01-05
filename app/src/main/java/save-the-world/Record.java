package com.savetheworld;

public abstract class Record {

    private String id;

    public Record(){
        this.id = "";
    }

    public Record(String id) {
        this.id = id;
    }

    protected void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    /**
     * Returns a string representation of this Record in JSON format.
     * @return A String representation of this Record in JSON format.
      */
    public abstract String toJSONString();
}