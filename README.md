# LLM Chat Wrapper

This project is an AI model wrapper developed for the Bilkent University CTIS 221 - Object-Oriented Programming course. It provides a modular and extensible framework for integrating and managing various AI models, including support for chat, embeddings, and other capabilities.

## Features

-   **Modular Architecture**: Built with Spring Modulith for modularity and scalability.
-   **Database Integration**: Uses PostgreSQL for persistent storage.
-   **Caching**: Redis is used for caching frequently accessed data.
-   **Streaming Support**: Real-time streaming capabilities using WebFlux and test streaming with Hono.
-   **AI Model Integration**: Supports integration with various AI models and providers.

## Prerequisites

-   **Java 17** or higher
-   **Gradle** (for building the backend service)
-   **Docker** (for running PostgreSQL, Redis, and other services)
-   **Bun** (for the `stream-hono` service)

## Setup Instructions

### Backend (LLM Chat Service)

1. Clone the repository:

    ```sh
    git clone https://github.com/sezrr/ctis221-project.git
    cd ctis221-project/projects/llm-chat-service
    ```

2. Start the required services using Docker Compose:

    ```sh
    docker-compose -f compose.yaml up -d
    ```

3. Build and run the Spring Boot application:

    ```sh
    ./gradlew bootRun
    ```

4. Access the application at `http://localhost:8080`.

#### Stream Hono - OPTIONAL

1. Navigate to the `stream-hono` directory:

    ```sh
    cd ctis221-project/projects/stream-hono
    ```

2. Install dependencies:

    ```sh
    bun install
    ```

3. Start the development server:

    ```sh
    bun run dev
    ```

4. Open `http://localhost:3000` in your browser.

### Frontend (JavaFX GUI)

TODO: Add instructions for running the JavaFX GUI.

## API Documentation

The API documentation is available at:

-   `http://localhost:8080/swagger-ui.html` for the backend services.

## Project Structure

The project uses a monorepo style structure with the following projects:

-   **`llm-chat-service`**: Backend service for managing AI models and their configurations.
-   **`stream-hono`**: Backend service for real-time streaming and interaction.
-   **`modulo-ai`**: Core library for AI model integration and provider management.
-   **`javafx-gui`**: JavaFX GUI for interacting with the backend services.

## Technologies Used

-   **Spring Boot**: Backend framework.
-   **PostgreSQL**: Database.
-   **Redis**: Caching.
-   **Hono**: Real-time streaming.
-   **Bun**: JavaScript runtime for the frontend.
-   **Docker**: Containerization.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request.
