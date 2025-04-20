package unischedule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Orar
 * 
 */
public class Timetable {
    private String name;
    private Map<String, ScheduleEntry> entries;

    public Timetable() {
        this.entries = new HashMap<>();
    }

    public Timetable(String name) {
        this.name = name;
        this.entries = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ScheduleEntry> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, ScheduleEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(ScheduleEntry entry) {
        String key = entry.getCourse().getCode() + "_" + 
                     entry.getStudentGroup().getName() + "_" +
                     entry.getTimeSlot().getDay() + "_" +
                     entry.getTimeSlot().getStartTime();
        entries.put(key, entry);
    }

    public boolean hasConflict(ScheduleEntry newEntry) {
        // Pentru cursurile online, nu verificam disponibilitatea salii
        if (newEntry.getRoom() != null) {
            // Verificare disponibilitate room  
            if (!newEntry.getRoom().isAvailable(newEntry.getTimeSlot())) {
                return true;
            }
        }

        // Verificare disponibilitate professor
        if (!newEntry.getCourse().getProfessor().isAvailable(newEntry.getTimeSlot())) {
            return true;
        }

        // Verificare disponibilitate student group
        if (!newEntry.getStudentGroup().isAvailable(newEntry.getTimeSlot())) {
            return true;
        }

        return false;
    }

    public List<ScheduleEntry> getEntriesForStudentGroup(StudentGroup group) {
        return entries.values().stream()
                .filter(entry -> entry.getStudentGroup().getName().equals(group.getName()))
                .collect(Collectors.toList());
    }

    public List<ScheduleEntry> getEntriesForProfessor(Professor professor) {
        return entries.values().stream()
                .filter(entry -> entry.getCourse().getProfessor().getName().equals(professor.getName()))
                .collect(Collectors.toList());
    }

    public List<ScheduleEntry> getEntriesForRoom(Room room) {
        return entries.values().stream()
                .filter(entry -> entry.getRoom() != null && 
                       entry.getRoom().getRoomNumber().equals(room.getRoomNumber()))
                .collect(Collectors.toList());
    }
    
    public List<ScheduleEntry> getOnlineCourses() {
        return entries.values().stream()
                .filter(entry -> entry.getRoom() == null && entry.isOnlineCourse())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orar: ").append(name).append("\n\n");
        
        List<ScheduleEntry> sortedEntries = new ArrayList<>(entries.values());
        
        for (ScheduleEntry entry : sortedEntries) {
            sb.append(entry.toString()).append("\n\n");
        }
        
        return sb.toString();
    }
} 