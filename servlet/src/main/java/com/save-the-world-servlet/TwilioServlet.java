package com.twilio;

import com.savetheworld.Database;
import com.savetheworld.ConversationRecord;
import com.savetheworld.MessageRecord;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

import java.io.BufferedReader;

import java.util.Date;

public class TwilioServlet extends HttpServlet {

    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Read from request
        StringBuilder requestBuffer = new StringBuilder();
        BufferedReader requestReader = request.getReader();
        String line;
        while ((line = requestReader.readLine()) != null) {
            requestBuffer.append(line);
        }
        String requestParameters = requestBuffer.toString();
        String messageBody = decodeHTMLEncodedText(getParameter(requestParameters, "Body"));
        String messageTo = decodeHTMLEncodedText(getParameter(requestParameters, "To"));
        String messageFrom = decodeHTMLEncodedText(getParameter(requestParameters, "From"));

        String responseBody = "Message: \"" + messageBody + "\"";
        responseBody += "\nTo:" + messageTo;
        responseBody += "\nFrom:" + messageFrom;
        responseBody += "\n\nFull parameters:\n" + requestParameters;

        // Save the message to the database
        MessageRecord messageRecord = new MessageRecord(messageTo, messageFrom, messageBody);
        Database db = Database.getInstance();
        db.pull();
        ConversationRecord conv = db.getConversation(messageTo, messageFrom);
        if(conv == null) {
            conv = new ConversationRecord(messageTo, messageFrom, null);
        }
        conv.addMessage(messageRecord);
        db.saveConversation(conv);
        db.push();

        // Send a response SMS message
        writeMessageResponse(responseBody, response);
    }

    private void writeMessageResponse(String responseBody, HttpServletResponse response) throws IOException {
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

    /**
     * @param parameterBlock All parameters, in block form.
     * @param parameter The parameter to extract. 
     * @return The value of the parameter, null if not present in the block.
     */
    public String getParameter(String parameterBlock, String parameter){
        String[] parameters = parameterBlock.split("&");
        for(int i = 0; i < parameters.length; ++i){
            String currentParameter = parameters[i];
            int splitIndex = currentParameter.indexOf("=");
            String currentParameterName = currentParameter.substring(0, splitIndex);
            if(currentParameterName.equals(parameter)){
                return currentParameter.substring(splitIndex + 1);
            }
        }
        return null;
    }

    /**
     * @param encodedText The text to decode.
     * @return An unencoded string.
     */
    public String decodeHTMLEncodedText(String encodedText){
        String unencodedText = "";
        for(int i = 0; i < encodedText.length(); ++i){
            char currentChar = encodedText.charAt(i);
            if (currentChar == '%') {
                String encodedChar = encodedText.substring(i + 1, i + 3);
                char unencodedChar = (char) Integer.parseInt(encodedChar, 16);
                unencodedText += unencodedChar;
                i += 2;
            } else if (currentChar == '+') {
                unencodedText += " ";
            } else {
                unencodedText += currentChar;
            }
        }
        return unencodedText;
    }
}