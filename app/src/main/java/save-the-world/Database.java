package com.savetheworld;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.concurrent.atomic.AtomicBoolean;

public class Database {
  
    // Singleton instance
    private static Database instance = new Database();

    // Firebase
    private static final String FIREBASE_URL = "https://save-the-world-c2795.firebaseio.com/";
    private Firebase firebaseReference;

    private Database(){
        // Database is created in a connected state
        firebaseReference = new Firebase(FIREBASE_URL);
    }

    public static Database getInstance(){
        return instance;
    }

    public void saveConversation(String message){
        final AtomicBoolean done = new AtomicBoolean(false);

        firebaseReference.child("conversations").push().setValue(message, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                done.set(true);
            }
        });

        // Blocks until write completes
        while (!done.get());
    }

    public void connect(){
        firebaseReference.goOnline();
    }

    public void disconnect(){
        firebaseReference.goOffline();
    }
}