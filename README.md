# Important Days Tracker

A client-server application designed to help you track and manage important dates such as anniversaries, birthdays, and special occasions. This project consists of a Spring Boot backend server and a Java Swing GUI client.

## Project Structure

The project is divided into two main components:

- **`webappserver`**: A Spring Boot application that acts as the backend server, providing a REST API to manage the data.
- **`userinterface`**: A Java Swing-based graphical user interface that allows users to interact with the tracker.

## Features

- **List Important Days**: View all your saved important dates in one place.
- **Add New Dates**: Easily add new entries with specific details.
  - Dynamic fields based on the type of day (e.g., Location for Anniversaries, Name for Birthdays, Frequency for Occasions).
- **Remove Dates**: Delete entries you no longer need.
- **Filtering**:
  - View passed days in the current year.
  - View upcoming days in the current year.
  - Filter by category: Anniversary, Birthday, or Occasion.
- **Persistence**: Data is saved automatically to a JSON file, ensuring your important dates are preserved between sessions.

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Framework for the backend server.
- **Maven**: Dependency management and build tool.
- **Gson**: Library for JSON serialization and deserialization.
- **Java Swing**: Toolkit for the graphical user interface.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21 or higher.
- Maven.

### Running the Application

1.  **Start the Server**:
    Open a terminal, navigate to the `webappserver` directory, and run the Spring Boot application.
    **Important:** Keep this terminal window open and running. Do not close it.

    ```bash
    cd webappserver
    ./mvnw spring-boot:run
    ```

2.  **Run the Client**:
    Open a **new** terminal window (keep the server terminal running), navigate to the `userinterface` directory, and run the client.

    **Command Line (Windows):**

    ```powecd userinterfacershell


    # Compile (if needed)
    javac -d bin -cp "lib/*;src" src/cmpt213/assignment/importantdaystracker/client/MainTrackerApplication.java src/cmpt213/assignment/importantdaystracker/view/*.java src/cmpt213/assignment/importantdaystracker/model/*.java src/cmpt213/assignment/importantdaystracker/control/*.java

    # Run
    java -cp "bin;lib/*" cmpt213.assignment.importantdaystracker.client.MainTrackerApplication
    ```

    _(Ensure the server is running before starting the client)_

## Author

**Euluna Gotami**
CMPT 213 Assignment
