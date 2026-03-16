import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Use Case 1: Application Entry
 * Use Case 2: Room Domain Modeling
 * Use Case 3: Centralized Room Inventory
 * Use Case 4: Room Search (Read-only access)
 * Use Case 5: Booking Request (FIFO Intake)
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @author Eshan Pankaj Joshi
 * @version 6.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // =============================
        // Use Case 1: Application Entry
        // =============================
        String appName = "Hotel Booking System";
        System.out.println("Welcome to " + appName);
        System.out.println("System initialized successfully.\n");

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
        inventory.setAvailability(suiteRoom.getRoomType(), 1); // Only 1 suite available

        // ===================================
        // Use Case 4: Room Search (Read-Only)
        // ===================================
        RoomSearchService searchService = new RoomSearchService(inventory);
        List<Room> roomCatalog = Arrays.asList(singleRoom, doubleRoom, suiteRoom);

        System.out.println("--- Current Room Availability ---");
        searchService.performSearch(roomCatalog);

        // ===================================
        // Use Case 5: Booking Request Intake
        // ===================================
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("--- Receiving Guest Requests ---");
        bookingQueue.enqueueRequest(new Reservation("Alice", "Suite Room"));
        bookingQueue.enqueueRequest(new Reservation("Bob", "Single Room"));
        bookingQueue.enqueueRequest(new Reservation("Charlie", "Suite Room")); // Should fail (Stock is 1)
        System.out.println();

        // ===================================
        // Use Case 6: Allocation & Confirmation
        // ===================================
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        System.out.println("--- Processing Allocations (FIFO) ---");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.dequeueRequest();
            allocationService.processAllocation(request);
        }

        System.out.println("\nFinal System State Check:");
        System.out.println("Suite Availability: " + inventory.getAvailability("Suite Room"));
    }
}

/**
 * Use Case 6: Room Allocation Service
 * Ensures uniqueness and inventory synchronization.
 */
class RoomAllocationService {
    private RoomInventory inventory;
    // Maps Room Type to a Set of assigned Room IDs to prevent double-booking
    private HashMap<String, Set<String>> allocatedRooms;
    private int idCounter = 101;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    public void processAllocation(Reservation request) {
        String type = request.getRoomType();
        int stock = inventory.getAvailability(type);

        if (stock > 0) {
            // Generate Unique ID (e.g., S-101)
            String roomId = type.substring(0, 1).toUpperCase() + "-" + (idCounter++);

            // Record the allocation to prevent double-booking
            allocatedRooms.get(type).add(roomId);

            // Immediate Inventory Update
            inventory.updateAvailability(type, stock - 1);

            System.out.println("CONFIRMED: " + request.getGuestName() +
                    " assigned to " + roomId + " [" + type + "]");
        } else {
            System.out.println("FAILED: No availability for " + request.getGuestName() + " (" + type + ")");
        }
    }
}

/**
 * Use Case 5: Booking Request & Queue
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
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void enqueueRequest(Reservation res) {
        queue.add(res);
        System.out.println("Enqueued: " + res.getGuestName() + " (" + res.getRoomType() + ")");
    }

    public Reservation dequeueRequest() { return queue.poll(); }
    public boolean hasPendingRequests() { return !queue.isEmpty(); }
}

/**
 * Use Case 4: Search Service
 */
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void performSearch(List<Room> rooms) {
        for (Room room : rooms) {
            int count = inventory.getAvailability(room.getRoomType());
            if (count > 0) {
                System.out.println(room.getRoomType() + ": " + count + " available at $" + room.getPrice());
            }
        }
        System.out.println();
    }
}

/**
 * Use Case 3: Centralized Room Inventory
 */
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void setAvailability(String roomType, int count) { inventory.put(roomType, count); }
    public int getAvailability(String roomType) { return inventory.getOrDefault(roomType, 0); }
    public void updateAvailability(String roomType, int count) { inventory.put(roomType, count); }
}

/**
 * Use Case 2: Abstract Room Model
 */
abstract class Room {
    private int beds;
    private double price;

    public Room(int beds, double price) {
        this.beds = beds;
        this.price = price;
    }

    public int getBeds() { return beds; }
    public double getPrice() { return price; }
    public abstract String getRoomType();
}

class SingleRoom extends Room {
    public SingleRoom() { super(1, 80.0); }
    @Override public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 120.0); }
    @Override public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 250.0); }
    @Override public String getRoomType() { return "Suite Room"; }
}