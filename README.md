# Introduction

The Note Taking API was developed to provide an example of an OAuth2 implementation with Dropwizard 

# Running The Application

To test the notes application run the following commands.

* To package the example run.

        mvn package

* To setup the MySQL database run.

        java -jar target/notes-api-1.0-SNAPSHOT.jar db migrate config.yml

* To run the server run.

        java -jar target/notes-api-1.0-SNAPSHOT.jar server config.yml
