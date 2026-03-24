import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Use Case 1: Application Entry
 * Use Case 2: Room Domain Modeling
 * Use Case 3: Centralized Room Inventory
 * Use Case 4: Room Search (Read-only access)
 * Use Case 5: Booking Request (FIFO Intake)
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Use Case 7: Add-On Service Selection
 * Use Case 8: Booking History & Reporting
 * Use Case 9: Error Handling & Validation
 *
 * @author Eshan Pankaj Joshi
 * @version 9.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // =============================
        // Use Case 1: Application Entry
        // =============================
        System.out.println("Welcome to Hotel Booking System");
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
        inventory.setAvailability(singleRoom.getRoomType(), 2);
        inventory.setAvailability(doubleRoom.getRoomType(), 1);
        inventory.setAvailability(suiteRoom.getRoomType(), 0);

        // ===================================
        // Use Case 4: Room Search
        // ===================================
        RoomSearchService searchService = new RoomSearchService(inventory);
        List<Room> rooms = Arrays.asList(singleRoom, doubleRoom, suiteRoom);

        System.out.println("--- Room Availability ---");
        searchService.performSearch(rooms);

        // ===================================
        // Use Case 5: Booking Queue
        // ===================================
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.enqueueRequest(new Reservation("R1", "Alice", "Suite Room"));
        queue.enqueueRequest(new Reservation("R2", "Bob", "Single Room"));
        queue.enqueueRequest(new Reservation("R3", "Charlie", "Invalid Room"));

        // ===================================
        // Use Case 9: Validation
        // ===================================
        InvalidBookingValidator validator = new InvalidBookingValidator();

        // ===================================
        // Use Case 6: Allocation with Validation
        // ===================================
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        System.out.println("\n--- Processing Bookings with Validation ---");

        while (queue.hasPendingRequests()) {

            Reservation res = queue.dequeueRequest();

            try {

                validator.validate(res, inventory);

                allocationService.processAllocation(res);

            } catch (InvalidBookingException e) {

                System.out.println("ERROR: " + e.getMessage());
            }
        }

        System.out.println("\n--- Final Inventory State ---");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));
    }
}

/**
 * Use Case 9: Custom Exception
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * Use Case 9: Validator
 */
class InvalidBookingValidator {

    public void validate(Reservation res, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!inventory.containsRoomType(res.getRoomType())) {
            throw new InvalidBookingException("Invalid Room Type: " + res.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(res.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for " + res.getRoomType());
        }

        // Validate guest name
        if (res.getGuestName() == null || res.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Invalid Guest Name");
        }
    }
}

/**
 * Use Case 6: Allocation Service
 */
class RoomAllocationService {

    private RoomInventory inventory;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processAllocation(Reservation request) {

        int stock = inventory.getAvailability(request.getRoomType());

        inventory.updateAvailability(request.getRoomType(), stock - 1);

        System.out.println("CONFIRMED: " + request.getGuestName() +
                " (" + request.getRoomType() + ")");
    }
}

/**
 * Use Case 5: Booking Queue
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void enqueueRequest(Reservation res) {
        queue.add(res);
        System.out.println("Enqueued: " + res.getGuestName() +
                " (" + res.getRoomType() + ")");
    }

    public Reservation dequeueRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

/**
 * Reservation Model
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/**
 * Use Case 3: Inventory
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void setAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Inventory cannot be negative");
        }
        inventory.put(roomType, count);
    }

    public boolean containsRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }
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
                System.out.println(room.getRoomType() + ": " +
                        count + " available at $" + room.getPrice());
            }
        }
        System.out.println();
    }
}

/**
 * Use Case 2: Room Model
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
    public SingleRoom() { super(1, 80); }
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 120); }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 250); }
    public String getRoomType() { return "Suite Room"; }
}