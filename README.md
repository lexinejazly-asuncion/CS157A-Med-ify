# Project Overview
Med-ify is a full-stack, three-tier hospital management system designed to streamline key clinical and administrative tasks through an integrated, data-driven approach. In many healthcare settings, information such as patient records, doctor assignments, appointments, and prescriptions is spread across multiple platforms or handled manually, creating inefficiencies and increasing the risk of error. 

Med-ify addresses these challenges by centralizing all essential data within a PostgreSQL database and providing a web-based interface for tasks like patient registration, appointment scheduling, and prescription management. Built with Java Database Connectivity (JDBC) and grounded in a clear three-tier architecture, the application demonstrates how modern healthcare systems depend on reliable data storage, retrieval, and update operations to support accurate, efficient, and secure hospital workflows.


---
# Project Setup and Deployment

Set up the **Med-ify** project structure and configure the local **Tomcat Server** for deployment.

---
## Project Dependencies / Tech Stack
* Java
* PostgreSQL
* Apache Tomcat 10.1.49
https://tomcat.apache.org/download-10.cgi
* Apache Maven 3.9.11
* IDE: IntelliJ

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

### 4. Database Connection
Before running the application, update the database connection:  

First connect to a PostgreSQL server, then create a database

Go to the database connection file: src/main/java/medify/DBConnection/DatabaseConnection.java    
Update the url, user, and pass variables with your local database information    

Save the file

### 5. Database Initilization
Before running the application, create tables and insert data entries:

Go to the database initialization file: **src/main/java/medify/DBConnection/DatabaseInitializer.java**
Run Current File to populate the database

---
## Run Application
Run the application with Tomcat Server 10.1.49 configuration
