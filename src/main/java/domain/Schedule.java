package domain;

import java.util.*;

/**
 * Represents a weekly schedule/timetable for teachers, student groups, or rooms
 * Designed for generating timetable images (Mon-Fri, 8-20h)
 */
public class Schedule {
    private String scheduleId;
    private String name; // Name of the schedule
    private String type; // "TEACHER", "STUDENT_GROUP", "ROOM"
    private String entityId; // ID of teacher, group, or room
    private Map<String, Map<Integer, String>> weeklySchedule; // day -> hour -> activity
    private Set<String> assignedCourses;
    private String semester;
    private int year;

    // Constants for timetable generation
    public static final String[] WEEKDAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final int START_HOUR = 8;
    public static final int END_HOUR = 20;

    public Schedule() {
        this.weeklySchedule = new HashMap<>();
        this.assignedCourses = new HashSet<>();
        initializeWeeklySchedule();
    }

    public Schedule(String scheduleId, String name, String type, String entityId, String semester, int year) {
        this.scheduleId = scheduleId;
        this.name = name;
        this.type = type;
        this.entityId = entityId;
        this.semester = semester;
        this.year = year;
        this.weeklySchedule = new HashMap<>();
        this.assignedCourses = new HashSet<>();
        initializeWeeklySchedule();
    }

    private void initializeWeeklySchedule() {
        for (String day : WEEKDAYS) {
            Map<Integer, String> daySchedule = new HashMap<>();
            for (int hour = START_HOUR; hour <= END_HOUR; hour++) {
                daySchedule.put(hour, null); // null means free slot
            }
            weeklySchedule.put(day, daySchedule);
        }
    }

    // Getters and Setters
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<String, Map<Integer, String>> getWeeklySchedule() {
        return new HashMap<>(weeklySchedule);
    }

    public Set<String> getAssignedCourses() {
        return new HashSet<>(assignedCourses);
    }

    // Business methods for schedule management
    public boolean addActivity(String day, int hour, String activity) {
        if (!isValidDay(day) || !isValidHour(hour)) {
            return false;
        }
        
        Map<Integer, String> daySchedule = weeklySchedule.get(day);
        if (daySchedule.get(hour) == null) { // Slot is free
            daySchedule.put(hour, activity);
            assignedCourses.add(activity);
            return true;
        }
        return false; // Slot is occupied
    }

    public boolean removeActivity(String day, int hour) {
        if (!isValidDay(day) || !isValidHour(hour)) {
            return false;
        }
        
        Map<Integer, String> daySchedule = weeklySchedule.get(day);
        String removedActivity = daySchedule.put(hour, null);
        
        if (removedActivity != null) {
            // Check if this was the last occurrence of this activity
            boolean activityExists = weeklySchedule.values().stream()
                    .flatMap(schedule -> schedule.values().stream())
                    .anyMatch(activity -> removedActivity.equals(activity));
            
            if (!activityExists) {
                assignedCourses.remove(removedActivity);
            }
            return true;
        }
        return false;
    }

    public String getActivity(String day, int hour) {
        if (!isValidDay(day) || !isValidHour(hour)) {
            return null;
        }
        return weeklySchedule.get(day).get(hour);
    }

    public boolean isSlotFree(String day, int hour) {
        return getActivity(day, hour) == null;
    }

    public List<String> getFreeSlots(String day) {
        List<String> freeSlots = new ArrayList<>();
        if (isValidDay(day)) {
            Map<Integer, String> daySchedule = weeklySchedule.get(day);
            for (int hour = START_HOUR; hour <= END_HOUR; hour++) {
                if (daySchedule.get(hour) == null) {
                    freeSlots.add(hour + ":00");
                }
            }
        }
        return freeSlots;
    }

    public int getTotalOccupiedHours() {
        return (int) weeklySchedule.values().stream()
                .flatMap(schedule -> schedule.values().stream())
                .filter(Objects::nonNull)
                .count();
    }

    public int getTotalFreeHours() {
        int totalSlots = WEEKDAYS.length * (END_HOUR - START_HOUR + 1);
        return totalSlots - getTotalOccupiedHours();
    }

    // Validation methods
    private boolean isValidDay(String day) {
        return Arrays.asList(WEEKDAYS).contains(day);
    }

    private boolean isValidHour(int hour) {
        return hour >= START_HOUR && hour <= END_HOUR;
    }

    // Method to generate JSON for Python script
    public Map<String, Object> toJsonFormat() {
        Map<String, Object> json = new HashMap<>();
        json.put("scheduleId", scheduleId);
        json.put("name", name);
        json.put("type", type);
        json.put("entityId", entityId);
        json.put("semester", semester);
        json.put("year", year);
        json.put("weeklySchedule", weeklySchedule);
        json.put("totalOccupiedHours", getTotalOccupiedHours());
        json.put("totalFreeHours", getTotalFreeHours());
        return json;
    }

    // Clear all activities
    public void clearSchedule() {
        assignedCourses.clear();
        initializeWeeklySchedule();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(scheduleId, schedule.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", entityId='" + entityId + '\'' +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", totalOccupiedHours=" + getTotalOccupiedHours() +
                ", totalFreeHours=" + getTotalFreeHours() +
                ", coursesCount=" + assignedCourses.size() +
                '}';
    }
} 