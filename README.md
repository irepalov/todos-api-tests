# ToDo Automation Test Project

## 📜 Project Overview

This is an automation testing project for the **ToDo API** and WebSockets. The project is built with modern testing frameworks and libraries and supports various advanced testing techniques. It is designed to test functionality and real-time WebSocket updates for the ToDo application.

### Key features:
- Automated testing for **API endpoints** (`POST /todos`, `GET /todos`, `PUT /todos`, `DELETE /todos`).
- Real-time **WebSocket** message validation.
- Implementation of **JUnit 5 Parametrized Tests** for data-driven testing.
- Flexible **Maven profiles** to support different environments.
- Modular structure for easy code maintenance and extensibility.

---

## 📦 Technologies and Libraries Used

The project is built using the following technologies and libraries:

- **Java 8**: Core programming language.
- **JUnit 5**: Testing framework for unit and integration tests.
- **TestNG**: Supplementary testing framework for parameterized and data-driven tests.
- **Rest-Assured**: Simplifies API testing.
- **OkHttp**: WebSocket client for real-time testing.
- **Allure**: For generating detailed test reports.
- **Selenide**: UI testing (optional, but included in dependencies).
- **Owner**: For environment-specific configurations.
- **Logback**: Logging framework integrated with SLF4J.

---

## 🏗️ Project Structure

The project follows a modular structure for easy navigation and scalability:

- `src/main/java/`:
    - **api**: Contains WebSocket and API helper methods.
    - **Data**: Shared classes like `ToDoJson` and `Stash`.
    - **Helpers**: Utility classes like `TestHelpers`.
    - **stepdefs**: Step definitions for test scenarios.
- `src/test/java/`:
    - **testcases**: Contains test cases for API and WebSocket testing.

---

## 🚀 Features and Capabilities

### 1. **API Testing**
- CRUD operations for ToDo entities:
    - `POST /todos`: Create new tasks.
    - `GET /todos`: Retrieve all tasks.
    - `PUT /todos`: Update tasks.
    - `DELETE /todos`: Delete tasks.
- Validation of API responses and status codes.

### 2. **WebSocket Testing**
- Establishing WebSocket connections.
- Listening for real-time messages.
- Validating incoming messages based on expected IDs and data.

### 3. **Data-Driven Tests**
- Use of **JUnit 5 Parametrized Tests** with data providers.
- Dynamic generation of test data using Java Streams.

### 4. **Profiles for Environment-Specific Configurations**
- Supports Maven profiles for running tests in different environments (e.g., `localhost`, `staging`, `production`).
- Example profile configuration:
  ```xml
  <profiles>
      <profile>
          <id>localhost</id>
          <properties>
              <environment>localhost</environment>
              <base.url>http://localhost:8080</base.url>
              <websockets.url>ws://localhost:8080/ws</websockets.url>
          </properties>
      </profile>
  </profiles>
---

## ⚙️ Prerequisites

- **Java 8** or higher installed.
- **Maven** installed and configured.
- IDE such as **IntelliJ IDEA** or **Eclipse**.

---


## 🔧 Setting Up and Running Tests

### 1. Clone the Repository
```bash
git clone https://github.com/your-repo/todo-test-project.git
cd todo-test-project

### 2. Configure Maven Profiles

To run the tests for a specific environment, you can configure Maven profiles either through **VM options** or directly via the IntelliJ IDEA interface.

#### Option 1: Using VM Options
1. Open **IntelliJ IDEA**.
2. Navigate to **Run > Edit Configurations**.
3. Click on the desired run configuration or create a new one.
4. In the **VM options** field, specify the Maven profile and any additional properties: -Plocalhost -Dwebsockets.url=ws://localhost:8080/ws -Dbase.url=http://localhost:8080

#### Option 2: Using IntelliJ IDEA Maven Panel
1. Open **IntelliJ IDEA**.
2. Locate the **Maven** panel on the right-hand side. If the panel is not visible, go to **View > Tool Windows > Maven**.
3. Click the **Profiles** tab in the Maven panel.
4. You will see a list of profiles defined in the `pom.xml` (e.g., `localhost`, `staging`).
5. Check the box next to the desired profile to activate it. Once activated, the profile will be applied to all Maven commands executed within the IDE.

#### Adding Profiles in `pom.xml`
To define additional environments or modify existing ones, update the `<profiles>` section in the `pom.xml` file. For example, to add a `staging` profile:
```xml
<profile>
 <id>staging</id>
 <properties>
     <environment>staging</environment>
     <base.url>https://staging.todo.com</base.url>
     <websockets.url>wss://staging.todo.com/ws</websockets.url>
 </properties>
</profile>
---

### 3. Run Tests
mvn clean test