Config Parser Service
Project Overview

This Spring Boot application parses a custom configuration file containing section-wise key-value pairs and exposes a REST API to retrieve configuration details dynamically.

The application reads the configuration file once, stores the data in memory, and provides fast, section-based access without using any database.

Features

Parses a plain text configuration file

Supports multiple sections and key-value pairs

Converts comma-separated values into lists

Stores configuration data in memory

Exposes REST API for section-wise retrieval

Follows Test Driven Development (TDD)

Technology Stack

Java 17

Spring Boot

Maven

JUnit 5

Mockito

Project Structure
assignment-one/
├── controller
├── service
├── model
├── src/test
├── pom.xml
└── README.md

API Endpoints
Load Configuration File

Endpoint

POST /config/load


Query Parameter

filePath = data/application-config.txt


Description
Loads the configuration file from the resources directory into memory.

Get Configuration by Section

Endpoint

GET /config


Query Parameter

section = Order Service


Response Example

{
  "broker": "https://orbroker.in",
  "topic": ["test_os_topic_1", "test_os_topic_2"]
}

How to Run

Clone the repository

Navigate to the assignment-one folder

Run the Spring Boot application

Call the load API to load configuration

Use the GET API to retrieve section data

Testing

Unit tests are written for service and controller layers

Tests verify file parsing, in-memory storage, and API behavior

Assumptions

Configuration file is correctly formatted

Data is stored only in memory

No persistence layer is used

Author

Animesh Karan – Config Parser Service

📄 README – Assignment 2
Time-Based Pricing Engine
Project Overview

This Spring Boot application implements a time-based pricing engine that reads pricing data from a TSV file and determines the correct price for a product based on a given time.

The solution uses in-memory storage to ensure fast lookups and avoids using any database.

Features

Reads pricing data from a TSV file

Stores pricing windows in memory

Supports multiple prices per SKU

Returns applicable price based on time

Handles invalid inputs gracefully

Follows Test Driven Development (TDD)

Technology Stack

Java 17

Spring Boot

Maven

JUnit 5

Mockito

Project Structure
assignment-two/
├── controller
├── service
├── model
├── src/test
├── pom.xml
└── README.md

API Endpoints
Load Pricing Data

Endpoint

POST /pricing/load


Query Parameter

filePath = data/pricing-data.tsv


Description
Loads the pricing TSV file from the resources directory into memory.

Get Price by SKU and Time

Endpoint

GET /pricing/price


Query Parameters

skuid = u00006541
time  = 10:03 (optional)


Response Examples

101
NOT SET

Business Rules

SKU is mandatory

Time is optional

If no active pricing window exists, response is NOT SET

Start time is inclusive and end time is exclusive

Latest pricing window is applied in case of overlap

How to Run

Clone the repository

Navigate to the assignment-two folder

Run the Spring Boot application

Call the load API to load the TSV file

Use the price API to query prices

Testing

Service layer tests validate pricing logic

Controller tests verify endpoint mappings and service invocation

All tests are written using JUnit 5 and MockMvc

Assumptions

Pricing data does not span across midnight

All data is stored in memory

Input TSV file format is valid

Author

Animesh Karan – Time-Based Pricing Engine
