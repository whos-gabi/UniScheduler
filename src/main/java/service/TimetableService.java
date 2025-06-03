package service;

import domain.*;
import exceptions.TimetableException;
import persistence.StudentRepository;
import persistence.TimetableEntryRepository;
import persistence.TeacherRepository;
import persistence.CourseRepository;
import persistence.RoomRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main service class for managing university timetable operations
 * Implements business logic and integrates with repository layer
 */
public class TimetableService {
    
    // Database repositories
    private final StudentRepository studentRepository = StudentRepository.getInstance();
    private final TimetableEntryRepository timetableEntryRepository = TimetableEntryRepository.getInstance();
    private final TeacherRepository teacherRepository = TeacherRepository.getInstance();
    private final CourseRepository courseRepository = CourseRepository.getInstance();
    private final RoomRepository roomRepository = RoomRepository.getInstance();

    public TimetableService() {
        // All data now managed by repositories
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

    // Teacher management operations - using database
    public void addTeacher(Teacher teacher) throws TimetableException {
        if (teacher == null || teacher.getTeacherId() == null) {
            throw new TimetableException("Teacher or teacher ID cannot be null");
        }
        if (findTeacherById(teacher.getTeacherId()) != null) {
            throw new TimetableException("Teacher with ID " + teacher.getTeacherId() + " already exists");
        }
        teacherRepository.save(teacher);
    }

    public Teacher findTeacherById(String teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Course management operations - using database  
    public void addCourse(Course course) throws TimetableException {
        if (course == null || course.getCourseId() == null) {
            throw new TimetableException("Course or course ID cannot be null");
        }
        if (findCourseById(course.getCourseId()) != null) {
            throw new TimetableException("Course with ID " + course.getCourseId() + " already exists");
        }
        courseRepository.save(course);
    }

    public Course findCourseById(String courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByYear(int year) {
        return courseRepository.findByYear(year);
    }

    // Room management operations - using database
    public void addRoom(Room room) throws TimetableException {
        if (room == null || room.getRoomId() == null) {
            throw new TimetableException("Room or room ID cannot be null");
        }
        if (findRoomById(room.getRoomId()) != null) {
            throw new TimetableException("Room with ID " + room.getRoomId() + " already exists");
        }
        roomRepository.save(room);
    }

    public Room findRoomById(String roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Timetable management operations - using database
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
        
        timetableEntryRepository.save(entry);
    }

    public List<TimetableEntry> getTimetableForGroup(String groupName) {
        return timetableEntryRepository.findByGroupName(groupName);
    }

    public List<TimetableEntry> getTimetableForTeacher(String teacherId) {
        return timetableEntryRepository.findByTeacherId(teacherId);
    }

    public List<TimetableEntry> getTimetableForRoom(String roomId) {
        return timetableEntryRepository.findByRoomId(roomId);
    }

    public List<TimetableEntry> getAllTimetableEntries() {
        return timetableEntryRepository.findAll();
    }

    public void removeTimetableEntry(TimetableEntry entry) throws TimetableException {
        if (entry == null) {
            throw new TimetableException("Timetable entry cannot be null");
        }
        timetableEntryRepository.delete(entry);
    }

    public void clearAllTimetableEntries() {
        List<TimetableEntry> allEntries = getAllTimetableEntries();
        for (TimetableEntry entry : allEntries) {
            timetableEntryRepository.delete(entry);
        }
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