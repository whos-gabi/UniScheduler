package unischedule.model;

import java.util.ArrayList;
import java.util.List;

import unischedule.model.enums.RoomType;

/**
 * sala din universitate
 */
public class Room implements Schedulable {
    private String roomNumber;
    private int capacity;
    private RoomType type;
    private List<TimeSlot> bookedTimeSlots;

    public Room() {
        this.bookedTimeSlots = new ArrayList<>();
    }

    public Room(String roomNumber, int capacity, RoomType type) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.bookedTimeSlots = new ArrayList<>();
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public List<TimeSlot> getBookedTimeSlots() {
        return bookedTimeSlots;
    }

    public void setBookedTimeSlots(List<TimeSlot> bookedTimeSlots) {
        this.bookedTimeSlots = bookedTimeSlots;
    }

    public void bookTimeSlot(TimeSlot timeSlot) {
        bookedTimeSlots.add(timeSlot);
    }

    @Override
    public String getName() {
        return "Sala " + roomNumber;
    }

    @Override
    public boolean isAvailable(TimeSlot timeSlot) {
        for (TimeSlot bookedSlot : bookedTimeSlots) {
            if (bookedSlot.overlaps(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sala " + roomNumber + " (" + type.getDisplayName() + ", " + capacity + " locuri)";
    }
} 