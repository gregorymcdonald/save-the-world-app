package com.savetheworld;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Database {
  
    // Singleton instance
    private static Database instance = new Database();

    private Database(){

    }

    public static Database getInstance(){
        return instance;
    }

    public JSONObject getAllConversations(String participantPhoneNumber, String otherParticipantPhoneNumber){
        String testUrl = "https://save-the-world-c2795.firebaseio.com/conversations.json";
        String jsonResult = readDataFromFirebase(testUrl);

        JSONObject result = null;
        if(jsonResult != null && jsonResult.length() > 0){
            try {
                result = (JSONObject) (new JSONParser()).parse(jsonResult);
            } catch (ParseException pe) {
                System.err.println("JSON Parse exception occurred.");
                pe.printStackTrace();
            }
        }
        return result;
    }

    private String readDataFromFirebase(String urlPath){
        if(!urlPath.startsWith("https")){
            System.err.println("Reads from Firebase must use HTTPS.");
            return null;
        } else if (!urlPath.endsWith(".json")){
            System.err.println("Reads from Firebase must return json.");
            return null;
        }

        // Attempt the read
        try{
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            switch (responseCode) {
                case 200:
                case 201:
                    BufferedReader connectionStreamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String connectionResult = "";
                    String line;
                    while ((line = connectionStreamReader.readLine()) != null) {
                        connectionResult += line;
                    }
                    connectionStreamReader.close();
                    return connectionResult;
                default:
                    System.err.println("Connection failed with response code " + responseCode + ".");
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error occurred while get conversation over HTTPS.");
        }
        return null;
    }

    /* // DEPRECATED BELOW, uses deprecated API
    import com.firebase.client.Firebase;
    import com.firebase.client.FirebaseError;
    import java.util.concurrent.atomic.AtomicBoolean;

    // Firebase
    private static final String FIREBASE_URL = "https://save-the-world-c2795.firebaseio.com/";
    private Firebase firebaseReference;

    private Database(){
        // Database is created in a connected state
        firebaseReference = new Firebase(FIREBASE_URL);
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
    */
}