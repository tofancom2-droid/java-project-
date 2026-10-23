import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Person {
    private String name;
    private String type;
    private String phone;

    public Person(String name, String type, String phone) {
        this.name = name;
        this.type = type;
        this.phone = phone;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Phone: " + phone;
    }
}

class Guest extends Person {
    private String idNumber;
    private String additionalDetails;

    public Guest(String name, String type, String phone, String idNumber, String additionalDetails) {
        super(name, type, phone);
        this.idNumber = idNumber;
        this.additionalDetails = additionalDetails;
    }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getAdditionalDetails() { return additionalDetails; }
    public void setAdditionalDetails(String additionalDetails) { this.additionalDetails = additionalDetails; }

    @Override
    public String toString() {
        return super.toString() + ", Guest ID: " + idNumber + ", Details: " + additionalDetails;
    }
}

class Room {
    private String roomId;
    private double price;
    private String description;
    private boolean isReserved;

    public Room(String roomId, double price, String description) {
        this.roomId = roomId;
        this.price = price;
        this.description = description;
        this.isReserved = false;
    }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isReserved() { return isReserved; }
    public void setReserved(boolean reserved) { isReserved = reserved; }

    @Override
    public String toString() {
        return "Room ID: " + roomId + " | Price: $" + price + " | Description: " + description + " | Status: " + (isReserved ? "Reserved" : "Available");
    }
}

class VipRoom extends Room {
    private int capacity;
    private String feature;

    public VipRoom(String roomId, double price, String description, int capacity, String feature) {
        super(roomId, price, description);
        this.capacity = capacity;
        this.feature = feature;
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getFeature() { return feature; }
    public void setFeature(String feature) { this.feature = feature; }

    @Override
    public String toString() {
        return super.toString() + " | VIP Capacity: " + capacity + " persons | Feature: " + feature;
    }
}

class Reservation {
    private String reservationId;
    private Room room;
    private Guest guest;
    private String bookingDate;
    private int bookingPeriodInDays;

    public Reservation(String reservationId, Room room, Guest guest, String bookingDate, int bookingPeriodInDays) {
        this.reservationId = reservationId;
        this.room = room;
        this.guest = guest;
        this.bookingDate = bookingDate;
        this.bookingPeriodInDays = bookingPeriodInDays;
        this.room.setReserved(true);
    }

    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }

    public Room getRoom() { return room; }
    public Guest getGuest() { return guest; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public int getBookingPeriodInDays() { return bookingPeriodInDays; }
    public void setBookingPeriodInDays(int bookingPeriodInDays) { this.bookingPeriodInDays = bookingPeriodInDays; }

    public void cancelReservation() {
        this.room.setReserved(false);
    }

    @Override
    public String toString() {
        return "=== Reservation Details [ID: " + reservationId + "] ===\n" +
               "Guest: " + guest.getName() + " (ID: " + guest.getIdNumber() + ")\n" +
               "Room: " + room.getRoomId() + " (" + (room instanceof VipRoom ? "VIP Room" : "Regular Room") + ")\n" +
               "Date: " + bookingDate + " | Duration: " + bookingPeriodInDays + " days\n" +
               "Total Cost: $" + (room.getPrice() * bookingPeriodInDays);
    }
}

class HotelSystem {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addReservation(Reservation res) {
        reservations.add(res);
        System.out.println("-> Reservation added successfully!");
    }

