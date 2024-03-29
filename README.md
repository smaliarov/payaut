Homework assignment
-----

A Dockerized SpringBoot Application that:
- runs on Java 11
- uses build tool Maven
- uses h2database as persistent storage

The application is a simple bookkeeping application that provides the following functionality through REST interface:
- create a new account
- debit on a specified account
- credit on a specified account
- transfer between two accounts
- balance of an account

-----
Create Docker image by running `mvn clean package docker:build`

Run it using `docker run -ti -p 8080:8080 smaliarov/payaut:latest`

Access REST services using swagger-ui: http://localhost:8080/swagger-ui.html

-----
Assumptions:
- every transaction has a unique id
- account may have negative amount of money