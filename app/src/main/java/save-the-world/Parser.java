package com.savetheworld;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVRecord;

public class Parser {


	private static final String LOCAL_FILE = "IFC_LIST.csv";
	private static final String HOME_DIRECTORY = System.getProperty("user.home"); 

	//Map Key Constants for First and Last Name entries 
    private static final String FIRST_NAME = "first-name";
    private static final String LAST_NAME = "last-name";
    private static final String NAME_COL = "Name";

	public static ArrayList <Contact> parseFile() {

		ArrayList <Contact> contacts = new ArrayList<Contact>();

		try {
			Reader in = new FileReader(LOCAL_FILE);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			for (CSVRecord record : records) {

				Map <String, String> entry = record.toMap();
				splitName(record.get(NAME_COL), entry);

				Contact c = new Contact(entry);
				contacts.add(c);
			}
		} catch (IOException e) {
			System.out.println("CSV FILE NOT FOUND");
		}

		return contacts;
	}

	private static void splitName(String fullName, Map <String, String> entry) {
	   int start = fullName.indexOf(' ');
	   int end = fullName.lastIndexOf(' ');

	   String firstName = "";
	   String middleName = "";
	   String lastName = "";

	   if (start >= 0) {
	       firstName = fullName.substring(0, start);
	       if (end > start)
	           middleName = fullName.substring(start + 1, end);
	       lastName = fullName.substring(end + 1, fullName.length());
	   }

	   entry.put(FIRST_NAME, firstName);
	   entry.put(LAST_NAME, lastName);
	}

}