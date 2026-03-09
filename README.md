# BookMyStayApp

This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.

**Use Case 1:**
-
Application Entry & Welcome Message

**Goal:**

Establish a clear and predictable starting point for the Hotel Booking application by demonstrating how a Java program begins execution and produces console output.

**Actor:**

User – runs the application from the command line or IDE.

**Flow:**

User runs the application.

JVM invokes the main() method.

Application prints a welcome message along with the application name and version.

Application terminates.

**Key Concepts Used**

Class - Even the simplest Java application must be defined inside a class.
The class acts as a container for application behavior and marks the logical boundary of the program.

main() Method - The main method is the entry point of every standalone Java application. The JVM looks specifically for the method signature:
public static void main(String[] args).

static Keyword - The main() method is declared static so that it can be executed without creating an object of the class.
This allows the JVM to start execution directly.

Console Output - System.out.println() is used to send text output to the console.
This is the simplest way to observe program behavior during early development.

String Literals - Text enclosed in double quotes (e.g., "Hotel Booking System v1.0") is treated as a String literal, which is immutable and stored in the String pool.

Method Invocation - Calling println() on the out object demonstrates how methods are invoked on objects in Java, even in basic programs.

Application Flow - Execution proceeds top to bottom inside the main() method unless altered by control structures.
This use case reinforces linear execution flow.
JavaDoc Comments - JavaDoc comments are used to document the class and its purpose. They serve as the foundation for professional code documentation.
JavaDoc Annotations - Tags such as @author and @version provide metadata about the class and help maintain traceability as the system evolves.

**Key Requirements**

Create a Java class that represents the application entry point.
Implement the main() method using the correct signature.
Print a welcome message to the console.
Display the application name and version information.
Use JavaDoc comments to document the class and its intent.
Ensure the program executes and terminates without errors.

**Key Benefits**

Clear and predictable application startup behavior
Single, well-defined execution entry point
Improved debuggability during early development
Please refer to the code snapshot below to write your code