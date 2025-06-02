package service;

import domain.*;
import exceptions.TimetableException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive service class demonstrating collection usage requirements:
 * - Multiple different collections (List, Set, Map, TreeSet, TreeMap)
 * - At least one sorted collection (TreeSet for TimeSlots, TreeMap for groups)
 * - Inheritance usage (Person hierarchy: Student, Teacher extending Person)
 * - Managing all domain objects including new StudentGroup and TimeSlot classes
 */
public class UniversityManagementService {
    
    // Different collection types to meet requirements:
    
    // 1. List - for maintaining insertion order and allowing duplicates
    private List<Person> allPersons; // Demonstrates inheritance usage
    private List<Course> allCourses; // Including OnlineCourse subclass
    private List<TimetableEntry> timetableEntries;
    
    // 2. Set - for unique collections without duplicates
    private Set<String> availableDepartments;
    private Set<String> registeredEmails; // To ensure unique emails
    
    // 3. Map - for key-value associations
    private Map<String, Room> roomsById;
    private Map<String, StudentGroup> groupsById;
    
    // 4. TreeSet - SORTED collection for time slots (implements Comparable)
    private TreeSet<TimeSlot> sortedTimeSlots;
    
    // 5. TreeMap - SORTED map for student groups by name
    private TreeMap<String, List<Student>> studentsByGroup;
    
    // 6. LinkedHashMap - maintains insertion order
    private LinkedHashMap<String, Teacher> teachersByDepartment;
    
    // Arrays as mentioned in requirements (if collections not fully used)
    private String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private int[][] weeklyScheduleMatrix; // 2D array for time conflicts [day][timeSlot]

    public UniversityManagementService() {
        // Initialize all collections
        this.allPersons = new ArrayList<>();
        this.allCourses = new ArrayList<>();
        this.timetableEntries = new ArrayList<>();
        
        this.availableDepartments = new HashSet<>();
        this.registeredEmails = new HashSet<>();
        
        this.roomsById = new HashMap<>();
        this.groupsById = new HashMap<>();
        
        this.sortedTimeSlots = new TreeSet<>(); // Sorted by day and time
        this.studentsByGroup = new TreeMap<>(); // Sorted by group name
        this.teachersByDepartment = new LinkedHashMap<>();
        
        // Initialize weekly schedule matrix (7 days x 24 hours)
        this.weeklyScheduleMatrix = new int[7][24];
    }

    // Person management (demonstrating inheritance)
    public void addPerson(Person person) throws TimetableException {
        if (person == null) {
            throw new TimetableException("Person cannot be null");
        }
        
        if (registeredEmails.contains(person.getEmail())) {
            throw new TimetableException("Email " + person.getEmail() + " is already registered");
        }
        
        allPersons.add(person);
        registeredEmails.add(person.getEmail());
        
        // Add to specific collections based on type
        if (person instanceof Student) {
            Student student = (Student) person;
            studentsByGroup.computeIfAbsent(student.getGroupName(), k -> new ArrayList<>()).add(student);
        } else if (person instanceof Teacher) {
            Teacher teacher = (Teacher) person;
            teachersByDepartment.put(teacher.getDepartment(), teacher);
            availableDepartments.add(teacher.getDepartment());
        }
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(allPersons);
    }

    public List<Student> getAllStudents() {
        return allPersons.stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .collect(Collectors.toList());
    }

    public List<Teacher> getAllTeachers() {
        return allPersons.stream()
                .filter(p -> p instanceof Teacher)
                .map(p -> (Teacher) p)
                .collect(Collectors.toList());
    }

    // Course management (including inheritance - OnlineCourse)
    public void addCourse(Course course) throws TimetableException {
        if (course == null) {
            throw new TimetableException("Course cannot be null");
        }
        
        // Check for duplicate course IDs
        boolean exists = allCourses.stream()
                .anyMatch(c -> c.getCourseId().equals(course.getCourseId()));
        
        if (exists) {
            throw new TimetableException("Course with ID " + course.getCourseId() + " already exists");
        }
        
        allCourses.add(course);
        availableDepartments.add(course.getDepartment());
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(allCourses);
    }

    public List<OnlineCourse> getOnlineCourses() {
        return allCourses.stream()
                .filter(c -> c instanceof OnlineCourse)
                .map(c -> (OnlineCourse) c)
                .collect(Collectors.toList());
    }

    // Room management using Map
    public void addRoom(Room room) throws TimetableException {
        if (room == null || room.getRoomId() == null) {
            throw new TimetableException("Room or room ID cannot be null");
        }
        
        if (roomsById.containsKey(room.getRoomId())) {
            throw new TimetableException("Room with ID " + room.getRoomId() + " already exists");
        }
        
        roomsById.put(room.getRoomId(), room);
    }

    public Room findRoomById(String roomId) {
        return roomsById.get(roomId);
    }

    public Collection<Room> getAllRooms() {
        return roomsById.values();
    }

