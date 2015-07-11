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

##### Build tool
* Gradle

## Run tests
`gradle test`

## Compile and run
`gradle jar` will compile the *.jar file at `build/libs/http-server-0.1.0.jar`.

The HttpServer requires two arguments: 

* `-p` for the port.
* `-d` for the directory to serve files from.

example: `java -jar build/libs/http-server-0.1.0.jar -p 9999 -d ~`
