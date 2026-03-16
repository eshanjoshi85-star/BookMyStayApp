# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.

Use Case 2:Basic Room Types & Static Availability
-
**Goal:** 

Introduce object modeling through inheritance and abstraction before introducing data structures, allowing students to focus on domain design rather than optimization.

**Actor:** 

User – runs the application to view predefined room types and their availability.

**Flow:**

User runs the application.

Room objects representing different room types are created.

Availability for each room type is stored using simple variables.

Room details and availability information are printed to the console.

Application terminates.

**Key Concepts Used**

Abstract Class - An abstract class is used to represent a generalized concept that should not be instantiated directly. The Room class defines common attributes and behavior shared by all room types while enforcing a consistent structure.

Inheritance - Concrete room classes (SingleRoom, DoubleRoom, SuiteRoom) extend the abstract Room class. This allows shared properties to be reused while enabling specialization for each room type.

Polymorphism - Room objects are referenced using the Room type, enabling uniform handling of different room implementations. This prepares the system for future extensibility without changing client code.

Encapsulation - Room attributes such as number of beds, size, and price are encapsulated within the Room class. This ensures that room characteristics are controlled and modified only through defined behavior.

Static Availability Representation - Room availability is stored using simple variables rather than data structures. This intentionally highlights the limitations of hardcoded and scattered state management.

Separation of Domain and State - Room objects represent what a room is, while availability variables represent current system state. This distinction becomes critical when inventory management is introduced later.

**Key Requirements**

Define an abstract Room class with common attributes.

Create concrete room classes for Single, Double, and Suite rooms.

Initialize room objects in the application entry point.

Store room availability using individual variables.

Display room details and availability to the console.

**Key Benefits**

Clear introduction to object-oriented domain modeling

Demonstrates inheritance and abstraction in a real-world context

Establishes a strong foundation for later inventory refactoring

**Drawbacks of Previous Use Case**

Use Case 1 focused only on application startup and execution flow.

No domain modeling or business concepts were introduced, limiting system realism.
