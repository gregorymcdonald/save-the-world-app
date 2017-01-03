package com.twilio;

import com.savetheworld.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

import java.io.BufferedReader;

public class TwilioServlet extends HttpServlet {

    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Database db = Database.getInstance();
        db.pull();

        // Read from request
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String requestBody = buffer.toString();
        String responseBody = "Message received with body: \"" + requestBody + "\"";

        Message message = new Message.Builder()
                .body(new Body(responseBody))
                .build();

        MessagingResponse twiml = new MessagingResponse.Builder()
                .message(message)
                .build();

        response.setContentType("application/xml");

        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }
}