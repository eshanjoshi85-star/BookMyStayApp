# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.


Use Case 7: Add-On Service Selection
-

**Goal:** 

Extend the booking model to support optional services, demonstrating how real-world business features can be added without modifying core booking or allocation logic.

**Actor:**

Guest – selects optional services for an existing reservation.

Add-On Service – represents an individual optional offering.

Add-On Service Manager – manages the association between reservations and selected services.

**Flow:**

Guest selects one or more add-on services.

Selected services are added to a list.

The list of services is mapped to the corresponding reservation ID.

Additional cost for the reservation is calculated.

Core booking and inventory state remain unchanged.

**Key Concepts Used**

Business Extensibility - Real-world bookings often include additional offerings beyond the primary product. The system must support new features without disrupting existing logic.

One-to-Many Relationship - A single reservation can have multiple associated services. This relationship is modeled using a map from reservation ID to a list of services.

Map and List Combination - Map<String, List<Service>> allows efficient lookup of services for a reservation. Lists preserve insertion order and allow multiple services to be attached.

Composition over Inheritance - Services are composed with reservations rather than inherited. This avoids rigid class hierarchies and supports flexible feature growth.

Separation of Core and Optional Features - Add-on services are managed independently of room allocation and inventory. This prevents optional features from complicating critical booking workflows.

Cost Aggregation - Service costs are calculated separately and combined when needed. This keeps pricing logic modular and easier to extend.

**Key Requirements**

Allow multiple services to be attached to a single reservation.

Store selected services using a reservation-to-services mapping.

Calculate total additional cost for selected services.

Ensure add-on logic does not modify booking or inventory state.

Support easy addition of new service types.

**Key Benefits**

Flexible attachment of optional services to reservations

Clean mapping between bookings and value-added features

Easy expansion of services without core booking changes

**Drawbacks of Previous Use Case**

Use Case 6 confirmed room allocation but treated bookings as static entities.

Without add-on support, the system could not model common real-world booking enhancements.