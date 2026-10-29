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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
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

    public String getIdNumber() {
        return idNumber;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
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

    public String getRoomId() {
        return roomId;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public String toString() {
        String status = isReserved ? "Reserved" : "Available";
        return "Room ID: " + roomId + " | Price: $" + price + " | Description: " + description + " | Status: " + status;
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

    public int getCapacity() {
        return capacity;
    }

    public String getFeature() {
        return feature;
    }

    @Override
    public String toString() {
        return super.toString() + " | VIP Capacity: " + capacity + " | Feature: " + feature;
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

    public String getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public int getBookingPeriodInDays() {
        return bookingPeriodInDays;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingPeriodInDays(int bookingPeriodInDays) {
        this.bookingPeriodInDays = bookingPeriodInDays;
    }

    public void cancelReservation() {
        this.room.setReserved(false);
    }

    @Override
    public String toString() {
        double totalCost = room.getPrice() * bookingPeriodInDays;
        return "Reservation ID: " + reservationId + " | Guest: " + guest.getName() + 
               " | Room: " + room.getRoomId() + " | Date: " + bookingDate + 
               " | Duration: " + bookingPeriodInDays + " days | Total Cost: $" + totalCost;
    }
}

class HotelSystem {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room findRoomById(String roomId) {
        for (Room r : rooms) {
            if (r.getRoomId().equalsIgnoreCase(roomId)) {
                return r;
            }
        }
        return null;
    }

    public void addReservation(Reservation res) {
        reservations.add(res);
        System.out.println("Reservation added successfully.");
    }

    public void modifyReservation(String resId, String newDate, int newPeriod) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(resId)) {
                r.setBookingDate(newDate);
                r.setBookingPeriodInDays(newPeriod);
                System.out.println("Reservation modified successfully.");
                return;
            }
        }
        System.out.println("Reservation ID not found.");
    }

    public void deleteReservation(String resId) {
        Reservation target = null;
        for (Reservation r : reservations) {
            if (r.getReservationId().equalsIgnoreCase(resId)) {
                target = r;
                break;
            }
        }
        
        if (target != null) {
            target.cancelReservation();
            reservations.remove(target);
            System.out.println("Reservation cancelled and removed successfully.");
        } else {
            System.out.println("Reservation ID not found.");
        }
    }

    public void displayAllRoomReports() {
        System.out.println("\nAll Rooms Status Report:");
        for (Room room : rooms) {
            System.out.println(room);
            if (room.isReserved()) {
                for (Reservation r : reservations) {
                    if (r.getRoom().getRoomId().equalsIgnoreCase(room.getRoomId())) {
                        System.out.println("   " + r);
                    }
                }
            }
        }
    }

    public void displayUnreservedRooms() {
        System.out.println("\nAvailable Rooms:");
        boolean found = false;
        for (Room r : rooms) {
            if (!r.isReserved()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms currently.");
        }
    }

    public void searchRoomsByPrice(double maxPrice) {
        System.out.println("\nRooms with price up to $" + maxPrice + ":");
        boolean found = false;
        for (Room r : rooms) {
            if (r.getPrice() <= maxPrice) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms found within this price range.");
        }
    }

    public void displayVipRoomReports() {
        System.out.println("\nVIP Rooms Report:");
        for (Room r : rooms) {
            if (r instanceof VipRoom) {
                System.out.println(r);
                if (r.isReserved()) {
                    for (Reservation res : reservations) {
                        if (res.getRoom().getRoomId().equalsIgnoreCase(r.getRoomId())) {
                            System.out.println("   " + res);
                        }
                    }
                }
            }
        }
    }

    public void displayReservedRooms() {
        System.out.println("\nReserved Rooms:");
        boolean found = false;
        for (Room r : rooms) {
            if (r.isReserved()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms are currently reserved.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HotelSystem system = new HotelSystem();
        Scanner scanner = new Scanner(System.in);

        system.addRoom(new Room("R101", 100.0, "Standard Single Room"));
        system.addRoom(new Room("R102", 150.0, "Deluxe Double Room"));
        system.addRoom(new VipRoom("V201", 300.0, "Executive Suite", 4, "Jacuzzi and Sea View"));
        system.addRoom(new VipRoom("V202", 450.0, "Presidential Suite", 6, "Butler and Heli-pad Access"));

        while (true) {
            System.out.println("\nHotel Reservation Management System");
            System.out.println("1. Add Reservation");
            System.out.println("2. Modify Reservation");
            System.out.println("3. Delete Reservation");
            System.out.println("4. View All Rooms Report");
            System.out.println("5. View Available Rooms");
            System.out.println("6. Search Rooms by Price");
            System.out.println("7. View VIP Rooms Report");
            System.out.println("8. View Reserved Rooms");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String resId = scanner.nextLine();
                    
                    System.out.print("Enter Room ID: ");
                    String roomId = scanner.nextLine();
                    Room selectedRoom = system.findRoomById(roomId);

                    if (selectedRoom == null) {
                        System.out.println("Error: Room does not exist.");
                    } else if (selectedRoom.isReserved()) {
                        System.out.println("Error: Room is already reserved.");
                    } else {
                        System.out.print("Enter Guest Name: ");
                        String gName = scanner.nextLine();
                        System.out.print("Enter Guest Type: ");
                        String gType = scanner.nextLine();
                        System.out.print("Enter Guest Phone: ");
                        String gPhone = scanner.nextLine();
                        System.out.print("Enter Guest ID: ");
                        String gId = scanner.nextLine();
                        System.out.print("Enter Additional Details: ");
                        String gDetails = scanner.nextLine();

                        Guest guest = new Guest(gName, gType, gPhone, gId, gDetails);

                        System.out.print("Enter Booking Date: ");
                        String bDate = scanner.nextLine();
                        System.out.print("Enter Duration in Days: ");
                        int bDays = scanner.nextInt();

                        Reservation newRes = new Reservation(resId, selectedRoom, guest, bDate, bDays);
                        system.addReservation(newRes);
                    }
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to Modify: ");
                    String mId = scanner.nextLine();
                    System.out.print("Enter New Date: ");
                    String mDate = scanner.nextLine();
                    System.out.print("Enter New Duration in Days: ");
                    int mDays = scanner.nextInt();
                    system.modifyReservation(mId, mDate, mDays);
                    break;

                case 3:
                    System.out.print("Enter Reservation ID to Delete: ");
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
                    System.out.print("Enter Maximum Price: ");
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
                    System.out.println("System terminated.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    }
