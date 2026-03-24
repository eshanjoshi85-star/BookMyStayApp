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
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * @author Eshan Pankaj Joshi
 * @version 11.0
 */
public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

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

        // ===================================
        // Use Case 11: Concurrent Booking Simulation (Thread Safety)
        // ===================================
        BookingRequestQueue concurrentQueue = new BookingRequestQueue();

        System.out.println("\n--- Concurrent Booking Simulation ---");

        concurrentQueue.enqueueRequest(new Reservation("Dave", "Single Room"));
        concurrentQueue.enqueueRequest(new Reservation("Eva", "Single Room"));
        concurrentQueue.enqueueRequest(new Reservation("Frank", "Single Room"));

        ConcurrentBookingProcessor processor =
                new ConcurrentBookingProcessor(concurrentQueue, inventory);

        Thread t1 = new Thread(processor);
        Thread t2 = new Thread(processor);
        Thread t3 = new Thread(processor);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("\n--- Final Inventory After Concurrency ---");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
    }
}

/* =============================
   Use Case 11: Concurrent Processor
   ============================= */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingRequestQueue queue,
                                      RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation res = queue.dequeueRequest();

            if (res == null) {
                break;
            }

            process(res);
        }
    }

    private void process(Reservation res) {

        synchronized (inventory) {

            int stock = inventory.getAvailability(res.getRoomType());

            if (stock > 0) {

                inventory.updateAvailability(res.getRoomType(), stock - 1);

                System.out.println(Thread.currentThread().getName() +
                        " CONFIRMED: " + res.getGuestName());
            } else {

                System.out.println(Thread.currentThread().getName() +
                        " FAILED: " + res.getGuestName());
            }
        }
    }
}

/* =============================
   Booking Request Queue (FINAL – Thread Safe)
   ============================= */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void enqueueRequest(Reservation res) {
        queue.add(res);
        System.out.println("Enqueued: " + res.getGuestName());
    }

    public synchronized Reservation dequeueRequest() {
        return queue.poll();
    }

    public synchronized boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

/* =============================
   Use Case 10: Cancellation Service
   ============================= */
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

        int current = inventory.getAvailability(res.getRoomType());
        inventory.updateAvailability(res.getRoomType(), current + 1);

        rollbackStack.push(res.getRoomType());

        confirmedBookings.remove(reservationId);

        history.addReservation(
                new Reservation(reservationId + "-CANCELLED",
                        res.getGuestName(),
                        res.getRoomType())
        );

        System.out.println("CANCELLED: " + res.getGuestName());
    }
}

/* =============================
   Use Case 8: Booking History
   ============================= */
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}

/* =============================
   Use Case 6: Allocation Service
   ============================= */
class RoomAllocationService {

    private RoomInventory inventory;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public boolean processAllocation(Reservation request) {

        int stock = inventory.getAvailability(request.getRoomType());

        if (stock > 0) {
            inventory.updateAvailability(request.getRoomType(), stock - 1);

            System.out.println("CONFIRMED: " + request.getGuestName());
            return true;
        }

        System.out.println("FAILED: " + request.getGuestName());
        return false;
    }
}

/* =============================
   Reservation
   ============================= */
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

/* =============================
   Use Case 3: Inventory
   ============================= */
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

/* =============================
   Use Case 2: Room Model
   ============================= */
abstract class Room {

    public abstract String getRoomType();
}

class SingleRoom extends Room {
    public SingleRoom() { super(); }
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(); }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(); }
    public String getRoomType() { return "Suite Room"; }
}