import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Use Case 1: Entry point of the Hotel Booking System.
 * Use Case 2: Basic Room Types using abstraction and inheritance.
 * Use Case 3: Centralized Room Inventory using HashMap.
 * Use Case 4: Room Search & Availability Check (Read-only access).
 * Use Case 5: Booking Request (First-Come-First-Served Queue).
 *
 * @author Eshan Pankaj Joshi
 * @version 5.0
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

        inventory.setAvailability(singleRoom.getRoomType(), 10);
        inventory.setAvailability(doubleRoom.getRoomType(), 5);
        inventory.setAvailability(suiteRoom.getRoomType(), 2);

        // ===================================
        // Use Case 4: Room Search Service
        // ===================================
        RoomSearchService searchService = new RoomSearchService(inventory);

        List<Room> roomCatalog = new ArrayList<>();
        roomCatalog.add(singleRoom);
        roomCatalog.add(doubleRoom);
        roomCatalog.add(suiteRoom);

        System.out.println("Available Room Types (Search Results):");
        System.out.println("--------------------------------------");
        searchService.performSearch(roomCatalog);

        // ===================================
        // Use Case 5: Booking Request Intake
        // ===================================
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("--- Processing Booking Requests ---");

        // Simulating multiple guests submitting requests
        bookingQueue.enqueueRequest(new Reservation("Guest_1: Eshan", "Suite Room"));
        bookingQueue.enqueueRequest(new Reservation("Guest_2: Rahul", "Single Room"));
        bookingQueue.enqueueRequest(new Reservation("Guest_3: Priya", "Double Room"));

        // Displaying the state of the queue
        bookingQueue.displayQueueStatus();

        System.out.println("Note: Inventory remains unchanged at " + inventory.getAvailability("Suite Room") + " Suites.");
        System.out.println("Requests are ordered by arrival time (FIFO).");
    }
}

/**
 * Use Case 5: Reservation Domain Object
 * Captures the intent of the guest.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation Request [" + guestName + " for " + roomType + "]";
    }
}

/**
 * Use Case 5: Booking Request Queue
 * Decouples request intake from allocation using FIFO logic.
 */
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        this.queue = new LinkedList<>();
    }

    // Add request to the end of the line
    public void enqueueRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Enqueued: " + reservation);
    }

    // Display current waiting line
    public void displayQueueStatus() {
        System.out.println("\nCurrent Waiting List:");
        for (Reservation res : queue) {
            System.out.println(" - " + res);
        }
        System.out.println();
    }

    // Retrieve the next request for processing
    public Reservation dequeueRequest() {
        return queue.poll();
    }
}

/**
 * Use Case 4: Room Search Service
 */
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void performSearch(List<Room> rooms) {
        for (Room room : rooms) {
            int availability = inventory.getAvailability(room.getRoomType());
            if (availability > 0) {
                System.out.println(room.getRoomType() + " | Price: $" + room.getPrice() + " | Available: " + availability);
            }
        }
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

    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
    public abstract String getRoomType();
}

/**
 * Use Case 2: Room Specializations
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