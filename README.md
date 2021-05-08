Presidential Suite Booking Application
1) This a JAVA 8 application using SPRING_BOOT framework and H2 database for storage
2) To run it with Eclipse IDE, find the class Application in left side package explorer "com.example"
	then right click and select Run as -> Spring Boot App.
	Check the console at the bottom for something like "Started Application in 15.458 seconds"
	Open a browser window and enter http://localhost:8080 
3) To run the test, right click on classes under package "com.example.test"
	then right click and select Run as -> Junit Test
	Check the console and the tab JUnit
4) Application is performing the following validations:
	- Number of Guests must be numeric
	- Check-in and Check-out dates must be valid if entered
	- Each booking allows up to three people for up to three days.
    	- Each booking can be made at least one day and at most 30 days before arrival.
5) Test coverage is 100% for the RESTful API calls (see ReservationRESTfulTest.java
	There is also a JAVA class SmokeTest.java used by JMeter to test performance
6) Application has also an UI using the Thymeleaf http://www.thymeleaf.org template engine
	Using the UI you can perform CRUD operations and check all required validations.
7) UI is using bootstrap CSS and JQuery (date picker module).
8) An extra validation is performed to check the Presidential Suite availability
9) An error handling class is implemented for reservation not found. 
	Most input errors are handled at front-end layer with JAVASCRIPT
10) Concurrency (simultaneous requests) are managed by the Tomcat servlet engine.
	Max threads are set on application.properties -> server.tomcat.max-threads=100
	If the application is deployed as a micro service we can implement load balancing using DOCKER and KUBERNETES
11) A JMeter plugin in on the pom.xml file to allow JMeter testing.	
