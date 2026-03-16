# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.

Use Case 4: Room Search & Availability Check
-

**Goal: **

Enable guests to view available rooms and their details without modifying system state, reinforcing safe data access and clear separation of responsibilities.

**Actor:**

Guest – initiates a search to view available room options.

Search Service – handles read-only access to inventory and room information.

**Flow:**

Guest initiates a room search request.

The system retrieves availability data from the inventory.

Room details and pricing are obtained from room objects.

Unavailable room types are filtered out.

Available room types and their details are displayed.

System state remains unchanged.

**Key Concepts Used**

Read-Only Access - Search operations are designed to read data without altering it. This prevents unintended side effects and ensures system stability.

Defensive Programming - The search logic performs checks to ensure only valid and available room types are displayed. This protects the system from incorrect assumptions and invalid data usage.

Separation of Concerns - Search functionality is isolated from inventory mutation and booking logic. This ensures that searching does not interfere with allocation or availability updates.

Inventory as State Holder - Inventory is accessed only to retrieve current availability counts. No updates are performed during search operations.

Domain Model Usage -  Room objects provide descriptive information such as pricing and amenities. This avoids duplicating room-related data in the inventory layer.

Validation Logic - Room types with zero availability are excluded from the search results. This ensures that guests see only actionable options.

**Key Requirements**

Retrieve room availability from the centralized inventory.

Display only room types with availability greater than zero.

Show room details and pricing using room domain objects.

Ensure inventory data is not modified during search operations.

Maintain a clear boundary between search logic and booking logic.

**Key Benefits**

Accurate availability visibility without state mutation

Reduced risk of accidental inventory corruption

Clear separation between read-only and write operations

**Drawbacks of Previous Use Case**

Use Case 3 introduced centralized inventory but did not differentiate between read and write access.

Without explicit separation, inventory could be accidentally modified during non-booking operations. state as complexity grows.
