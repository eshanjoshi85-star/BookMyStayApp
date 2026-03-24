# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.


Use Case 9: Error Handling & Validation
-

**Goal:** 

Strengthen system reliability by introducing structured validation and error handling, ensuring that invalid inputs and inconsistent states are detected and handled early.

**Actor:**

Guest – provides booking input that must be validated.

Invalid Booking Validator – validates input and system state before processing requests.

**Flow:**

Guest provides booking input.

System validates input values and system constraints.

If validation fails, an error is raised immediately.

A meaningful failure message is displayed.

The system prevents invalid state changes and continues running safely.

**Key Concepts Used**

Input Validation - Validation ensures that incoming data conforms to expected rules before processing. This prevents invalid or inconsistent data from entering the system.

Custom Exceptions - Domain-specific exceptions are used to represent invalid booking scenarios. Custom exceptions make error causes explicit and improve code readability.

Fail-Fast Design - The system detects errors as early as possible and stops further processing. This avoids cascading failures and simplifies debugging.

Guarding System State - Checks are performed before inventory updates or allocations. This ensures that critical state, such as availability counts, remains valid.

Graceful Failure Handling - Errors are communicated clearly without crashing the application. This improves system usability and maintainability.

Correctness over Happy Path - The system is designed to handle incorrect usage, not just ideal scenarios. This reflects real-world conditions where invalid input is common.

**Key Requirements**

Validate room types before processing bookings.

Prevent inventory from reaching invalid or negative values.

Throw and handle custom exceptions for invalid scenarios.

Display clear and informative failure messages.

Ensure the system remains stable after errors.

**Key Benefits**

Early detection of invalid system states

Reduced risk of silent data corruption

More stable and predictable application behavior

**Drawbacks of Previous Use Case**

Use Case 8 focused on storing and reporting booking data but assumed valid input.

Without validation, incorrect data could corrupt system state and reports.