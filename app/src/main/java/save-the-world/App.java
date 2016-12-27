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

import java.util.ArrayList;

public class App {
  // Developer phone number(s), for testing. XXX REMOVE LATER
  private static final String ADAM_PHONE_NUMBER = "+15163533154";
  private static final String GREG_PHONE_NUMBER = "+14692379287";
  private static final String KIERAN_PHONE_NUMBER = "+17136208645";

  public static void main(String[] args) {

    //Messenger messenger = Messenger.getInstance();
    //messenger.sendSMS(GREG_PHONE_NUMBER, "Testing 1, 2, 3.");
    ArrayList <Contact> contacts = Parser.parseFile();
    System.out.println(contacts.get(0).getFirstName());
  }
}