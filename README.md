# OneSuiteBookingApplication
One Suite Booking Application
1) This a JAVA application using the SPRING_BOOT framework and the H2 database for storage
2) To run it with Eclipse IDE, find the class Application in left side package explorer "com.example"
	then right click and select Run as -> Spring Boot App.
	Check the console at the bottom for something like "Started Application in 15.458 seconds"
	Open a browser window and enter http://localhost:8080 
3) To run the test, right click on classes under package "com.example.test"
	then right click and select Run as -> Junit Test
	Check the console and the tab JUnit
4) Test coverage is done for a Smoke Test and to test the CRUD repository
5) Application has an UI using the Thymeleaf http://www.thymeleaf.org template engine
	Using the UI you can perform CRUD operations and check all required validations.
6) UI is using bootstrap CSS and JQuery (date picker module).
7) An extra validation is performed to check the Presidential Suite availability
9) An error handling class is implemented for reservation not found. 
	Most input errors are handled at front-end layer with JAVASCRIPT
10) Concurrency (simultaneous requests) are managed by the Tomcat servlet engine.
	Max threads are set on application.properties -> server.tomcat.max-threads=100
	If the application is deployed as a micro service we can implement load balancing using DOCKER and KUBERNETES
