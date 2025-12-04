# Project Setup and Deployment

Set up the **Med-ify** project structure and configure the local **Tomcat Server** for deployment.

---
## Project Dependencies
* Java
* PostgreSQL
* Apache Tomcat 10.1.49 
https://tomcat.apache.org/download-10.cgi
* Apache Maven 3.9.11

---
## Initial Project Setup

### 1. Clone the repository
git clone https://github.com/lexinejazly-asuncion/CS157A-Med-ify.git  
cd Med-ify

### 2. Configure Project Structure
Go to File --> Project Structure  
Under Project Settings --> Project:
* Set Project SDK to a version that supports SDK 23
* Set Project language level to SDK default

### 3. Build Project
Go to Build --> Build Project

---
## Configure Tomcat Server and Database Connection
### 1. Add Configuration
Go to Current File --> Edit Configurations  
Click the + sign to Add New Configuration  
Select Tomcat Server --> Local

### 2. Configure Application Server
Select Apache Tomcat installation directory

### 3. Configure Deployment 
Go to Deployment tab  
Click the + sign   
Select Artifact --> Med-ify:war exploded  
Change the Application context field to: /Med_ify  
Click Apply, then OK

### 4. Database Connection Update
Before running the application, update the database connection:  

First connect to a PostgreSQL server, then create a database

Go to the database connection file: src/main/java/medify/DBConnection/DatabaseConnection.java  
Update the url, user, and pass variables with your local database information  
Save the file

### 4. Database Initilization
Before running the application, create tables and insert data entries

Move 'db' folder into resources folder: src/test/resources

Go to the database initialization file: src/main/java/medify/DBConnection/DatabaseInitializer.java
Run Current File to populate the database

---
## Run Application
Run the application with Tomcat Server 10.1.49 configuration
