# onedrive


#Read Me

This is the Read me document to open One Drive technical assessment for Konica Minolta Business Solutions U.S.A., Inc.


Technical requirements to open One Drive technical assessment:

Software:
1.	Java
2.	IDE (Eclipse/IntelliJ)

Download Java using the below link:

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Please choose 32 bit/64 bit based on your Operating System.


Download Eclipse IDE using the below link:

https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon3

Please choose 32 bit/64 bit based on your Operating System.

Download and Install Maven from Apache website.

https://maven.apache.org/download.cgi


Project Description:

Steps to open the One Drive project in Eclipse:


1.	Clone the project from Github repository and import to your local workspace as existing maven project.
2.	Set the below environment variables to your installation directories.
       JAVA_HOME= where you installed Java
      MAVEN_HOME = where you installed maven.



Test scripts developed for One Drive project:

1.	Login
2.	Click on the documents
3.	Verify if there are any existing files
4.	If files are existing, Delete those files
5.	Upload a 0-kb file and validate the error message
6.	Upload a 4-kb file and validate the meta data
7.	Open info session and validate the meta data
8.	Click and open the file editor
9.	Modify the file and save it
10.	Download both versions and compare the dissimilarity
11.	Delete the file
12.	Close the browser
Frameworks used to develop One Drive project:

1.	Maven Project: Dependencies added in pom.xml which required for the project
2.	Design Pattern: Page object model
3.	Implementation: Page factory
4.	Framework: TestNG
5.	External jars: TestNG 6.14.3, Selenium-java 3.11.0, Selenium-server 3.11.0, Extent report 2.41.0
Used Extent report to generate a graphical look and feel of the report. 
After execution, automatically the report will pop up.