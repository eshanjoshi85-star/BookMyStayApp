# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.



Use Case 3: Centralized Room Inventory Management
-

**Goal:** 

Introduce centralized inventory management by replacing scattered availability variables with a single, consistent data structure, demonstrating how HashMap solves real-world state management problems.

**Actor:** 

RoomInventory – responsible for managing and exposing room availability across the system.

**Flow:**

The system initializes the inventory component.

Room types are registered with their available counts.

Availability is stored and retrieved from a centralized HashMap.

Updates to availability are performed through controlled methods.

The current inventory state is displayed when requested.

**Key Concepts Used**

Problem of Scattered State - In the previous use case, availability was stored in separate variables. This leads to inconsistent updates, duplication, and poor scalability as the system grows.

HashMap - HashMap<String, Integer> is used to map room types to available room counts. This allows fast access, updates, and lookups based on a logical key.

O(1) Lookup - HashMap provides average constant-time complexity for get and put operations. This makes it suitable for systems that require frequent availability checks.

Single Source of Truth - All availability data is maintained in one centralized structure. This eliminates discrepancies caused by multiple variables representing the same state.

Encapsulation of Inventory Logic - Inventory-related operations are encapsulated within a dedicated class. Other parts of the system interact with inventory only through exposed methods, reducing coupling.

Separation of Concerns - Inventory manages how many rooms are available, not what a room is. Room characteristics such as price and size remain part of the Room domain model.

Scalability - Adding a new room type requires only inserting a new entry into the map. No changes are required in application logic, demonstrating scalable design.

**Key Requirements**

Initialize room availability using a constructor.

Store room availability using a HashMap.

Provide methods to retrieve current availability.

Support controlled updates to room availability.

Ensure inventory state remains consistent across operations.

**Key Benefits**

Single source of truth for room availability

Constant-time inventory access and updates

Improved scalability when introducing new room types

Drawbacks of Previous Use Case

Availability was managed using independent variables.

This approach does not scale and increases the risk of inconsistent system state as complexity grows.
