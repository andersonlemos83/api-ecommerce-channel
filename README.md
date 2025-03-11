[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-channel&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-channel)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-channel&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-channel)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-channel&metric=coverage)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-channel)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-channel&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-channel)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-channel&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-channel)

This is the English version. For the Portuguese version, click [here](./README.pt.md).

# About the api-ecommerce-channel project

This is a fictional project exclusively created for studying and validating new technologies.

## Technologies and Concepts

The main concepts and technologies I want to validate include:

- **Architecture**: Clean Architecture and Hexagonal Architecture
- **Framework**: Spring Boot 3.4.3 (Webflux, Netty, Data Mongodb Reactive, and Mail)
- **Acceptance Testing**: Cucumber, WireMock, Greenmail, and Testcontainers
- **Database**: MongoDB
- **Caching and Fault Tolerance**: Redis and Resilience4j

## Bibliographic Inspiration

📖 *Clean Architecture: A Craftsman's Guide to Software Structure and Design* – Robert C. Martin  
📖 *Clean Agile: Back to Basics* – Robert C. Martin  
📖 *Clean Code: A Handbook of Agile Software Craftsmanship* – Robert C. Martin  
📖 *Growing Object-Oriented Software, Guided by Tests* – Steve Freeman  
📖 *Refactoring: Improving the Design of Existing Code* – Martin Fowler

