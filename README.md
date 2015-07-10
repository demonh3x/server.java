# server.java

## Description

A connections server with an HTTP module

## Features

##### HTTP
* Serves files in a directory

## Dependencies

##### Execution
* Java 1.7

##### Testing
* JUnit
* Hamcrest

## Setup

##### Compile from commandline
Pending. (Currently delegated to IDE)

##### Run tests from commandline
Pending. (Currently delegated to IDE)

##### Run
The HttpServer requires two arguments: 

* `-p` for the port.
* `-d` for the directory to serve files from.

example: `java -jar httpserver.jar -p 9999 -d ~`
