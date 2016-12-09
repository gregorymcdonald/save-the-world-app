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



  private Messenger(){
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  }

  public static Messenger getInstance(){
    return singletonInstance;
  }

  public boolean sendSMS(String phoneNumber, String messageText){
    // Check validity of things

    Message message = Message.creator(new PhoneNumber(phoneNumber),
        new PhoneNumber(TWILIO_PHONE_NUMBER), 
        messageText).create();
    System.out.println(message.getSid());

    return true;
  }
}