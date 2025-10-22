# String Analyzer API - Backend Task Stage 1

A high-performance RESTful API service that analyzes strings and stores their computed properties using in-memory storage. Built with Spring Boot 3.2.0 and Java 17, this API provides comprehensive string analysis including SHA-256 hashing, palindrome detection, character frequency analysis, and advanced filtering capabilities.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
    - [Endpoint 1: Create/Analyze String](#1-createanalyze-string)
    - [Endpoint 2: Get Specific String](#2-get-specific-string)
    - [Endpoint 3: Get All Strings with Filtering](#3-get-all-strings-with-filtering)
    - [Endpoint 4: Natural Language Filtering](#4-natural-language-filtering)
    - [Endpoint 5: Delete String](#5-delete-string)
- [Testing Guide](#testing-guide)
- [Deployment](#deployment)
- [Project Structure](#-project-structure)
- [Error Handling](#-error-handling)
- [Author](#-author)

---

## Overview

The String Analyzer API is a production-ready backend service designed for the Backend Wizards Stage 1 challenge. It demonstrates:

- **RESTful API Design**: Following REST principles with proper HTTP methods and status codes
- **String Analysis Algorithms**: SHA-256 hashing, palindrome detection, character frequency mapping
- **Advanced Filtering**: Multi-criteria filtering with natural language query support
- **In-Memory Storage**: High-performance data storage using ConcurrentHashMap
- **Robust Error Handling**: Comprehensive exception handling with meaningful error responses
- **Type Safety**: Strict validation ensuring values are strings, not numbers or other types

**Live Demo:** `http://stringanalyzer-production-2c36.up.railway.app`

---

## Features

### Core Functionality
- ✅ **String Analysis**
    - SHA-256 hash generation for unique identification
    - Case-insensitive palindrome detection
    - Character frequency mapping
    - Unique character counting
    - Word count calculation
    - String length analysis

### Advanced Capabilities
- ✅ **Multi-Criteria Filtering**
    - Filter by palindrome status
    - Filter by minimum/maximum length
    - Filter by exact word count
    - Filter by character containment

- ✅ **Natural Language Queries**
    - "all single word palindromic strings"
    - "strings longer than X characters"
    - "strings containing the letter a-z"
    - "palindromic strings that contain the first vowel"

### Technical Features
- ✅ **In-Memory Storage**: Lightning-fast operations with ConcurrentHashMap
- ✅ **Thread-Safe**: Handles concurrent requests safely
- ✅ **Type Validation**: Custom deserializer ensures strict string types
- ✅ **RESTful Design**: Proper HTTP methods and status codes
- ✅ **Comprehensive Error Handling**: Consistent error response format
- ✅ **Input Validation**: Request body and parameter validation

---

## Tech Stack

| Technology | Version  | Purpose |
|-----------|----------|---------|
| **Java** | 21       | Programming language |
| **Spring Boot** | 3.5.6    | Application framework |
| **Maven** | 3.6+     | Dependency management & build tool |
| **Lombok** | 1.18.30  | Reduce boilerplate code |
| **Jackson** | 2.15.0   | JSON serialization/deserialization |
| **Jakarta Validation** | 3.0.2    | Input validation |
| **ConcurrentHashMap** | Built-in | Thread-safe in-memory storage |

**No Database Required!** Pure in-memory storage for maximum simplicity and performance.

---

## Architecture

### Layer Architecture

```
┌─────────────────────────────────────────────┐
│           Controller Layer                   │
│  (HTTP Endpoints, Request/Response Handling) │
├─────────────────────────────────────────────┤
│            Service Layer                     │
│  (Business Logic, String Analysis Algorithms)│
├─────────────────────────────────────────────┤
│           Storage Layer                      │
│  (In-Memory Data Access with ConcurrentHashMap)│
├─────────────────────────────────────────────┤
│            Model Layer                       │
│        (Data Models & DTOs)                  │
└─────────────────────────────────────────────┘
```
---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version
   # Should output: java version "17" or higher
   ```
   Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)

2. **Apache Maven 3.6+**
   ```bash
   mvn -version
   # Should output: Apache Maven 3.6.x or higher
   ```
   Download: [Maven](https://maven.apache.org/download.cgi)

3. **Git**
   ```bash
   git --version
   ```
   Download: [Git](https://git-scm.com/downloads)

4. **(Optional) IDE**
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Recommended)
    - [Eclipse](https://www.eclipse.org/downloads/)
    - [VS Code](https://code.visualstudio.com/) with Java extensions

---

### Installation

#### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/string-analyzer-api.git
cd string-analyzer-api
```

#### Step 2: Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required dependencies
- Compile the source code
- Run tests (if available)
- Create the executable JAR file

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 15.234 s
```

---

### Running the Application

#### Method 1: Using Maven (Development)

```bash
mvn spring-boot:run
```

The application will start and be available at: `http://localhost:8080`

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2025-10-21 16:00:00.123  INFO 12345 --- [main] c.b.s.StringAnalyzerApplication          : Started StringAnalyzerApplication in 2.456 seconds
```

#### Method 2: Using IDE

1. Open the project in your IDE
2. Navigate to `src/main/java/com/backendwizards/stringanalyzer/StringAnalyzerApplication.java`
3. Right-click and select **Run 'StringAnalyzerApplication'**

---

### Verify Installation

Test that the API is running:

```bash
curl http://localhost:8080/strings
```

---

## API Documentation

### Base URL

```
Local: http://localhost:8080
Production: https://your-deployed-url.com
```

---

### 1. Create/Analyze String

Analyzes a string and stores its computed properties.

**Endpoint:** `POST /strings`

**Request Body:**
```json
{
  "value": "string to analyze"
}
```

**Success Response (201 Created):**
```json
{
  "id": "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",
  "value": "racecar",
  "properties": {
    "length": 7,
    "is_palindrome": true,
    "unique_characters": 4,
    "word_count": 1,
    "sha256_hash": "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",
    "character_frequency_map": {
      "r": 2,
      "a": 2,
      "c": 2,
      "e": 1
    }
  },
  "created_at": "2025-10-21T16:00:00"
}
```

**Error Responses:**

| Status Code | Description | Example |
|------------|-------------|---------|
| **409 Conflict** | String already exists | `{"status": 409, "error": "Conflict", "message": "String already exists in the system"}` |
| **400 Bad Request** | Missing value field | `{"status": 400, "error": "Bad Request", "message": "Invalid request body or missing 'value' field"}` |
| **422 Unprocessable Entity** | Invalid data type (not string) | `{"status": 422, "error": "Unprocessable Entity", "message": "Invalid data type for 'value' (must be string)"}` |

---

### 2. Get Specific String

Retrieves a previously analyzed string by its value.

**Endpoint:** `GET /strings/{string_value}`

**Path Parameters:**
- `string_value` (string, required): The original string value to retrieve

**Success Response (200 OK):**
```json
{
  "id": "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",
  "value": "racecar",
  "properties": {
    "length": 7,
    "is_palindrome": true,
    "unique_characters": 4,
    "word_count": 1,
    "sha256_hash": "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",
    "character_frequency_map": {
      "r": 2,
      "a": 2,
      "c": 2,
      "e": 1
    }
  },
  "created_at": "2025-10-21T16:00:00"
}
```

**Error Response:**

| Status Code | Description |
|------------|-------------|
| **404 Not Found** | String does not exist in the system |

---

### 3. Get All Strings with Filtering

Retrieves all stored strings with optional filters.

**Endpoint:** `GET /strings`

**Query Parameters (all optional):**

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `is_palindrome` | boolean | Filter by palindrome status | `true` or `false` |
| `min_length` | integer | Minimum string length | `5` |
| `max_length` | integer | Maximum string length | `20` |
| `word_count` | integer | Exact word count | `1` |
| `contains_character` | string | Single character to search for | `a` |

**Success Response (200 OK):**
```json
{
  "data": [
    {
      "id": "abc123...",
      "value": "racecar",
      "properties": {
        "length": 7,
        "is_palindrome": true,
        "unique_characters": 4,
        "word_count": 1,
        "sha256_hash": "abc123...",
        "character_frequency_map": {
          "r": 2,
          "a": 2,
          "c": 2,
          "e": 1
        }
      },
      "created_at": "2025-10-21T16:00:00"
    },
    {
      "id": "def456...",
      "value": "level",
      "properties": {
        "length": 5,
        "is_palindrome": true,
        "unique_characters": 3,
        "word_count": 1,
        "sha256_hash": "def456...",
        "character_frequency_map": {
          "l": 2,
          "e": 2,
          "v": 1
        }
      },
      "created_at": "2025-10-21T16:01:00"
    }
  ],
  "count": 2,
  "filters_applied": {
    "is_palindrome": true,
    "min_length": 5
  }
}
```

**Error Response:**

| Status Code | Description |
|------------|-------------|
| **400 Bad Request** | Invalid query parameter values or types |

---

### 4. Natural Language Filtering

Filters strings using natural language queries.

**Endpoint:** `GET /strings/filter-by-natural-language`

**Query Parameters:**
- `query` (string, required): Natural language query

**Supported Query Patterns:**

| Query | Interpreted As |
|-------|---------------|
| "all single word palindromic strings" | `word_count=1, is_palindrome=true` |
| "strings longer than 10 characters" | `min_length=11` |
| "strings shorter than 5 characters" | `max_length=4` |
| "strings containing the letter z" | `contains_character=z` |
| "palindromic strings that contain the first vowel" | `is_palindrome=true, contains_character=a` |
| "two word strings" | `word_count=2` |

**Success Response (200 OK):**
```json
{
  "data": [
    {
      "id": "abc123...",
      "value": "radar",
      "properties": {
        "length": 5,
        "is_palindrome": true,
        "unique_characters": 3,
        "word_count": 1,
        "sha256_hash": "abc123...",
        "character_frequency_map": {
          "r": 2,
          "a": 2,
          "d": 1
        }
      },
      "created_at": "2025-10-21T16:00:00"
    }
  ],
  "count": 1,
  "interpreted_query": {
    "original": "all single word palindromic strings",
    "parsed_filters": {
      "word_count": 1,
      "is_palindrome": true
    }
  }
}
```

**Error Responses:**

| Status Code | Description |
|------------|-------------|
| **400 Bad Request** | Unable to parse natural language query |

---

### 5. Delete String

Deletes a stored string by its value.

**Endpoint:** `DELETE /strings/{string_value}`

**Path Parameters:**
- `string_value` (string, required): The original string value to delete

**Success Response:** `204 No Content` (empty body)

**Error Response:**

| Status Code | Description |
|------------|-------------|
| **404 Not Found** | String does not exist in the system |

---

## Testing Guide

### Testing with Postman

#### Import Collection

Create a Postman collection with these requests:

**1. Create String**
- Method: `POST`
- URL: `{{baseUrl}}/strings`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
  ```json
  {
    "value": "racecar"
  }
  ```

**2. Get All Strings**
- Method: `GET`
- URL: `{{baseUrl}}/strings`

**3. Get Specific String**
- Method: `GET`
- URL: `{{baseUrl}}/strings/racecar`

**4. Filter Strings**
- Method: `GET`
- URL: `{{baseUrl}}/strings?is_palindrome=true&min_length=5`

**5. Natural Language Query**
- Method: `GET`
- URL: `{{baseUrl}}/strings/filter-by-natural-language?query=all single word palindromic strings`

**6. Delete String**
- Method: `DELETE`
- URL: `{{baseUrl}}/strings/racecar`

**Environment Variable:**
```json
{
  "baseUrl": "http://localhost:8080"
}
```
---

## Deployment

### Deploy to Railway

Railway provides easy deployment with automatic HTTPS and custom domains.

**Steps:**

1. **Create Railway Account**
    - Visit [railway.app](https://railway.app)
    - Sign up with GitHub

2. **Deploy from GitHub**
   ```
   New Project → Deploy from GitHub → Select Repository
   ```

3. **Configure Build**
    - Railway auto-detects Spring Boot
    - Build command: `mvn clean package`
    - Start command: `java -jar target/string-analyzer-0.0.1-SNAPSHOT.jar`

4. **Set Environment Variables (Optional)**
   ```
   SERVER_PORT=8080
   SPRING_PROFILES_ACTIVE=production
   ```

5. **Deploy**
    - Push to GitHub
    - Railway automatically builds and deploys
    - Get your URL: `https://your-app.railway.app`

**Cost:** Free tier available (500 hours/month)

---

## 📁 Project Structure

```
string-analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/backendwizards/stringanalyzer/
│   │   │   ├── StringAnalyzerApplication.java           # Main application class
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── StringAnalyzerController.java        # REST API endpoints
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── StringAnalyzerService.java           # Business logic & analysis
│   │   │   │   └── NaturalLanguageProcessor.java       # NLP query parser
│   │   │   │
│   │   │   ├── storage/
│   │   │   │   └── InMemoryStringStorage.java           # Data access layer
│   │   │   │
│   │   │   ├── model/
│   │   │   │   └── AnalyzedString.java                  # Domain model
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── StringRequest.java                   # POST request DTO
│   │   │   │   ├── StringResponse.java                  # Single string response
│   │   │   │   ├── StringProperties.java                # Analyzed properties
│   │   │   │   ├── StringListResponse.java              # Multiple strings response
│   │   │   │   ├── NaturalLanguageResponse.java         # NLP response
│   │   │   │   ├── InterpretedQuery.java                # Query interpretation
│   │   │   │   └── StrictStringDeserializer.java        # Type validation
│   │   │   │
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java          # Centralized error handling
│   │   │       ├── StringAlreadyExistsException.java    # 409 exception
│   │   │       ├── StringNotFoundException.java         # 404 exception
│   │   │       └── InvalidQueryException.java           # 400 exception
│   │   │
│   │   └── resources/
│   │       └── application.properties                    # Configuration
│   │
│   └── test/
│       └── java/com/backendwizards/stringanalyzer/
│           └── StringAnalyzerApplicationTests.java       # Unit tests
│
├── pom.xml                                               # Maven dependencies
├── README.md                                             # This file
├── .gitignore                                            # Git ignore rules
└── Dockerfile                                            # Docker configuration
```

---

## ❗ Error Handling

### Error Response Format

All errors return a consistent JSON format:

```json
{
  "timestamp": "2025-10-21T16:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "String does not exist in the system"
}
```

### HTTP Status Codes

| Code | Status | When Used | Example |
|------|--------|-----------|---------|
| **200** | OK | Successful GET request | Retrieved string successfully |
| **201** | Created | String created | New string analyzed and stored |
| **204** | No Content | Successful deletion | String deleted (empty body) |
| **400** | Bad Request | Invalid request format | Missing field, invalid query param |
| **404** | Not Found | Resource not found | String doesn't exist |
| **409** | Conflict | Duplicate resource | String already exists |
| **422** | Unprocessable Entity | Invalid data type | Value is number, not string |
| **500** | Internal Server Error | Unexpected error | Server crashed |


## 👤 Author

**Name:** AYOBAMI ADEPOJU  
**Email:** ayobamiadepoju263@gmail.com  
**Stack:** Java / Spring Boot  
**GitHub:** [@ayobamiadepoju](https://github.com/ayobamiadepoju)
---