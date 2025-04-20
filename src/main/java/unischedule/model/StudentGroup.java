package unischedule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * grupa de studenti
 */
public class StudentGroup implements Schedulable {
    private String name;
    private int year;
    private int numberOfStudents;
    private List<TimeSlot> bookedTimeSlots;

    public StudentGroup() {
        this.bookedTimeSlots = new ArrayList<>();
    }

    public StudentGroup(String name, int year, int numberOfStudents) {
        this.name = name;
        this.year = year;
        this.numberOfStudents = numberOfStudents;
        this.bookedTimeSlots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
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
        return "Grupa " + name + " (Anul " + year + ", " + numberOfStudents + " studenti)";
    }
} 