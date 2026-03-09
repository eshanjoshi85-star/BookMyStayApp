/**
 * HotelBookingApplication
 *
 * This class represents the entry point of the Hotel Booking System.
 * It demonstrates how a Java application starts execution using the
 * main() method and produces console output.
 *
 * The program prints a welcome message including the application
 * name and version, then terminates.
 *
 * @author Eshan Pankaj Joshi
 * @version 1.0
 * Use Case 1: Application Entry & Welcome Message
 */

public class BookMyStayApp {

    /**
     * Main method - the entry point of the Java application.
     * The JVM invokes this method when the program is executed.
     *
     * @param args Command-line arguments passed during execution
     */
    public static void main(String[] args) {

        // Application Name
        String appName = "Hotel Booking System";

        // Display welcome message
        System.out.println("Welcome to " + appName);
        System.out.println("System initialized successfully.");
    }
}