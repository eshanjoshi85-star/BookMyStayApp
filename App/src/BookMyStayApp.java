/**
 * BookMyStayApp
 *
 * Use Case 1: Entry point of the Hotel Booking System.
 * Use Case 2: Demonstrates application startup and basic room modeling.
 *
 * @author Eshan Pankaj Joshi
 * @version 2.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Use Case 1: Application Entry & Welcome Message
        String appName = "Hotel Booking System";
        System.out.println("Welcome to " + appName);
        System.out.println("System initialized successfully.");
        System.out.println();

        // Use Case 2: Basic Room Types & Static Availability

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailable = 10;
        int doubleRoomAvailable = 5;
        int suiteRoomAvailable = 2;

        // Display room details
        System.out.println("Available Room Types:");
        System.out.println("----------------------");

        System.out.println(singleRoom.getRoomType());
        System.out.println("Beds: " + singleRoom.getBeds());
        System.out.println("Size: " + singleRoom.getSize() + " sq ft");
        System.out.println("Price: $" + singleRoom.getPrice());
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println();

        System.out.println(doubleRoom.getRoomType());
        System.out.println("Beds: " + doubleRoom.getBeds());
        System.out.println("Size: " + doubleRoom.getSize() + " sq ft");
        System.out.println("Price: $" + doubleRoom.getPrice());
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println();

        System.out.println(suiteRoom.getRoomType());
        System.out.println("Beds: " + suiteRoom.getBeds());
        System.out.println("Size: " + suiteRoom.getSize() + " sq ft");
        System.out.println("Price: $" + suiteRoom.getPrice());
        System.out.println("Available: " + suiteRoomAvailable);
    }
}

/**
 * Abstract representation of a hotel room.
 */
abstract class Room {

    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();
}

/**
 * Single Room implementation
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 80.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

/**
 * Double Room implementation
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 120.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

/**
 * Suite Room implementation
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 250.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}