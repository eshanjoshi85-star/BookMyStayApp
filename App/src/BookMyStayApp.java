import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * UseCase4RoomSearch
 *
 * Use Case 1: Entry point of the Hotel Booking System.
 * Use Case 2: Basic Room Types using abstraction and inheritance.
 * Use Case 3: Centralized Room Inventory using HashMap.
 * Use Case 4: Room Search & Availability Check (Read-Only Access).
 *
 * @author Eshan Pankaj Joshi
 * @version 4.0
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
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // ===================================
        // Use Case 3: Centralized Inventory
        // ===================================
        RoomInventory inventory = new RoomInventory();

        // Setting initial state
        inventory.setAvailability(singleRoom.getRoomType(), 10);
        inventory.setAvailability(doubleRoom.getRoomType(), 0); // Testing 0 availability
        inventory.setAvailability(suiteRoom.getRoomType(), 2);

        // ===================================
        // Use Case 4: Room Search Service
        // ===================================
        // Initializing the Search Service with the existing inventory
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Creating a list of rooms to search through
        List<Room> roomCatalog = new ArrayList<>();
        roomCatalog.add(singleRoom);
        roomCatalog.add(doubleRoom);
        roomCatalog.add(suiteRoom);

        System.out.println("--- Guest Room Search Results ---");
        System.out.println("Displaying only available options:");
        System.out.println("----------------------------------");

        // Perform the search (Read-only operation)
        searchService.performSearch(roomCatalog);

        System.out.println("Search complete. No inventory state was modified.");
    }
}

/**
 * Use Case 4: Search Service
 * Handles read-only access to inventory.
 */
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Requirement: Retrieve availability and display only rooms > 0.
     * Ensures Separation of Concerns and Defensive Programming.
     */
    public void performSearch(List<Room> rooms) {
        for (Room room : rooms) {
            int count = inventory.getAvailability(room.getRoomType());

            // Validation Logic: Filter out unavailable rooms
            if (count > 0) {
                System.out.println("Room Type:  " + room.getRoomType());
                System.out.println("Beds:       " + room.getBeds());
                System.out.println("Price:      $" + room.getPrice());
                System.out.println("Available:  " + count);
                System.out.println("Status:     Ready for Booking");
                System.out.println();
            }
        }
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

    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
    public abstract String getRoomType();
}

/**
 * Use Case 2: Concrete Room Implementations
 */
class SingleRoom extends Room {
    public SingleRoom() { super(1, 200, 80.0); }
    @Override public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 350, 120.0); }
    @Override public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 600, 250.0); }
    @Override public String getRoomType() { return "Suite Room"; }
}