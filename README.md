# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.


Use Case 8: Booking History & Reporting
-
**Goal:** 

Introduce historical tracking of confirmed bookings to provide operational visibility, enable audits, and support reporting, reinforcing a persistence-oriented mindset without introducing external storage.

**Actor:**

Admin – reviews booking history and reports for operational purposes.

Booking History – maintains a record of confirmed reservations.

Booking Report Service – generates summaries and reports from stored booking data.

**Flow:**

A booking is successfully confirmed.

The confirmed reservation is added to booking history.

Booking history maintains records in insertion order.

Admin requests booking information or reports.

Stored reservations are retrieved and displayed as required.

**Key Concepts Used**

Operational Visibility - Real systems require visibility into past transactions.

Historical data allows administrators to understand system usage and behavior.

List Data Structure - A List<Reservation> is used to store confirmed bookings. Lists preserve insertion order, making them suitable for chronological records.

Ordered Storage - Bookings are stored in the order they are confirmed. This naturally reflects real-world timelines and supports sequential reporting.

Historical Tracking - Once stored, bookings form an audit trail. This enables later review, analysis, and verification of system actions.

Reporting Readiness - Storing structured booking data prepares the system for reporting. Reports can be generated without reprocessing live booking flows.

Separation of Data Storage and Reporting - Booking history focuses on storing data. Reporting logic is delegated to a separate service, reducing coupling.

Persistence Mindset (Without Storage Medium) - Although data is stored in memory, the system treats history as long-lived information. This prepares learners conceptually for file-based or database persistence in later stages.

**Key Requirements**

Store each confirmed reservation in booking history.

Maintain bookings in the order they are confirmed.

Allow retrieval of stored reservations for review.

Generate summary reports from booking history.

Ensure reporting does not modify stored booking data.

**Key Benefits**

Complete and traceable booking audit trail

Simplified reporting and administrative analysis

Improved support for customer issue resolution

**Drawbacks of Previous Use Case**

Use Case 7 extended booking functionality but did not retain historical data.
Without booking history, completed transactions could not be reviewed or analyzed.
Cost Aggregation - Service costs are calculated separately and combined when needed. This keeps pricing logic modular and easier to extend.
