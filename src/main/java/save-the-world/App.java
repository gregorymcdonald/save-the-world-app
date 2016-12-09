package com.savetheworld;

// /**
//  * Hello world!
//  *
//  */
// public class App 
// {
//     public static void main( String[] args )
//     {
//         System.out.println( "Hello World!" );
//     }
// }

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class App {
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC7bf672b3288a8dbd2535daa1e128ec5f";
  public static final String AUTH_TOKEN = "b7600d19ed3d064daf64dea221ed6477";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber("+15163533154"),
        new PhoneNumber("+15162102347"), 
        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

    System.out.println(message.getSid());
  }
}