🎓 *[Java Spring Microservices Specialist Training](https://e-certificado.com/login/visualizar?c=2343053A8150F7A015193380)* – Decoder Project

## Main Technologies and Tools Used

- **Language**: Java 21 LTS, Gherkin (for BDD with Cucumber)
- **Framework**: Spring Boot 3.4.3 (Webflux, Netty, Data Mongodb Reactive, AMQP, Mail, Data Redis, Validation, Actuator, among others)
- **Messaging and Asynchronous Processing**: RabbitMQ
- **Unit and Acceptance Testing**: JUnit 5, Mockito, Cucumber, WireMock, Greenmail, Testcontainers, and Instancio
- **Database**: MongoDB
- **Caching and Fault Tolerance**: Redis and Resilience4j
- **API Documentation**: Swagger/OpenAPI
- **Service Communication**: OpenFeign
- **Containerization**: Docker
- **Logging and Monitoring**: Log4j2 and Spring Boot Actuator
- **Dependency Management**: Maven
- **Version Control**: Git
- **Continuous Integration (CI)**: GitHub Actions
- **Code Quality**: SonarQube

## Domain

In a fictional e-commerce platform with multiple sales channels, including a website, mobile app, physical store, and self-checkout kiosk, 
whenever a customer makes a purchase, the respective channel must invoice each order in the sales orchestrator system, 
record all transactions in the database, and finally send a copy of the invoice to the customer's email. 
This process completes the order invoicing workflow.

Additionally, the customers will be able to check their purchases by providing the order number or specifying a specific period of time.

Finally, as an exercise, it is intended to simulate the interaction of multiple customers through a bot that will make random purchases.

## Features

## start-order-bot.feature
### Base Scenario - Start order bot with all valid data provided
**Given** that the number of random orders has been specified  
**When** the bot is started via the `/order/start-bot` endpoint  
**Then** a message should be published for each generated order number in the queue `order-generator-queue`  
**And** a response should be returned with all generated order numbers

## process-order-generation.feature
### Base Scenario - Process random order generation with all valid data provided
**Given** that a valid order number has been provided  
**And** that all address data is available from the `/findByZipCode` endpoint of the ViaCepClient service  
**When** the random order generation is processed via the `order-generator-queue` listener  
**Then** a message containing the generated order data should be published in the queue `order-queue`

## process-order.feature
### Base Scenario - Process order with all valid data provided
**Given** that all valid order data has been provided  
**When** the order is processed via the `order-queue` listener  
**Then** the system should authorize the order invoicing through the `/authorize-sale` endpoint of the EcommerceCheckoutClient service  
**And** it should register an order awaiting an invoice in the database

## process-order-callback.feature
### Base Scenario - Process a callback order with all valid data provided
**Given** that all valid callback order data has been provided  
**And** that there is an order awaiting an invoice in the database  
**When** the callback order is processed via the `sale-callback-queue` listener  
**Then** an order with an invoice should be registered in the database  
**And** an email containing the order invoice details should be sent to the customer

## find-order-by-order-number.feature
### Base Scenario - Find an existing order from the database by order number
**Given** that there are orders in the database  
**When** the order is searched via the `/order/{orderNumber}` endpoint  
**Then** a response should be returned with all the expected order details

## find-orders-by-period.feature
### Base Scenario - Find existing orders from the database by period of time
**Given** that a valid period of time has been provided  
**And** that there are orders in the database  
**When** the orders are searched via the `/order/paginated` endpoint  
**Then** a response should be returned with all orders from the specified period of time

## Feature Flow

<img src="script/diagrams/feature-start-order-bot.png" alt="Feature Start Order Bot" width="100%" height="100%">

[View in full screen](./script/diagrams/feature-start-order-bot.png)

<img src="script/diagrams/feature-find-order-by-order-number.png" alt="Feature Find Order By Order Number" width="100%" height="100%">

[View in full screen](./script/diagrams/feature-find-order-by-order-number.png)

<img src="script/diagrams/feature-find-orders-by-period.png" alt="Feature Find Orders By Period" width="100%" height="100%">

[View in full screen](./script/diagrams/feature-find-orders-by-period.png)

## Architecture

The Ecommerce Channel project was developed following the principles of Clean Architecture and Hexagonal Architecture, and it is structured as follows:

- **Core Module**: Responsible for centralizing business rules in their purest form, minimizing dependency on frameworks and external technologies as much as possible.
- **Infrastructure Module**: Responsible for integrating input and output information, using different technologies and frameworks to communicate with databases, APIs, and other systems.

<img src="./script/diagrams/architecture.png" alt="Architecture (Clean + Hexagonal)" width="70%" height="70%">

[View in full screen](./script/diagrams/architecture.png)

## Requirements

- Java JDK 21
- Maven 3.6.2 or higher
- Docker (Necessary for Testcontainer and to run the application locally)

## First Steps

- **Download all project dependencies**:
  ```
    mvn dependency:resolve -U
  ```
- **Build the project**:
  ```
    mvn -U -B clean install -Dmaven.test.skip=true
  ```
- **Build the project and run all tests**:
  ```
    mvn -U -B clean install
  ```

## About the Tests

To organize the tests according to their type and function, they were grouped into three main suites:

- **RunCucumberTest**: It contains all acceptance tests implemented with Cucumber and BDD. This suite has a slower execution, as it requires the initialization of the context and infrastructure.
- **UnitTests**: It contains all unit tests of the project. As it has no external dependencies, its execution is fast.
- **AllTests**: It groups all implemented tests, combining acceptance tests (RunCucumberTest) and unit tests (UnitTests).

## Getting Started with the Application

- **Wiremock**:
1. Start a Wiremock instance:
  ```
    docker-compose -f .\script\docker\wiremock.yml up -d
  ```

2. Test the Wiremock instance: 
  ```
    curl --location 'http://localhost:8443/authorize-sale' --header 'Content-Type: application/json' --data '{"anyData": 0}'
  ```

- **Redis**:
1. Start a Redis instance:
  ```
    docker-compose -f .\script\docker\redis.yml up -d
  ```

2. Test the Redis instance:
  ```
    1. docker exec -it redis /bin/bash
    2. redis-cli
    3. KEYS "*"
    4. exit
    5. exit
  ```

- **RabbitMQ**:
1. Start a RabbitMQ instance:
  ```
    docker-compose -f .\script\docker\rabbitmq.yml up -d
  ```

2. Access the RabbitMQ instance:
   [Access RabbitMQ Admin](http://localhost:15672/)

3. Log in to RabbitMQ Admin with guest:
  ```
    username: guest
    password: guest
  ```

4. Create a new user ecommerce-channel:
  ```
    1. Go to /Admin/User
    2. Fill in Username: ecommerce-channel, Password: ecommerce-channel and Tags: administrator
    3. Click "Add user" 
  ```

5. Create a new virtual host ecommerce-checkout:
  ```
    1. Go to /Admin/Virtual Hosts
    2. Fill in Name: ecommerce-checkout and Default Queue Type: Classic
    3. Click "Add virtual host" 
  ```

6. Add permissions for the ecommerce-channel user to the ecommerce-checkout virtual host:
  ```
    1. Go to /Admin/Virtual Hosts/ecommerce-checkout
    2. Fill in User: ecommerce-channel, Configure regexp: .*, Write regexp: .* and Read regexp: .*
    3. Click "Set permissions" 
  ```

- **MongoDB**:
1. Start a MongoDB instance:
  ```
    docker-compose -f .\script\docker\mongodb.yml up -d
  ```

2. Access the Mongo Express instance:
   [Access Mongo Express](http://localhost:8081/)

3. Log in to Mongo Express with express:
  ```
    username: express
    password: express
  ```

- **Mailhog**:
1. Start a Mailhog instance:
  ```
    docker-compose -f .\script\docker\mailhog.yml up -d
  ```

2. Access the Mailhog instance:
   [Access Mailhog](http://localhost:8025/)

- **Running the Application**:
1. Create and run a Spring Boot runner:
  ```
    Main Class: /infrastructure/src/main/java/br/com/alc/ecommerce/channel/infrastructure/EcommerceChannelInfrastructureApplication.java
    Profile: local (application-local.yml)
  ```

2. Access Swagger UI:
   [Access Swagger UI](http://localhost:8383/swagger-ui.html)

3. Import Postman Collection:
   [api-ecommerce-channel.postman_collection.json](./script/postman/api-ecommerce-channel.postman_collection.json)

4. Test application:
  ```
  1. Send a POST request to http://localhost:8383/order/start-bot (Use Swagger or Postman!);
  2. Send a GET request to http://localhost:8383/order/{order_number} with an order number returned from step 1 (Use Swagger or Postman!);
  3. Check if the response from step 2 has status=INVOICE_PENDING;
  4. Publish the message "sale-callback-queue-message.json" to the "sale-callback-queue" queue, adjusting the order number to the one returned in step 1 (Use RabbitMQ Admin!);
  5. Send a GET request to http://localhost:8383/order/paginated with a valid period (Use Swagger or Postman!);
  6. Check if the response from step 5 has status=INVOICED;
  7. Verify if the invoice was sent to Mailhog.
  ```
[sale-callback-queue-message.json](./script/rabbit/sale-callback-queue-message.json)

## That's all folks!

I hope you enjoyed it.