    public void modifyReservation(String resId, String newDate, int newPeriod) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(resId)) {
                r.setBookingDate(newDate);
                r.setBookingPeriodInDays(newPeriod);
                System.out.println("-> Reservation " + resId + " updated successfully.");
                return;
            }
        }
        System.out.println("-> Reservation ID not found!");
    }

    public void deleteReservation(String resId) {
        Reservation toRemove = null;
        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(resId)) {
                toRemove = r;
                break;
            }
        }
        if (toRemove != null) {
            toRemove.cancelReservation();
            reservations.remove(toRemove);
            System.out.println("-> Reservation " + resId + " cancelled and removed successfully.");
        } else {
            System.out.println("-> Reservation ID not found!");
        }
    }

    public void displayAllRoomReports() {
        System.out.println("\n--- ALL ROOMS STATUS REPORT ---");
        for (Room room : rooms) {
            System.out.println(room);
            if (room.isReserved()) {
                for (Reservation r : reservations) {
                    if (r.getRoom().getRoomId().equalsIgnoreCase(room.getRoomId())) {
                        System.out.println("   [Reservation Details: " + r.getGuest().getName() + ", Date: " + r.getBookingDate() + "]");
                    }
                }
            }
        }
    }

    public void displayUnreservedRooms() {
        System.out.println("\n--- AVAILABLE (UNRESERVED) ROOMS ---");
        boolean found = false;
        for (Room r : rooms) {
            if (!r.isReserved()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No unreserved rooms available.");
    }

    public void searchRoomsByPrice(double maxPrice) {
        System.out.println("\n--- ROOMS WITHIN PRICE <= $" + maxPrice + " ---");
        boolean found = false;
        for (Room r : rooms) {
            if (r.getPrice() <= maxPrice) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No rooms found matching this price limit.");
    }

    public void displayVipRoomReports() {
        System.out.println("\n--- VIP ROOMS REPORT ---");
        for (Room r : rooms) {
            if (r instanceof VipRoom) {
                System.out.println(r);
                if (r.isReserved()) {
                    for (Reservation res : reservations) {
                        if (res.getRoom().getRoomId().equalsIgnoreCase(r.getRoomId())) {
                            System.out.println("   [VIP Reservation Details: Guest " + res.getGuest().getName() + ", Duration: " + res.getBookingPeriodInDays() + " Days]");
                        }
                    }
                }
            }
        }
    }

    public void displayReservedRooms() {
        System.out.println("\n--- ALL RESERVED ROOMS ---");
        boolean found = false;
        for (Room r : rooms) {
            if (r.isReserved()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No reserved rooms at the moment.");
    }

    public Room findRoomById(String roomId) {
        for (Room r : rooms) {
            if (r.getRoomId().equalsIgnoreCase(roomId)) return r;
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        HotelSystem system = new HotelSystem();
        Scanner scanner = new Scanner(System.in);

        Room r101 = new Room("R101", 100.0, "Standard Single Bed Room");
        Room r102 = new Room("R102", 150.0, "Deluxe Double Bed Room");
        VipRoom v201 = new VipRoom("V201", 300.0, "Executive Suite", 4, "Private Jacuzzi & Ocean View");
        VipRoom v202 = new VipRoom("V202", 450.0, "Presidential Suite", 6, "Private Butler & Heli-pad Access");

        system.addRoom(r101);
        system.addRoom(r102);
        system.addRoom(v201);
        system.addRoom(v202);

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("   HOTEL RESERVATION MANAGEMENT SYSTEM   ");
            System.out.println("===========================================");
            System.out.println("1. Add New Reservation");
            System.out.println("2. Modify Reservation");
            System.out.println("3. Delete/Cancel Reservation");
            System.out.println("4. View Room Reports (All Rooms & Details)");
            System.out.println("5. View All Unreserved Rooms");
            System.out.println("6. Search Rooms by Max Price");
            System.out.println("7. View VIP Room Reports");
            System.out.println("8. View All Reserved Rooms");
            System.out.println("9. Exit");
            System.out.print("Select an option (1-9): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String resId = scanner.nextLine();
                    System.out.print("Enter Room ID to Reserve (e.g., R101, V201): ");
                    String roomId = scanner.nextLine();
                    Room selectedRoom = system.findRoomById(roomId);

                    if (selectedRoom == null) {
                        System.out.println("Error: Room ID not found!");
                    } else if (selectedRoom.isReserved()) {
                        System.out.println("Error: Room is already reserved!");
                    } else {
                        System.out.print("Enter Guest Name: ");
                        String gName = scanner.nextLine();
                        System.out.print("Enter Guest Type (e.g., VIP/Regular): ");
                        String gType = scanner.nextLine();
                        System.out.print("Enter Guest Phone: ");
                        String gPhone = scanner.nextLine();
                        System.out.print("Enter Guest National ID / Passport: ");
                        String gId = scanner.nextLine();
                        System.out.print("Enter Additional Notes/Details: ");
                        String gDetails = scanner.nextLine();

                        Guest guest = new Guest(gName, gType, gPhone, gId, gDetails);

                        System.out.print("Enter Booking Date (e.g., 2026-11-25): ");
                        String bDate = scanner.nextLine();
                        System.out.print("Enter Booking Duration (Days): ");
                        int bDays = scanner.nextInt();

                        Reservation reservation = new Reservation(resId, selectedRoom, guest, bDate, bDays);
                        system.addReservation(reservation);
                    }
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to Modify: ");
                    String mId = scanner.nextLine();
                    System.out.print("Enter New Booking Date: ");
                    String mDate = scanner.nextLine();
                    System.out.print("Enter New Duration (Days): ");
                    int mDays = scanner.nextInt();
                    system.modifyReservation(mId, mDate, mDays);
                    break;

                case 3:
                    System.out.print("Enter Reservation ID to Cancel/Delete: ");
                    String dId = scanner.nextLine();
                    system.deleteReservation(dId);
                    break;

                case 4:
                    system.displayAllRoomReports();
                    break;

                case 5:
                    system.displayUnreservedRooms();
                    break;

                case 6:
                    System.out.print("Enter Max Budget/Price: ");
                    double maxP = scanner.nextDouble();
                    system.searchRoomsByPrice(maxP);
                    break;

                case 7:
                    system.displayVipRoomReports();
                    break;

                case 8:
                    system.displayReservedRooms();
                    break;

                case 9:
                    System.out.println("Exiting System. Good luck with your defense!");
                    System.exit(0);

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}
