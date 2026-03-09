import java.util.HashMap;

/**
 * BookMyStayApp
 *
 * Use Case 1: Entry point of the Hotel Booking System.
 * Use Case 2: Basic Room Types using abstraction and inheritance.
 * Use Case 3: Centralized Room Inventory using HashMap.
 *
 * @author Eshan Pankaj Joshi
 * @version 3.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // =============================
        // Use Case 1: Application Entry
        // =============================
        String appName = "Hotel Booking System";
        System.out.println("Welcome to " + appName);
        System.out.println("System initialized successfully.");
        System.out.println();

        // ===================================
        // Use Case 2: Room Domain Modeling
        // ===================================

        // Create room objects (polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // ===================================
        // Use Case 3: Centralized Inventory
        // ===================================

        RoomInventory inventory = new RoomInventory();

        inventory.setAvailability(singleRoom.getRoomType(), 10);
        inventory.setAvailability(doubleRoom.getRoomType(), 5);
        inventory.setAvailability(suiteRoom.getRoomType(), 2);

        // Display room details with availability
        System.out.println("Available Room Types:");
        System.out.println("----------------------");

        displayRoom(singleRoom, inventory);
        displayRoom(doubleRoom, inventory);
        displayRoom(suiteRoom, inventory);
    }

    // Helper method
    public static void displayRoom(Room room, RoomInventory inventory) {

        System.out.println(room.getRoomType());
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sq ft");
        System.out.println("Price: $" + room.getPrice());
        System.out.println("Available: " + inventory.getAvailability(room.getRoomType()));
        System.out.println();
    }
}

/**
 * Use Case 3: Centralized Room Inventory
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void setAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }
}

/**
 * Use Case 2: Abstract Room Model
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
 * Use Case 2: Single Room
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
 * Use Case 2: Double Room
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
 * Use Case 2: Suite Room
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