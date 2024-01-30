# takeaway_challenge

* Implementation
    * Java 17
    * Spring Boot 3.0.4
    * Liquibase 4.17.12
    * Postgres 13.1
    * Swagger
        * http://localhost:8080/swagger-ui/index.html#/
        * http://localhost:8080/v3/api-docs/TakeawayChallenge
* To run it
    * Run with docker
        * Go to the directory /takeaway_challenge
            * Run the command `docker compose up`
            * To clean up the app `docker compose up --build`
* Comment:
    * hobbies.csv is used to add data in the hobbies table, in future APIs could be created to maintain it.

## Architecture used

Hexagonal Architecture was used.
Hexagonal architecture, also known as Ports and Adapters architecture or the Onion architecture, is a software design
pattern that promotes loosely coupled and highly maintainable systems.
Here are some reasons why using the hexagonal architecture in this challenge could be beneficial:

1. **Separation of concerns**.
2. **Flexibility and modifiability**.
3. **Testability**.
4. **Domain-driven design (DDD) alignment**.
5. **Scalability**.

## Bonus implemented

- Liquibase was used as database schema migration tool
- Documentation is an important aspect of software development, please provide some
  sort of API documentation
    - Was implemented Swagger, all APIs are documented
- Add authorization to access create, update and delete employee endpoints
    - Basic Authorization was implemented
    - To access those APIs with authorization use the user `admin` password `password`
    - Other APIs use the user `user` password `password`
- Run the database you’ve chosen to store the employee's data in a Docker container
    - App, postgres and rabbitMQ was containerized
    - To simplify the setup host name used was `host.docker.internal`
    - As an improvement, network bridge could be used
- A service publishes an event that affects the Employee service. Such an event is
  published in a queue/topic named employee-events with payload as example provided
  below. Please implement the code that consumes that event removing the affected
  employee record
    - Please check the package messageconsumer
    - DeadLetter queue also was implemented

## Future Improvements

- Implement Security using Keycloak with OAuth
- Implement pagination when return all employees
- Implement OpenAPI Generator to create automatic the client classes to access the APIs
- Implement a resilient system to handle database failures or external systems in case it be implemented:
- Retry mechanism: When a database failure occurs, the system should attempt to reconnect and recover the
  connection. Implementing a retry mechanism will allow the system to automatically retry connecting to the
  database, reducing the impact on the overall process. However, keep in mind that this approach might slow down
  the process during database recovery.
  - Silent implementation: In case the retry mechanism fails to establish a connection, the system should log the
  error and return an empty list instead of throwing an exception. This approach allows the system to continue
  functioning and enables the use of metrics to track and analyze the occurrence of database failures.
  - HTTP error handling: If a database failure occurs, the frontend should be able to handle the exception
  gracefully. This can be achieved by implementing appropriate error handling mechanisms in the frontend code.
  By treating the exception and providing informative error messages to the users, you can ensure a smoother
  user experience even when the database is unavailable.
  - Implement caching

## Running the project

1. Run with docker
    - Go to the directory /global-bank-coding-challenge-ralbergaria
    - Run the command `docker compose up`
2. Run without docker
    - Go to the directory /spring-boot/demo
        - Run `./gradlew build`
        - Run `./gradlew run`
3. Swagger
    - Authorize using the api-key bank123456
    - http://localhost:9000/webjars/swagger-ui/index.html
    - http://localhost:9000/swagger-doc/v3/api-doc