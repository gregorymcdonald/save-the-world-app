package com.savetheworld;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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

    public boolean sendSMS(String phoneNumber, String messageText) {
        // Preliminary validity-check of arguments
        boolean validArguments = isValidPhoneNumber(phoneNumber) && isValidMessage(messageText);

        // If send messages flag is set and arguments are valid
        if(SEND_MESSAGES && validArguments){
            try {
                // Send the message
                Message message = Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber(TWILIO_PHONE_NUMBER), 
                    messageText).create();
                System.out.println(message.getSid());

                return true;
            } catch (Exception e) {
                // Something went wrong
                return false;
            }
        }

        return validArguments;
    }

    // Helper Method(s)

    public static boolean isValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null) return false;

        boolean sufficientLength = phoneNumber.length() > 8;
        return sufficientLength;
    }

    public static boolean isValidMessage(String message){
        if(message == null) return false;

        boolean sufficientLength = message.length() > 0;
        return sufficientLength;
    }
}