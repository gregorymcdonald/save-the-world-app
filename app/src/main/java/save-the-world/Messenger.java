package com.savetheworld;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Date;

public class Messenger {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC7bf672b3288a8dbd2535daa1e128ec5f";
    public static final String AUTH_TOKEN = "b7600d19ed3d064daf64dea221ed6477";

    // Subject to change.
    private static final String TWILIO_PHONE_NUMBER = "+15162102347";

    // Singleton instance
    private static Messenger singletonInstance = new Messenger();

    private static final boolean SEND_MESSAGES = false;

    private Messenger() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static Messenger getInstance() {
        return singletonInstance;
    }

    public MessageRecord sendSMS(String toPhoneNumber, String messageBody) {
        // Preliminary validity-check of arguments
        boolean validArguments = isValidPhoneNumber(toPhoneNumber) && isValidMessageBody(messageBody);

        // If send messages flag is set and arguments are valid
        if(SEND_MESSAGES && validArguments){
            try {
                // Send the message
                Message message = Message.creator(new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(TWILIO_PHONE_NUMBER), 
                    messageBody).create();
                System.out.println(message.getSid());
            } catch (Exception e) {
                // Something went wrong
                System.err.println("Error encountered sending message to " + toPhoneNumber + " with body \"" + messageBody + "\".");
                return null;
            }
        }

        return validArguments ? new MessageRecord(toPhoneNumber, TWILIO_PHONE_NUMBER, messageBody, new Date()) : null;
    }

    // Helper Method(s)

    public static boolean isValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null) return false;

        boolean sufficientLength = phoneNumber.length() > 9;
        return sufficientLength;
    }

    public static boolean isValidMessageBody(String messageBody){
        if(messageBody == null) return false;

        boolean sufficientLength = messageBody.length() > 0;
        return sufficientLength;
    }
}