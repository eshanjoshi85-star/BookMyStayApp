import java.util.*;

/**
 * UseCase10BookingCancellation
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
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @author Eshan Pankaj Joshi
 * @version 10.0
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
        inventory.setAvailability(suiteRoom.getRoomType(), 1);

        // ===================================
        // Use Case 5: Booking Requests
        // ===================================
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.enqueueRequest(new Reservation("R1", "Alice", "Suite Room"));
        queue.enqueueRequest(new Reservation("R2", "Bob", "Single Room"));

        // ===================================
        // Use Case 6: Allocation
        // ===================================
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        Map<String, Reservation> confirmedBookings = new HashMap<>();

        System.out.println("\n--- Confirming Bookings ---");

        while (queue.hasPendingRequests()) {

            Reservation res = queue.dequeueRequest();

            boolean success = allocationService.processAllocation(res);

            if (success) {
                confirmedBookings.put(res.getReservationId(), res);
            }
        }

        // ===================================
        // Use Case 8: Booking History
        // ===================================
        BookingHistory history = new BookingHistory();

        for (Reservation res : confirmedBookings.values()) {
            history.addReservation(res);
        }

        System.out.println("\n--- Booking History ---");

        for (Reservation res : history.getReservations()) {
            System.out.println(res);
        }

        // ===================================
        // Use Case 10: Cancellation & Rollback
        // ===================================
        CancellationService cancellationService = new CancellationService(inventory);

        System.out.println("\n--- Cancellation Process ---");

        cancellationService.cancelBooking("R1", confirmedBookings, history);

        System.out.println("\n--- Updated Booking History ---");

        for (Reservation res : history.getReservations()) {
            System.out.println(res);
        }

        System.out.println("\n--- Final Inventory ---");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));
    }
}

/**
 * Use Case 10: Cancellation Service
 */
class CancellationService {

    private RoomInventory inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId,
                              Map<String, Reservation> confirmedBookings,
                              BookingHistory history) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("CANCELLATION FAILED: Reservation does not exist");
            return;
        }

        Reservation res = confirmedBookings.get(reservationId);

        // Restore inventory
        int current = inventory.getAvailability(res.getRoomType());
        inventory.updateAvailability(res.getRoomType(), current + 1);

        // LIFO rollback tracking
        rollbackStack.push(res.getRoomType());

        // Remove from confirmed bookings
        confirmedBookings.remove(reservationId);

        // Add cancellation record
        history.addReservation(
                new Reservation(reservationId + "-CANCELLED",
                        res.getGuestName(),
                        res.getRoomType())
        );

        System.out.println("CANCELLED: " + res.getGuestName() +
                " | Room Released: " + res.getRoomType());
    }
}

/**
 * Use Case 8: Booking History
 */
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
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

    public boolean processAllocation(Reservation request) {

        int stock = inventory.getAvailability(request.getRoomType());

        if (stock > 0) {
            inventory.updateAvailability(request.getRoomType(), stock - 1);

            System.out.println("CONFIRMED: " + request.getGuestName() +
                    " (" + request.getRoomType() + ")");
            return true;
        }

        System.out.println("FAILED: " + request.getGuestName());
        return false;
    }
}

/**
 * Use Case 5: Booking Queue
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void enqueueRequest(Reservation res) {
        queue.add(res);
        System.out.println("Enqueued: " + res.getGuestName());
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

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
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
        inventory.put(roomType, count);
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