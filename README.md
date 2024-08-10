# OPERIS packages structure

We chose the Hexagonal Architecture for structuring our microservices because it promotes a clear separation of concerns, enhances modularity and testability, and allows for greater flexibility in adapting to changing technologies and requirements. Here are the key principles of this architecture:

## Hexagonal Architecture Overview

Hexagonal Architecture, also known as the Ports and Adapters pattern, is a software design pattern that aims to create maintainable, testable, and flexible applications. It achieves this by isolating the core business logic from external concerns, promoting a clean separation of concerns.

### Key Concepts

#### 1. Core Domain Logic

- The core of the application contains the business logic, which is independent of external systems. This core, often referred to as the "domain," is surrounded by adapters.

#### 2. Ports

- Ports define interfaces that describe how the application interacts with the outside world. There are two types of ports:
    - **Inbound Ports**: Define how external actors (e.g., users, other systems) can interact with the application's core.
    - **Outbound Ports**: Define how the application communicates with external systems (e.g., databases, message queues).

#### 3.  Adapters

- Adapters implement the interfaces defined by the ports, allowing the application to interact with external systems.
    - **Primary Adapters**: Handle requests coming into the application (e.g., REST controllers, CLI commands).
    - **Secondary Adapters**: Handle outgoing interactions with external systems (e.g., database repositories, external API clients).

> Each business microservices will be divided into two modules: 
>
>- the first module, suffixed with '-core' (e.g., user-profile-core), will encapsulate the domain logic, 
>- the second module, suffixed with '-service' (e.g., user-profile-service), will implement the adapters and configure the Spring Boot application.



