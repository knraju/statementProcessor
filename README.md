# Customer Statement Validation
   

### /statementProcessor
The code implementing the logic for validating the customer transactions. The following technologies are in use for coding and hosting of the application:

* Spring boot
* Maven
* Java
	
#### Build and Run
This is maven project and the configuration is in the `pom.xml` file.
		
* Following are prerequisites for building and deploying locally:		
    * Install Java 8 locally from [oracle.com](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
	* Install Maven; instructions can be found at [maven.apache.org](https://maven.apache.org/install.html)
		
* Once your local environment is properly configured, navigate to `statementProcessor`, then build the project by running the following command in your local terminal. 
			 
	    $mvn install
		  
* If the install is successful, the next step is to run the project execute one of the following commands:
			
		$mvn spring-boot:run
				or
		$java -jar target/statementProcessor-1.0-SNAPSHOT.jar
			
## API Documentation
All API endpoints information provided by Swagger.[http://localhost:8080/swagger-ui.html]
