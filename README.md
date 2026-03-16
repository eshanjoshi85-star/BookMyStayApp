# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.

Use Case 6: Reservation Confirmation & Room Allocation
-
**Goal:**

Confirm booking requests by assigning rooms safely while ensuring inventory consistency and preventing double-booking under all circumstances.

**Actor:**

Booking Service – processes queued booking requests and performs room allocation.

Inventory Service – maintains and updates room availability state.

**Flow:**

Booking request is dequeued from the request queue.

The system checks availability for the requested room type.

A unique room ID is generated and assigned.

The room ID is recorded to prevent reuse.

Inventory count is decremented immediately.

Reservation is confirmed.

**Key Concepts Used:**

Problem of Double Booking - Without controlled allocation, the same room may be assigned to multiple guests. This results in room ID collisions and inconsistent system state.

Set Data Structure - A Set<String> is used to store allocated room IDs. Sets enforce uniqueness by design, preventing duplicate room assignments.

Uniqueness Enforcement - By checking against an existing set of room IDs, the system guarantees that no room is assigned more than once. This removes the need for manual duplicate checks.

Mapping Room Types to Assigned Rooms - A HashMap<String, Set<String>> maps each room type to its allocated room IDs. This allows grouped tracking and simplifies validation and reporting.

Atomic Logical Operations - Room allocation is treated as a single logical unit. Assignment and inventory update occur together to avoid partial or inconsistent state.

Inventory Synchronization - Inventory is updated immediately after allocation. This ensures that availability reflects the current system state at all times.

**Key Requirements:**

Retrieve booking requests from the queue in FIFO order.

Generate and assign a unique room ID for each confirmed reservation.

Prevent reuse of room IDs across all allocations.

Update inventory immediately after successful allocation.

Ensure allocation logic maintains system consistency.

**Key Benefits:**

Guaranteed uniqueness of room assignments

Immediate synchronization between booking and inventory

Elimination of double-booking scenarios

**Drawbacks of Previous Use Case:**

Use Case 5 handled request ordering but did not confirm bookings.

Without allocation and uniqueness enforcement, queued requests could still result in conflicting assignments.