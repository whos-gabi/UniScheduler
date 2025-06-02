package controller;

import domain.*;
import service.TimetableService;
import service.AuditService;
import exceptions.TimetableException;
import java.util.List;

/**
 * Controller class for handling timetable operations
 * Provides interface between view and service layers
 */
public class TimetableController {
    private final TimetableService timetableService = new TimetableService();
    private final AuditService auditService = AuditService.getInstance();
    
    // Student operations
    public void addStudent(Student student) {
        try {
            timetableService.addStudent(student);
            System.out.println("Student added successfully: " + student);
            auditService.logAction("Add Student");
        } catch (TimetableException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    public void findStudentById(String studentId) {
        try {
            Student student = timetableService.findStudentById(studentId);
            if (student != null) {
                System.out.println("Student found: " + student);
                auditService.logAction("Find Student by ID");
            } else {
                System.out.println("Student not found with ID: " + studentId);
            }
        } catch (Exception e) {
            System.out.println("Error finding student: " + e.getMessage());
        }
    }
    
    public void listAllStudents() {
        System.out.println("Currently registered students:");
        List<Student> students = timetableService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered yet!");
        } else {
            students.forEach(System.out::println);
        }
        auditService.logAction("Show All Students");
    }
    
    public void listStudentsByGroup(String groupName) {
        System.out.println("Students in group " + groupName + ":");
        List<Student> students = timetableService.getStudentsByGroup(groupName);
        if (students.isEmpty()) {
            System.out.println("No students found in group: " + groupName);
        } else {
            students.forEach(System.out::println);
        }
        auditService.logAction("Show Students by Group");
    }
    
    // Teacher operations
    public void addTeacher(Teacher teacher) {
        try {
            timetableService.addTeacher(teacher);
            System.out.println("Teacher added successfully: " + teacher);
            auditService.logAction("Add Teacher");
        } catch (TimetableException e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }
    
    public void findTeacherById(String teacherId) {
        Teacher teacher = timetableService.findTeacherById(teacherId);
        if (teacher != null) {
            System.out.println("Teacher found: " + teacher);
        } else {
            System.out.println("Teacher not found with ID: " + teacherId);
        }
    }
    
    public void listAllTeachers() {
        System.out.println("Currently registered teachers:");
        List<Teacher> teachers = timetableService.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers registered yet!");
        } else {
            teachers.forEach(System.out::println);
        }
        auditService.logAction("Show All Teachers");
    }
    
    // Course operations
    public void addCourse(Course course) {
        try {
            timetableService.addCourse(course);
            System.out.println("Course added successfully: " + course);
            auditService.logAction("Add Course");
        } catch (TimetableException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }
    
    public void findCourseById(String courseId) {
        Course course = timetableService.findCourseById(courseId);
        if (course != null) {
            System.out.println("Course found: " + course);
        } else {
            System.out.println("Course not found with ID: " + courseId);
        }
    }
    
    public void listAllCourses() {
        System.out.println("Currently registered courses:");
        List<Course> courses = timetableService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses registered yet!");
        } else {
            courses.forEach(System.out::println);
        }
        auditService.logAction("Show All Courses");
    }
    
    // Room operations
    public void addRoom(Room room) {
        try {
            timetableService.addRoom(room);
            System.out.println("Room added successfully: " + room);
            auditService.logAction("Add Room");
        } catch (TimetableException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }
    
    public void findRoomById(String roomId) {
        Room room = timetableService.findRoomById(roomId);
        if (room != null) {
            System.out.println("Room found: " + room);
        } else {
            System.out.println("Room not found with ID: " + roomId);
        }
    }
    
    public void listAllRooms() {
        System.out.println("Currently registered rooms:");
        List<Room> rooms = timetableService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms registered yet!");
        } else {
            rooms.forEach(System.out::println);
        }
        auditService.logAction("Show All Rooms");
    }
    
    // Timetable operations
    public void addTimetableEntry(TimetableEntry entry) {
        try {
            timetableService.addTimetableEntry(entry);
            System.out.println("Timetable entry added successfully: " + entry);
        } catch (TimetableException e) {
            System.out.println("Error adding timetable entry: " + e.getMessage());
        }
    }
    
    public void viewTimetableByGroup(String groupName) {
        System.out.println("Timetable for group " + groupName + ":");
        List<TimetableEntry> entries = timetableService.getTimetableForGroup(groupName);
        if (entries.isEmpty()) {
            System.out.println("No timetable entries found for group: " + groupName);
        } else {
            entries.forEach(System.out::println);
        }
        auditService.logAction("View Timetable by Group");
    }
    
    public void viewTimetableByTeacher(String teacherId) {
        System.out.println("Timetable for teacher " + teacherId + ":");
        List<TimetableEntry> entries = timetableService.getTimetableForTeacher(teacherId);
        if (entries.isEmpty()) {
            System.out.println("No timetable entries found for teacher: " + teacherId);
        } else {
            entries.forEach(System.out::println);
        }
        auditService.logAction("View Timetable by Teacher");
    }
    
    public void viewTimetableByRoom(String roomId) {
        System.out.println("Timetable for room " + roomId + ":");
        List<TimetableEntry> entries = timetableService.getTimetableForRoom(roomId);
        if (entries.isEmpty()) {
            System.out.println("No timetable entries found for room: " + roomId);
        } else {
            entries.forEach(System.out::println);
        }
        auditService.logAction("View Timetable by Room");
    }
    
    // Utility operations
    public List<String> getAllGroups() {
        return timetableService.getAllGroups();
    }
} 