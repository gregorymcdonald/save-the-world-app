package com.savetheworld;

import java.lang.StringBuilder;

public class MessageBuilder {
	
	private static final String FIRST_NAME_INSERTION = "$(firstname)";
	private static final String LAST_NAME_INSERTION = "$(lastname)";

	public static String buildMessage(String message, ContactTableViewModel model) {

		StringBuilder b = new StringBuilder();

		if(message.contains(FIRST_NAME_INSERTION)){
			message = message.replace(FIRST_NAME_INSERTION, model.firstName.get());
		}

		if(message.contains(LAST_NAME_INSERTION)) {
			message = message.replace(LAST_NAME_INSERTION, model.lastName.get());
		}

		return message;
	}
}