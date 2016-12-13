# **Save the World App**
**Authors**: Adam Estrin, Greg McDonald, Kieran Vanderslice

## How to Run
Run command: `mvn package`   
Then run command: `java -jar target/save-the-world-1.0-SNAPSHOT.jar`  
**Note**: When the main class name if modified, the maven-jar-plugin in the POM must be updated to reflect this name change.

## How to Clean
Run command: `mvn clean`   


## Notes:

1. What: Desktop Application 
	a. UI

2. Purpose: 
	a. Text PNM automatically based on CSV input.
	b. Forward any responses to real phone numbers 
	c. Allow real person to continue conversation.

3. Features:

	a. Twilio automatic messaging 
	b. Parser for CSV
	c. Bridge from parsed CSV to auto message 
	d. Twilio SMS response, hits REST endpoint
		i. From PNM
		ii. To PNM (from actives)

