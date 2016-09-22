// README.txt

Student ID: z5017483
Name: Dhruv Kaushik

Installation Guide

- How to compile, deploy the service code (step-by-step)
	Extract part1.zip
	Load into Eclipse using 'Import -> Exising Maven projects' 
	Run maven-install after clicking the root folder
	Deploy server and test :)
	http://localhost:8080/GNAFService/GNAFAddressingService?wsdl 

- How to setup SQLite (e.g., where to put the database file)
    The gnaf.db file should be placed in the resources folder 'Asi1/src/main/resources/'

- Specify if the assessor needs to prepare anything before running your solution? (e.g., do you assume there is a directory named 'XYZ' somewhere?)
	If there is a maven-install issue with sqlite-jdbc, go into my POM.xml file and replace the sqlite version with
    whatever version ran with your project.

- Specify if there is anything else you want to mention to help the assessor
    :)