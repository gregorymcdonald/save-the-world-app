# **Save the World App**
**Authors**: Adam Estrin, Angelo Cioffi, Greg McDonald, Kieran Vanderslice, Rob Misasi

## How to Run App
Ensure you are in the app directory, then  
Enter the following command(s) in order:
1. 'mvn package`   
2. 'java -jar target/save-the-world-1.0-SNAPSHOT.jar'  

**Note**: When the main class name if modified, the maven-jar-plugin in the POM must be updated to reflect this name change.  

## Servlet Prerequisites  
1. install 'heroku-cli'   
2. install 'heroku-cli-deploy'  

## Servlet Deploy Instructions
Ensure you are in the servlet directory, then  
Enter the following command(s) in order:
1. 'mvn package'  
2. 'heroku war:deploy target/save-the-world-servlet-1.0-SNAPSHOT.war --app <app_name>'  
    a. <app_name> is given when 'heroku create' is run, or can be specified  
    b. Suggested <app_name> is 'save-the-world-servlet'
    c. <app_name> will vary based on developer  

## Servlet Run/Scale Instructions
Enter the following command(s) in order (you do not need to be in the servlet directory):
1. 'heroku ps:scale web=<number_of_dynos> --app <app_name>'  
    a. <app_name> is given when 'heroku create' is run, or can be specified  
    b. Suggested <app_name> is 'save-the-world-servlet'
    c. <app_name> will vary based on developer  
    d. <number_of_dynos> is the number of instances you want to run  
    	i. Inputting a 0 will turn off the servlet  

## How to Clean
Ensure you are in the app or servlet directory, then  
Enter the following command(s) in order:
1. `mvn clean`


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

