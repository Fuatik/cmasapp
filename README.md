### System for management entity USER with REST API (without frontend) for CMAS

**The task was:**

Create a Web Application with a REST API to manage the entity User.

You must provide all the CRUD operations through the REST API.

Try to apply the HATEOAS concepts on it. As an optional, try to handle the exceptions thrown by your API with this library 
https://github.com/zalando/problem. CMAS adopt it as a standard.

Create a persistence layer to persist the users on a Postgres database.

Test your code at two different levels: Controllers and Services with BDD Mockito.
You don’t need to test the application in an exhaustive way, give some examples of each type.

Some considerations:

• Create a Spring Boot project (Latest Version) (Minimum Requirements: Java 8 or later)

• Keep your code clean, indented and well documented

• Structure your project according to Domain-Driven Design concepts.

• Maven is the in-house standard as dependency management. Keep only the dependencies
you really need in your pom file.

• Use YAML in your Spring configuration file(s).

• Use Lombok to avoid unnecessary boilerplate.

User Entity fields:

• First name

• Last name

• Email

• Age

• Active

-------------------------------------------------------------
- Stack: [JDK 21](http://jdk.java.net/17/), Spring Boot 3.x, Lombok, PostgreSQL
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------