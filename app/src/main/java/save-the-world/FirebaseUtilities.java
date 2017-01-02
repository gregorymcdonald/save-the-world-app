package com.savetheworld;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class FirebaseUtilities {
    // The Firebase URL of this project
    public static final String FIREBASE_URL = "https://save-the-world-c2795.firebaseio.com";

    // Allowed characters in a Firebase ID
    private static final String FIREBASE_ID_CHARACTER_SET = "+-_0123456789abdcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Length of Firebase ID
    private static final int FIREBASE_ID_LENGTH = 19;

    /**
     * Reads data from the remote Firebase database.
     * @param urlPath The URL path to read data from, must include the protocol (HTTPS) and end with ".json"
     * @return A String containing the result of the request, null if request failed.
     */
    public static String readDataFromFirebase(String urlPath){
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

    /**
     * Write data to the remote Firebase database. The data must already have a value in the remote for the write to succeed.
     * @param urlPath The URL path to write data to, must include the protocol (HTTPS) and end with ".json"
     * @return A String containing the result of the request, null if request failed.
     */
    public static String writeDataToFirebase(String urlPath, String content){
        if(!urlPath.startsWith("https")){
            System.err.println("Writes to Firebase must use HTTPS.");
            return null;
        } else if (!urlPath.endsWith(".json")){
            System.err.println("Writes to Firebase must target json.");
            return null;
        }

        // Attempt the read
        try{
            URL url = new URL(urlPath);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(content);
            out.flush();
            out.close();

            // Read result
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
        } finally {

        }
        return null;
    }
}