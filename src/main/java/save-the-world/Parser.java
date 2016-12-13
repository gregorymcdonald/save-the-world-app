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

	public static final String LOCAL_FILE = "/Users/adamestrin/Downloads/IFC-List.csv";

	//CSV Column Name Constants
	public static final String NAME_COL = "Name";
	public static final String EMAIL_COL = "Email Address";
	public static final String HOME_TOWN_COL = "Home Town";
	public static final String HIGH_SCHOOL_COL = "High School";
	public static final String PHONE_NUMBER_COL = "Cellphone Number";
	public static final String HOUSING_COL = "Fall Housing Plans";
	public static final String STATUS_COL = "Recruitment Status";
	public static final String MAJOR_COL = "Expected Major";
	public static final String POSITIONS_COL = "Leadership Positions Held";
	public static final String LEGACY_COL = "Legacy INFORMATION";
	public static final String EID_COL = "Eid";

	public static void parseFile() {
		try {
			Reader in = new FileReader(LOCAL_FILE);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			for (CSVRecord record : records) {
				//Contact c = new Contact();
			    // String firstName = record.get(NAME_COL);
			    // String email = record.get(EMAIL_COL);
			    // String homeTown = record.get(HOME_TOWN_COL);
			    // String highSchool = record.get(HIGH_SCHOOL_COL);
			    // String phoneNumber = record.get(PHONE_NUMBER_COL);
			    // String housing = record.get(HOUSING_COL);
			    // String status = record.get(STATUS_COL);
			    // String major = record.get(MAJOR_COL);
			    // String positions = record.get(POSITIONS_COL);
			    // String legacy = record.get(LEGACY_COL);
			    // String eid = record.get(EID_COL);

			    Map <String, String> recordMapping = record.toMap();
			}
		} catch (IOException e) {
			System.out.println("CSV FILE NOT FOUND");
		}
		
	}

	private static void splitName(String fullName) {
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
	}

}