    // StudentGroup management using Map
    public void addStudentGroup(StudentGroup group) throws TimetableException {
        if (group == null || group.getGroupId() == null) {
            throw new TimetableException("StudentGroup or group ID cannot be null");
        }
        
        if (groupsById.containsKey(group.getGroupId())) {
            throw new TimetableException("StudentGroup with ID " + group.getGroupId() + " already exists");
        }
        
        groupsById.put(group.getGroupId(), group);
    }

    public StudentGroup findStudentGroupById(String groupId) {
        return groupsById.get(groupId);
    }

    public Collection<StudentGroup> getAllStudentGroups() {
        return groupsById.values();
    }

    // TimeSlot management using SORTED TreeSet
    public void addTimeSlot(TimeSlot timeSlot) throws TimetableException {
        if (timeSlot == null) {
            throw new TimetableException("TimeSlot cannot be null");
        }
        
        if (!timeSlot.isValidTimeSlot()) {
            throw new TimetableException("Invalid time slot configuration");
        }
        
        // Check for overlaps
        for (TimeSlot existing : sortedTimeSlots) {
            if (existing.overlaps(timeSlot)) {
                throw new TimetableException("TimeSlot overlaps with existing slot: " + existing.getTimeSlotId());
            }
        }
        
        sortedTimeSlots.add(timeSlot); // Automatically sorted by Comparable implementation
    }

    public TreeSet<TimeSlot> getSortedTimeSlots() {
        return new TreeSet<>(sortedTimeSlots); // Return defensive copy
    }

    public List<TimeSlot> getTimeSlotsForDay(String dayOfWeek) {
        return sortedTimeSlots.stream()
                .filter(ts -> ts.getDayOfWeek().equalsIgnoreCase(dayOfWeek))
                .collect(Collectors.toList());
    }

    // Department management using Set
    public Set<String> getAvailableDepartments() {
        return new HashSet<>(availableDepartments); // Defensive copy
    }

    public void addDepartment(String department) {
        if (department != null && !department.trim().isEmpty()) {
            availableDepartments.add(department);
        }
    }

    // Sorted student groups using TreeMap
    public TreeMap<String, List<Student>> getStudentsByGroupSorted() {
        return new TreeMap<>(studentsByGroup); // Returns sorted by group name
    }

    public List<Student> getStudentsInGroup(String groupName) {
        return studentsByGroup.getOrDefault(groupName, new ArrayList<>());
    }

    // Teachers by department using LinkedHashMap (maintains insertion order)
    public LinkedHashMap<String, Teacher> getTeachersByDepartmentOrdered() {
        return new LinkedHashMap<>(teachersByDepartment);
    }

    // Array operations (as mentioned in requirements)
    public String[] getDayNames() {
        return dayNames.clone(); // Return defensive copy
    }

    public void markTimeSlotBusy(String dayOfWeek, int hour) {
        int dayIndex = getDayIndex(dayOfWeek);
        if (dayIndex >= 0 && hour >= 0 && hour < 24) {
            weeklyScheduleMatrix[dayIndex][hour] = 1; // 1 = busy, 0 = free
        }
    }

    public boolean isTimeSlotFree(String dayOfWeek, int hour) {
        int dayIndex = getDayIndex(dayOfWeek);
        if (dayIndex >= 0 && hour >= 0 && hour < 24) {
            return weeklyScheduleMatrix[dayIndex][hour] == 0;
        }
        return false;
    }

    private int getDayIndex(String dayOfWeek) {
        for (int i = 0; i < dayNames.length; i++) {
            if (dayNames[i].equalsIgnoreCase(dayOfWeek)) {
                return i;
            }
        }
        return -1;
    }

    // Statistical methods using collections
    public Map<String, Long> getPersonCountByRole() {
        return allPersons.stream()
                .collect(Collectors.groupingBy(Person::getRole, Collectors.counting()));
    }

    public Map<String, Long> getCourseCountByDepartment() {
        return allCourses.stream()
                .collect(Collectors.groupingBy(Course::getDepartment, Collectors.counting()));
    }

    public long getOnlineCourseCount() {
        return allCourses.stream().filter(c -> c instanceof OnlineCourse).count();
    }

    // Validation and utility methods
    public boolean isEmailRegistered(String email) {
        return registeredEmails.contains(email);
    }

    public int getTotalPersonCount() {
        return allPersons.size();
    }

    public int getTotalTimeSlotCount() {
        return sortedTimeSlots.size();
    }

    public void clearAllData() {
        allPersons.clear();
        allCourses.clear();
        timetableEntries.clear();
        availableDepartments.clear();
        registeredEmails.clear();
        roomsById.clear();
        groupsById.clear();
        sortedTimeSlots.clear();
        studentsByGroup.clear();
        teachersByDepartment.clear();
        
        // Reset matrix
        for (int i = 0; i < weeklyScheduleMatrix.length; i++) {
            Arrays.fill(weeklyScheduleMatrix[i], 0);
        }
    }
} 