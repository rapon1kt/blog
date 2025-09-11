## Spring-Boot Blog

This blog project is structured using a software architecture that adopts principles such as Domain-Driven Design (DDD) and Clean Architecture, which is reflected in the organization of the code across different layers and domains.

---

#### Architectural Principles

The project is divided into the following layers:

- **`Blog/domain`**: This is the core layer and represents the business domain. It is independent of any technology or framework and contains business logic, such as models (Account, Comment, Post, etc.), repositories (interfaces), and ports.

* **`Blog/application`**: This layer contains application-specific business logic (usecase and service) and validators. It orchestrates the execution of operations using the interfaces defined in the domain layer.
* **`Blog/infrastructure`**: This layer handles the technical aspects, such as data persistence (repository implementations) and security configurations.

- **`Blog/presentation`**: The presentation layer, which encompasses `controllers` and DTOs (Data Transfer Objects), is the project's external interface, responsible for receiving HTTP requests, converting data, and returning responses.

---

The project structure, with a clear separation of responsibilities, facilitates maintenance, scalability, and the development of new features, ensuring that the core business logic remains isolated from the infrastructure and presentation technologies.

### Author

##### [rapon1kt](https://github.com/rapon1kt)
