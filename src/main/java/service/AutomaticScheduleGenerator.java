package service;

import domain.*;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Intelligent automatic schedule generator
 * Creates schedules based on student year, course requirements, professor availability, and room capacity
 * Rules: First course at 8:00, 2-hour slots, max 4 courses (8 hours) per day per group
 */
public class AutomaticScheduleGenerator {
    
    private final TimetableService timetableService;
    
    // Time slots for scheduling (Monday to Friday, 8:00-20:00, 6 slots of 2 hours each)
    private final String[] DAYS = {"Luni", "Marți", "Miercuri", "Joi", "Vineri"};
    private final LocalTime[] TIME_SLOTS = {
        LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0), 
        LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)
    };
    private final int SLOT_DURATION_HOURS = 2;
    private final int MAX_COURSES_PER_DAY = 4; // Maximum 8 hours = 4 courses per day
    
    // Availability tracking
    private Map<String, Set<String>> teacherSchedule = new HashMap<>(); // teacherId -> set of "day_time" slots
    private Map<String, Set<String>> roomSchedule = new HashMap<>(); // roomId -> set of "day_time" slots  
    private Map<String, Set<String>> groupSchedule = new HashMap<>(); // groupName -> set of "day_time" slots
    private Map<String, Map<String, Integer>> groupDailyCounts = new HashMap<>(); // groupName -> day -> course count
    
    public AutomaticScheduleGenerator(TimetableService timetableService) {
        this.timetableService = timetableService;
    }
    
    public List<TimetableEntry> generateCompleteSchedule() {
        List<TimetableEntry> generatedEntries = new ArrayList<>();
        initializeSchedules();
        
        System.out.println("Starting intelligent schedule generation...");
        System.out.println("Rules: 8:00-20:00, 2-hour slots, max 4 courses per day per group");
        
        // Get all data from database
        List<Course> allCourses = timetableService.getAllCourses();
        List<Student> allStudents = timetableService.getAllStudents();
        List<Teacher> allTeachers = timetableService.getAllTeachers();
        List<Room> allRooms = timetableService.getAllRooms();
        
        // Group students by their study groups
        Map<String, List<Student>> studentsByGroup = allStudents.stream()
                .collect(Collectors.groupingBy(Student::getGroupName));
        
        System.out.println("Found: " + allCourses.size() + " courses, " + studentsByGroup.size() + " groups, " 
                          + allTeachers.size() + " teachers, " + allRooms.size() + " rooms");
        
        if (allCourses.isEmpty() || studentsByGroup.isEmpty() || allTeachers.isEmpty() || allRooms.isEmpty()) {
            throw new RuntimeException("Cannot generate schedule: Missing essential data");
        }
        
        int entryId = 1;
        
        // For each student group
        for (Map.Entry<String, List<Student>> groupEntry : studentsByGroup.entrySet()) {
            String groupName = groupEntry.getKey();
            List<Student> groupStudents = groupEntry.getValue();
            
            // Determine the year for this group (assuming all students in group have same year)
            int groupYear = groupStudents.get(0).getYear();
            System.out.println("\n=== Processing group " + groupName + " (Year " + groupYear + ") ===");
            
            // Get courses for this year
            List<Course> yearCourses = allCourses.stream()
                    .filter(course -> course.getYear() == groupYear)
                    .collect(Collectors.toList());
            
            System.out.println("Found " + yearCourses.size() + " courses for year " + groupYear);
            
            // For each course for this year
            for (Course course : yearCourses) {
                System.out.println("Scheduling course: " + course.getName());
                
                // Create entries based on course hours per week
                List<TimetableEntry> courseEntries = scheduleCourseSessions(
                    course, groupName, allTeachers, allRooms, entryId
                );
                
                generatedEntries.addAll(courseEntries);
                entryId += courseEntries.size();
            }
        }
        
        System.out.println("\n=== Schedule Generation Summary ===");
        System.out.println("Generated " + generatedEntries.size() + " timetable entries");
        printScheduleStatistics();
        
        return generatedEntries;
    }
    
    private List<TimetableEntry> scheduleCourseSessions(Course course, String groupName, 
                                                       List<Teacher> allTeachers, List<Room> allRooms, int startEntryId) {
        List<TimetableEntry> courseEntries = new ArrayList<>();
        int entryId = startEntryId;
        
        // Schedule lectures
        for (int i = 0; i < course.getLectureHours(); i += SLOT_DURATION_HOURS) {
            TimetableEntry entry = createScheduleEntry(entryId++, course, groupName, "Curs", 
                                                     allTeachers, allRooms);
            if (entry != null) {
                courseEntries.add(entry);
                System.out.println("  ✓ Scheduled lecture for " + course.getName());
            } else {
                System.out.println("  ❌ Could not schedule lecture for " + course.getName());
            }
        }
        
        // Schedule seminars
        for (int i = 0; i < course.getSeminarHours(); i += SLOT_DURATION_HOURS) {
            TimetableEntry entry = createScheduleEntry(entryId++, course, groupName, "Seminar", 
                                                     allTeachers, allRooms);
            if (entry != null) {
                courseEntries.add(entry);
                System.out.println("  ✓ Scheduled seminar for " + course.getName());
            } else {
                System.out.println("  ❌ Could not schedule seminar for " + course.getName());
            }
        }
        
        // Schedule lab sessions
        for (int i = 0; i < course.getLabHours(); i += SLOT_DURATION_HOURS) {
            TimetableEntry entry = createScheduleEntry(entryId++, course, groupName, "Laborator", 
                                                     allTeachers, allRooms);
            if (entry != null) {
                courseEntries.add(entry);
                System.out.println("  ✓ Scheduled lab for " + course.getName());
            } else {
                System.out.println("  ❌ Could not schedule lab for " + course.getName());
            }
        }
        
        return courseEntries;
    }
    
    private TimetableEntry createScheduleEntry(int entryId, Course course, String groupName, String type,
                                             List<Teacher> allTeachers, List<Room> allRooms) {
        
        // Find best available teacher (one with least occupied slots)
        Teacher bestTeacher = findBestAvailableTeacher(allTeachers);
        if (bestTeacher == null) {
            return null;
        }
        
        // Find suitable room based on type and availability
        Room suitableRoom = findSuitableRoom(allRooms, type);
        if (suitableRoom == null) {
            return null;
        }
        
        // Find best time slot for all parties (respecting daily limits)
        TimeSlot bestSlot = findBestTimeSlot(bestTeacher.getTeacherId(), suitableRoom.getRoomId(), groupName);
        if (bestSlot == null) {
            return null;
        }
        
        // Reserve the time slot
        String timeSlotKey = bestSlot.day + "_" + bestSlot.startTime;
        teacherSchedule.get(bestTeacher.getTeacherId()).add(timeSlotKey);
        roomSchedule.get(suitableRoom.getRoomId()).add(timeSlotKey);
        groupSchedule.get(groupName).add(timeSlotKey);
        
        // Update daily course count for the group
        groupDailyCounts.get(groupName).merge(bestSlot.day, 1, Integer::sum);
        
        // Create the timetable entry
        return new TimetableEntry(
            "TE" + String.format("%03d", entryId),
            course.getCourseId(),
            bestTeacher.getTeacherId(),
            suitableRoom.getRoomId(),
            groupName,
            bestSlot.day,
            bestSlot.startTime,
            bestSlot.endTime,
            type,
            "ALL"
        );
    }
    
    private Teacher findBestAvailableTeacher(List<Teacher> teachers) {
        return teachers.stream()
                .min(Comparator.comparingInt(teacher -> 
                    teacherSchedule.get(teacher.getTeacherId()).size()))
                .orElse(null);
    }
    
    private Room findSuitableRoom(List<Room> rooms, String activityType) {
        // Prioritize room types based on activity
        String preferredType = switch (activityType) {
            case "Laborator" -> "Laborator";
            case "Seminar" -> "Seminar";
            default -> "Amfiteatru"; // For lectures
        };
        
        // First try to find a room of preferred type with least bookings
        Optional<Room> preferredRoom = rooms.stream()
                .filter(room -> room.getType().contains(preferredType))
                .min(Comparator.comparingInt(room -> 
                    roomSchedule.get(room.getRoomId()).size()));
        
        if (preferredRoom.isPresent()) {
            return preferredRoom.get();
        }
        
        // If no preferred type available, find any available room
        return rooms.stream()
                .min(Comparator.comparingInt(room -> 
                    roomSchedule.get(room.getRoomId()).size()))
                .orElse(null);
    }
    
    private TimeSlot findBestTimeSlot(String teacherId, String roomId, String groupName) {
        // Try to find a time slot that works for teacher, room, and group
        // Also respect the daily limit of 4 courses per group per day
        for (String day : DAYS) {
            // Check if group already has maximum courses for this day
            int currentDayCount = groupDailyCounts.get(groupName).getOrDefault(day, 0);
            if (currentDayCount >= MAX_COURSES_PER_DAY) {
                continue; // Skip this day, already at maximum
            }
            
            for (LocalTime startTime : TIME_SLOTS) {
                String timeSlotKey = day + "_" + startTime;
                
                // Check if this slot is free for all three parties
                if (!teacherSchedule.get(teacherId).contains(timeSlotKey) &&
                    !roomSchedule.get(roomId).contains(timeSlotKey) &&
                    !groupSchedule.get(groupName).contains(timeSlotKey)) {
                    
                    LocalTime endTime = startTime.plusHours(SLOT_DURATION_HOURS);
                    return new TimeSlot(day, startTime, endTime);
                }
            }
        }
        
        return null; // No available slot found
    }
    
    private void initializeSchedules() {
        // Initialize empty schedules for all teachers
        List<Teacher> teachers = timetableService.getAllTeachers();
        for (Teacher teacher : teachers) {
            teacherSchedule.put(teacher.getTeacherId(), new HashSet<>());
        }
        
        // Initialize empty schedules for all rooms
        List<Room> rooms = timetableService.getAllRooms();
        for (Room room : rooms) {
            roomSchedule.put(room.getRoomId(), new HashSet<>());
        }
        
        // Initialize empty schedules for all groups
        List<String> groups = timetableService.getAllGroups();
        for (String group : groups) {
            groupSchedule.put(group, new HashSet<>());
            
            // Initialize daily course counts for each group
            Map<String, Integer> dailyCounts = new HashMap<>();
            for (String day : DAYS) {
                dailyCounts.put(day, 0);
            }
            groupDailyCounts.put(group, dailyCounts);
        }
    }
    
    private void printScheduleStatistics() {
        System.out.println("Teacher utilization:");
        teacherSchedule.forEach((teacherId, slots) -> 
            System.out.println("  " + teacherId + ": " + slots.size() + " slots"));
        
        System.out.println("Room utilization:");
        roomSchedule.forEach((roomId, slots) -> 
            System.out.println("  " + roomId + ": " + slots.size() + " slots"));
        
        System.out.println("Group schedules:");
        groupSchedule.forEach((groupName, slots) -> 
            System.out.println("  " + groupName + ": " + slots.size() + " slots"));
            
        System.out.println("Daily course distribution:");
        groupDailyCounts.forEach((groupName, dailyCounts) -> {
            System.out.println("  " + groupName + ":");
            dailyCounts.forEach((day, count) -> 
                System.out.println("    " + day + ": " + count + " courses"));
        });
    }
    
    // Helper class for time slot management
    private static class TimeSlot {
        String day;
        LocalTime startTime;
        LocalTime endTime;
        
        TimeSlot(String day, LocalTime startTime, LocalTime endTime) {
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
} 