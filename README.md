# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.


Use Case 11: Concurrent Booking Simulation (Thread Safety)
-
**Goal:**

Demonstrate how concurrent access to shared resources can lead to inconsistent system state and show how synchronization ensures correctness under multi-user conditions.

**Actor:**

Multiple Guests – submit booking requests concurrently.

Concurrent Booking Processor – processes booking requests in a multi-threaded environment.

**Flow:**

Multiple guests submit booking requests simultaneously.

Requests are added to a shared booking queue.

Threads retrieve requests using synchronized access.

Room allocation and inventory updates are performed inside critical sections.

The system completes allocations without conflicts or inconsistencies.

**Key Concepts Used**

Race Conditions - Race conditions occur when multiple threads access and modify shared data simultaneously. The final system state becomes dependent on execution timing rather than logic.

Thread Safety - Thread safety ensures that shared resources behave correctly when accessed by multiple threads. This is critical in systems handling concurrent user actions.

Shared Mutable State - The booking queue and inventory are shared across threads. Uncontrolled access to shared mutable data can corrupt system state.

Critical Sections - Critical sections are blocks of code that must be executed by only one thread at a time. Synchronization ensures exclusive access to these sections.

Synchronized Access - Synchronization mechanisms are used to protect shared resources. This prevents interleaving operations that could lead to double allocation.

Concurrency vs. Parallelism - Concurrency focuses on correctness when tasks overlap in time. This use case emphasizes correctness over performance optimization.

**Key Requirements**

Simulate multiple booking requests occurring at the same time.

Use shared data structures for booking requests and inventory.

Ensure inventory updates are performed in a thread-safe manner.

Prevent double allocation under concurrent execution.

Maintain consistent system state under load.

**Key Benefits**

Safe multi-user booking simulation

Correct room allocations under concurrent load

Foundation for building scalable, multi-user systems

**Drawbacks of Previous Use Case**

Earlier use cases assumed a single-threaded execution model. Such assumptions are unsafe in real production environments where concurrent access is common.