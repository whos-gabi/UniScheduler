package unischedule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezinta un profesor din universitate
 */
public class Professor implements Schedulable {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String name;
    private String department;
    private String email;
    private String phone;
    private List<TimeSlot> bookedTimeSlots;

    public Professor() {
        this.bookedTimeSlots = new ArrayList<>();
    }

    public Professor(String name, String department) {
        this.name = name;
        this.department = department;
        this.bookedTimeSlots = new ArrayList<>();
    }
    
    public Professor(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.bookedTimeSlots = new ArrayList<>();
    }
    
    public Professor(String title, String firstName, String lastName, String email, String department) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.email = email;
        this.department = department;
        this.bookedTimeSlots = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        if (firstName != null && lastName != null) {
            this.name = firstName + " " + lastName;
        }
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
        if (firstName != null && lastName != null) {
            this.name = firstName + " " + lastName;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (title != null && !title.isEmpty()) {
            return title + " " + firstName + " " + lastName + " (Dep. " + department + ")";
        }
        return "Prof. " + name + " (Dep. " + department + ", ID: " + id + ")";
    }
} 