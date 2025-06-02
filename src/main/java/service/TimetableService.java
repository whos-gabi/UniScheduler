package service;

import domain.*;
import exceptions.TimetableException;
import persistence.StudentRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main service class for managing university timetable operations
 * Implements business logic and integrates with repository layer
 */
public class TimetableService {
    
    // Repository dependencies - using singleton pattern
    private final StudentRepository studentRepository = StudentRepository.getInstance();
    
    // In-memory collections for entities not yet implemented with database
    private List<Teacher> teachers;
    private List<Course> courses;
    private List<Room> rooms;
    private List<TimetableEntry> timetableEntries;
    
    // Sorted collections for efficient searching
    private TreeMap<String, List<TimetableEntry>> timetableByGroup;

    public TimetableService() {
        this.teachers = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.timetableEntries = new ArrayList<>();
        
        // Initialize sorted collections
        this.timetableByGroup = new TreeMap<>();
    }

    // Student management operations - using database
    public void addStudent(Student student) throws TimetableException {
        if (student == null || student.getStudentId() == null) {
            throw new TimetableException("Student or student ID cannot be null");
        }
        
        // Check if student already exists
        if (studentRepository.findById(student.getStudentId()).isPresent()) {
            throw new TimetableException("Student with ID " + student.getStudentId() + " already exists");
        }
        
        studentRepository.save(student);
    }

    public Student findStudentById(String studentId) throws TimetableException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new TimetableException("Student ID cannot be null or empty");
        }
        
        return studentRepository.findById(studentId).orElse(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByGroup(String groupName) {
        return studentRepository.findByGroupName(groupName);
    }

    public void updateStudent(Student student) throws TimetableException {
        if (studentRepository.findById(student.getStudentId()).isEmpty()) {
            throw new TimetableException("Cannot update student. Student with ID " + student.getStudentId() + " does not exist");
        }
        
        studentRepository.update(student);
    }

    public void removeStudent(String studentId) throws TimetableException {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new TimetableException("Cannot delete student. Student with ID " + studentId + " does not exist");
        }
        
        studentRepository.delete(student.get());
    }

    // Teacher management operations - in-memory for now
    public void addTeacher(Teacher teacher) throws TimetableException {
        if (teacher == null || teacher.getTeacherId() == null) {
            throw new TimetableException("Teacher or teacher ID cannot be null");
        }
        if (findTeacherById(teacher.getTeacherId()) != null) {
            throw new TimetableException("Teacher with ID " + teacher.getTeacherId() + " already exists");
        }
        teachers.add(teacher);
    }

    public Teacher findTeacherById(String teacherId) {
        return teachers.stream()
                .filter(t -> t.getTeacherId().equals(teacherId))
                .findFirst()
                .orElse(null);
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    // Course management operations - in-memory for now  
    public void addCourse(Course course) throws TimetableException {
        if (course == null || course.getCourseId() == null) {
            throw new TimetableException("Course or course ID cannot be null");
        }
        if (findCourseById(course.getCourseId()) != null) {
            throw new TimetableException("Course with ID " + course.getCourseId() + " already exists");
        }
        courses.add(course);
    }

    public Course findCourseById(String courseId) {
        return courses.stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public List<Course> getCoursesByYear(int year) {
        return courses.stream()
                .filter(c -> c.getYear() == year)
                .collect(Collectors.toList());
    }

    // Room management operations - in-memory for now
    public void addRoom(Room room) throws TimetableException {
        if (room == null || room.getRoomId() == null) {
            throw new TimetableException("Room or room ID cannot be null");
        }
        if (findRoomById(room.getRoomId()) != null) {
            throw new TimetableException("Room with ID " + room.getRoomId() + " already exists");
        }
        rooms.add(room);
    }

    public Room findRoomById(String roomId) {
        return rooms.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    // Timetable management operations - in-memory for now
    public void addTimetableEntry(TimetableEntry entry) throws TimetableException {
        if (entry == null) {
            throw new TimetableException("Timetable entry cannot be null");
        }
        
        // Validate references exist
        if (findCourseById(entry.getCourseId()) == null) {
            throw new TimetableException("Course with ID " + entry.getCourseId() + " does not exist");
        }
        if (findTeacherById(entry.getTeacherId()) == null) {
            throw new TimetableException("Teacher with ID " + entry.getTeacherId() + " does not exist");
        }
        if (findRoomById(entry.getRoomId()) == null) {
            throw new TimetableException("Room with ID " + entry.getRoomId() + " does not exist");
        }
        
        timetableEntries.add(entry);
        
        // Update sorted collection
        timetableByGroup.computeIfAbsent(entry.getGroupName(), k -> new ArrayList<>()).add(entry);
    }

    public List<TimetableEntry> getTimetableForGroup(String groupName) {
        return timetableByGroup.getOrDefault(groupName, new ArrayList<>());
    }

    public List<TimetableEntry> getTimetableForTeacher(String teacherId) {
        return timetableEntries.stream()
                .filter(e -> e.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    public List<TimetableEntry> getTimetableForRoom(String roomId) {
        return timetableEntries.stream()
                .filter(e -> e.getRoomId().equals(roomId))
                .collect(Collectors.toList());
    }

    public List<TimetableEntry> getAllTimetableEntries() {
        return new ArrayList<>(timetableEntries);
    }

    // Utility methods
    public Map<String, Integer> getStudentCountByGroup() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(
                    Student::getGroupName,
                    Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
                ));
    }

    public List<String> getAllGroups() {
        return getAllStudents().stream()
                .map(Student::getGroupName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
} 