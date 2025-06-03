package controller;

import domain.*;
import service.AuditService;
import service.TimetableService;
import java.util.List;

/**
 * Main application controller for entity management operations
 * Handles CRUD operations for students, teachers, courses, rooms, and timetable entries
 */
public class TimetableController {
    
    private final TimetableService timetableService = new TimetableService();
    private final AuditService auditService = AuditService.getInstance();
    
    // Student operations
    public void addStudent(Student student) {
        try {
            timetableService.addStudent(student);
            System.out.println("Student added successfully: " + student.getFirstName() + " " + student.getLastName());
            auditService.logAction("Add Student");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    public void findStudentById(String studentId) {
        try {
            Student student = timetableService.findStudentById(studentId);
            if (student != null) {
                System.out.println("Found student: " + student);
            } else {
                System.out.println("Student not found with ID: " + studentId);
            }
            auditService.logAction("Find Student by ID");
        } catch (Exception e) {
            System.out.println("Error finding student: " + e.getMessage());
        }
    }
    
    public void listAllStudents() {
        System.out.println("All students:");
        List<Student> students = timetableService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found!");
        } else {
            students.forEach(student -> System.out.println(student.toString()));
        }
        auditService.logAction("List All Students");
    }
    
    public void listStudentsByGroup(String groupName) {
        System.out.println("Students in group " + groupName + ":");
        List<Student> students = timetableService.getStudentsByGroup(groupName);
        if (students.isEmpty()) {
            System.out.println("No students found in group: " + groupName);
        } else {
            students.forEach(student -> System.out.println(student.toString()));
        }
        auditService.logAction("List Students by Group");
    }
    
    // Teacher operations
    public void addTeacher(Teacher teacher) {
        try {
            timetableService.addTeacher(teacher);
            System.out.println("Teacher added successfully: " + teacher.getFirstName() + " " + teacher.getLastName());
            auditService.logAction("Add Teacher");
        } catch (Exception e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }
    
    public void findTeacherById(String teacherId) {
        Teacher teacher = timetableService.findTeacherById(teacherId);
        if (teacher != null) {
            System.out.println("Found teacher: " + teacher);
        } else {
            System.out.println("Teacher not found with ID: " + teacherId);
        }
        auditService.logAction("Find Teacher by ID");
    }
    
    public void listAllTeachers() {
        System.out.println("All teachers:");
        List<Teacher> teachers = timetableService.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers found!");
        } else {
            teachers.forEach(teacher -> System.out.println(teacher.toString()));
        }
        auditService.logAction("List All Teachers");
    }
    
    // Course operations
    public void addCourse(Course course) {
        try {
            timetableService.addCourse(course);
            System.out.println("Course added successfully: " + course.getName());
            auditService.logAction("Add Course");
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }
    
    public void findCourseById(String courseId) {
        Course course = timetableService.findCourseById(courseId);
        if (course != null) {
            System.out.println("Found course: " + course);
        } else {
            System.out.println("Course not found with ID: " + courseId);
        }
        auditService.logAction("Find Course by ID");
    }
    
    public void listAllCourses() {
        System.out.println("All courses:");
        List<Course> courses = timetableService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found!");
        } else {
            courses.forEach(course -> System.out.println(course.toString()));
        }
        auditService.logAction("List All Courses");
    }
    
    // Room operations
    public void addRoom(Room room) {
        try {
            timetableService.addRoom(room);
            System.out.println("Room added successfully: " + room.getName());
            auditService.logAction("Add Room");
        } catch (Exception e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }
    
    public void findRoomById(String roomId) {
        Room room = timetableService.findRoomById(roomId);
        if (room != null) {
            System.out.println("Found room: " + room);
        } else {
            System.out.println("Room not found with ID: " + roomId);
        }
        auditService.logAction("Find Room by ID");
    }
    
    public void listAllRooms() {
        System.out.println("All rooms:");
        List<Room> rooms = timetableService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found!");
        } else {
            rooms.forEach(room -> System.out.println(room.toString()));
        }
        auditService.logAction("List All Rooms");
    }
    
    // Timetable entry operations
    public void addTimetableEntry(TimetableEntry entry) {
        try {
            timetableService.addTimetableEntry(entry);
            System.out.println("Timetable entry added successfully");
            auditService.logAction("Add Timetable Entry");
        } catch (Exception e) {
            System.out.println("Error adding timetable entry: " + e.getMessage());
        }
    }
    
    // Utility operations
    public List<String> getAllGroups() {
        return timetableService.getAllGroups();
    }
    
    public void loadCoursesFromCurriculum() {
        // Simplified: just show a message that courses can be added manually
        System.out.println("Course loading simplified. Use 'Add Custom Course' to add courses manually.");
        System.out.println("You can add courses with types: DF, DS, DC and exam types: E, V");
        auditService.logAction("Load Courses Info");
    }
} 