# Introduction
This repository contains example application (Tasker) developed using Dropwizard framework.
More on Dropwizard is available @ https://github.com/dropwizard 

Taske application is a simple task management app, where one can add a task and its expected date of completion. 
CRUD operation are allowed (check complete a task, delete a task, add new task)

#Overview

Application has 2 component, One back-end (tasker-server) providing api to persistant storage, front-end (tasker-ui) providing user interface.

#Tasker-Server

Included with this application is H2 DB for persisten and DB API module uses Hibernate to do operation on the database.

This database example is comprised of the following classes:

Task - is the Entity class providing access to DB storage. This is the mapping of Java classes to database tables with assisting of JPA annotations.

TaskDAO provides Data Access Object pattern with assisting of Hibernate. This handles the interation with Entity and also takes care of the CRUD operation and Transaction handling. All the SQL/DB operation is handled in TaskDAO that located in the Task class.

migrations.xml illustrates the usage of dropwizard-migrations which can create your database prior to running your application for the first time. (other db than H2)

The TaskResource is the REST resource which use the TaskDAO to retrieve data from the database. HealthCheckResource provides health check for loadbalance monitoring.

As with all the modules the app is wired up in the initialize function of the TaskApplication.

TaskAppConfiguration provides configuraion for database and other settings. If setting up or migrating to new database, this class to be updated.

#Tasker-Ui
Tasker UI is built using react and built and packaged by webpack.
Main UI class is in 
src/view/Tasker.tsx -> provides the UI rendering for the main task management.
src/api/service.tsx -> provides api interface to back-end server
src/api/task.tsk -> this is the model object for api 
src/view/layout -> provides the main layout of the application

# Docker Configs
Dockerfile provides the docker configurations for respective component.

# environment configuration

donenv is used to provide environment specific configuation, if the application is hosted in split server and UI component needs to access data from another server modify (.env) file and set REACT_APP_DB_URL= to appropriate server. if both frontend and backend hosted in same server keep only empty setting. REACT_APP_DB_URL= 

#Running The Application

Prerequisite for building and running the application:
JDK11 or Up
Maven v3 or Up
Node 16 or Up
Docker latest

To test the example application run the following commands.

git clone https://github.com/mhossain6/dropwizard-apps.git

# build server 

cd dropwizard-apps/tasker-server/
mvn clean install
docker build -t tasker_server --label tasker_server  . \
docker container run -p 8080:8080/tcp  tasker_server

# build ui
 cd ../tasker-ui/
 docker build -t tasker_ui --label tasker_ui  . \
 docker container run -p 3000:3000  tasker_ui
 
 # check the services are up
 docker container list
 
 Browse site:
 
 http://localhost:3000/
