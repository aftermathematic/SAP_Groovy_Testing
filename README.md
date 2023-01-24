# SAP_Groovy_Testing in IntelliJ IDEA

This small IntelliJ program offers the possibility to test the Groovy scripts for your SAP iflows locally, with IntelliSense.

## How does it work?
- Place your input xml data in the /data/in/input.xml file
- If you have any properties or headers you want to send along with your input, 
place them in /data/in/properties.txt and /data/in/headers.txt as key/value pairs (e.g. HeaderName=Testvalue)
- Run the TestingProgram.groovy (not processData.groovy, that is the actual groovy script that goes into your SAP iFlow).
- The program will now run and place the output of your processData script both in the console, as well as in /data/out/output.xml. 
The console will also inform you of the headers and properties that were set.

### For optimal performance, please use following JDK's:
Groovy: 2.4.21
Java: 1.8.0_341
Camel: 2.24.2-sap-29

Enjoy!
