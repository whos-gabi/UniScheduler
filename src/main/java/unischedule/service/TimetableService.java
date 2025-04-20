package unischedule.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import unischedule.model.Course;
import unischedule.model.OnlineCourse;
import unischedule.model.Professor;
import unischedule.model.Room;
import unischedule.model.Schedulable;
import unischedule.model.ScheduleEntry;
import unischedule.model.StudentGroup;
import unischedule.model.TimeSlot;
import unischedule.model.Timetable;
import unischedule.model.enums.DayOfWeek;

/**
 * Serviciu pentru gestionarea orarului
 */
public class TimetableService {
    private Timetable timetable;
    
    public TimetableService() {
        this.timetable = new Timetable("Orar Universitate");
    }
    
    public TimetableService(Timetable timetable) {
        this.timetable = timetable;
    }
    
    public Timetable getTimetable() {
        return timetable;
    }
    
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
    
    public boolean scheduleEntry(Course course, Room room, StudentGroup group, TimeSlot timeSlot) {
        ScheduleEntry entry = new ScheduleEntry(course, room, group, timeSlot);
        
        if (timetable.hasConflict(entry)) {
            System.out.println("Eroare: Exista un conflict in orar!");
            return false;
        }
        
        // book
        if (room != null) {
            room.bookTimeSlot(timeSlot);
        }
        course.getProfessor().bookTimeSlot(timeSlot);
        group.bookTimeSlot(timeSlot);
        
        timetable.addEntry(entry);
        
        //  handling OnlineCourse
        if (course instanceof OnlineCourse) {
            System.out.println("Curs online programat: " + course.getName() + 
                    " pe platforma " + ((OnlineCourse)course).getPlatformName());
        }
        
        return true;
    }
    
    public List<ScheduleEntry> getStudentGroupSchedule(StudentGroup group) {
        return timetable.getEntriesForStudentGroup(group);
    }
    
    public List<ScheduleEntry> getProfessorSchedule(Professor professor) {
        return timetable.getEntriesForProfessor(professor);
    }
    
    public List<ScheduleEntry> getRoomSchedule(Room room) {
        return timetable.getEntriesForRoom(room);
    }
    
    public List<Room> getAvailableRooms(TimeSlot timeSlot, int minCapacity) {
        List<Room> rooms = new ArrayList<>();
        for (ScheduleEntry entry : timetable.getEntries().values()) {
            Room room = entry.getRoom();
            if (room != null && room.getCapacity() >= minCapacity && room.isAvailable(timeSlot)) {
                rooms.add(room);
            }
        }
        return rooms;
    }
    
    public List<TimeSlot> getAvailableTimeSlots(Schedulable schedulable, DayOfWeek day) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        
        // We'll create 2-hour slots from 8:00 to 20:00
        while (startTime.plusHours(2).compareTo(endTime) <= 0) {
            LocalTime slotEndTime = startTime.plusHours(2);
            TimeSlot slot = new TimeSlot(day, startTime, slotEndTime);
            
            if (schedulable.isAvailable(slot)) {
                availableSlots.add(slot);
            }
            
            startTime = slotEndTime;
        }
        
        return availableSlots;
    }
    
    public void displayTimetable() {
        System.out.println(timetable.toString());
    }
} 