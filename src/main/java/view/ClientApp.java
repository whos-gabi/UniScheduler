package view;

import controller.TimetableController;
import domain.*;
import exceptions.TimetableException;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Main client application for the University Timetable System
 * Provides console-based interface for users
 */
public class ClientApp {
    
    private final TimetableController timetableController = new TimetableController();
    private final UserInteraction userInteraction = new UserInteraction();
    
    public static void main(String[] args) {
        ClientApp clientApp = new ClientApp();
        while (true) {
            clientApp.showMenu();
            int option = clientApp.readOption();
            clientApp.execute(option);
        }
    }
    
    private void showMenu() {
        System.out.println("""
                ------------------------------------------------------------------------------
                Welcome to University Timetable Management System (UTMS)
                What do you want to do?
                1. Manage Students (Add/View/Search)
                2. Manage Teachers (Add/View)
                3. Load and View Courses from Curriculum
                4. Manage Rooms (Add/View)
                5. Create Timetable Entry
                6. Generate Timetable for Student Group
                7. Generate Timetable for Teacher
                8. Generate Timetable for Room
                9. View All Timetable Entries
                10. Generate Schedule Image (JSON Export)
                0. Exit
                """);
    }
    
    private int readOption() {
        return userInteraction.readOption();
    }
    
    private void execute(int option) {
        switch (option) {
            case 1: {
                // Manage Students (Add/View/Search)
                studentManagementMenu();
                break;
            }
            case 2: {
                // Manage Teachers (Add/View)
                teacherManagementMenu();
                break;
            }
            case 3: {
                // Load and View Courses from Curriculum
                courseManagementMenu();
                break;
            }
            case 4: {
                // Manage Rooms (Add/View)
                roomManagementMenu();
                break;
            }
            case 5: {
                // Create Timetable Entry
                createTimetableEntry();
                break;
            }
            case 6: {
                // Generate Timetable for Student Group
                String groupName = userInteraction.inputGroupName();
                timetableController.viewTimetableByGroup(groupName);
                break;
            }
            case 7: {
                // Generate Timetable for Teacher
                String teacherId = userInteraction.inputTeacherId();
                timetableController.viewTimetableByTeacher(teacherId);
                break;
            }
            case 8: {
                // Generate Timetable for Room
                String roomId = userInteraction.inputRoomId();
                timetableController.viewTimetableByRoom(roomId);
                break;
            }
            case 9: {
                // View All Timetable Entries
                timetableController.listAllTimetableEntries();
                break;
            }
            case 10: {
                // Generate Schedule Image (JSON Export)
                scheduleImageExportMenu();
                break;
            }
            case 0: {
                userInteraction.closeScanner();
                System.exit(0);
            }
            default: {
                System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Sub-menu methods for consolidated functionality
    private void studentManagementMenu() {
        System.out.println("""
                Student Management:
                1. Add Student
                2. Show All Students  
                3. Find Student by ID
                4. Show Students by Group
                """);
        System.out.print("Choose option: ");
        int choice = userInteraction.readSimpleInt();
        
        switch (choice) {
            case 1: {
                Student student = userInteraction.readStudentDetails();
                timetableController.addStudent(student);
                break;
            }
            case 2: {
                timetableController.listAllStudents();
                break;
            }
            case 3: {
                String studentId = userInteraction.inputStudentId();
                timetableController.findStudentById(studentId);
                break;
            }
            case 4: {
                String groupName = userInteraction.inputGroupName();
                timetableController.listStudentsByGroup(groupName);
                break;
            }
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void teacherManagementMenu() {
        System.out.println("""
                Teacher Management:
                1. Add Teacher
                2. Show All Teachers
                """);
        System.out.print("Choose option: ");
        int choice = userInteraction.readSimpleInt();
        
        switch (choice) {
            case 1: {
                Teacher teacher = userInteraction.readTeacherDetails();
                timetableController.addTeacher(teacher);
                break;
            }
            case 2: {
                timetableController.listAllTeachers();
                break;
            }
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void courseManagementMenu() {
        System.out.println("""
                Course Management:
                1. Load Courses from curriculum.json
                2. Show All Loaded Courses
                3. Add Custom Course
                """);
        System.out.print("Choose option: ");
        int choice = userInteraction.readSimpleInt();
        
        switch (choice) {
            case 1: {
                timetableController.loadCoursesFromCurriculum();
                break;
            }
            case 2: {
                timetableController.listAllCourses();
                break;
            }
            case 3: {
                Course course = userInteraction.readCourseDetails();
                timetableController.addCourse(course);
                break;
            }
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void roomManagementMenu() {
        System.out.println("""
                Room Management:
                1. Add Room
                2. Show All Rooms
                """);
        System.out.print("Choose option: ");
        int choice = userInteraction.readSimpleInt();
        
        switch (choice) {
            case 1: {
                Room room = userInteraction.readRoomDetails();
                timetableController.addRoom(room);
                break;
            }
            case 2: {
                timetableController.listAllRooms();
                break;
            }
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void createTimetableEntry() {
        TimetableEntry entry = userInteraction.readTimetableEntryDetails();
        timetableController.addTimetableEntry(entry);
    }

    private void scheduleImageExportMenu() {
        System.out.println("""
                Schedule Image Export:
                1. Export Teacher Schedule
                2. Export Student Group Schedule
                3. Export Room Schedule
                """);
        System.out.print("Choose option: ");
        int choice = userInteraction.readSimpleInt();
        
        switch (choice) {
            case 1: {
                String teacherId = userInteraction.inputTeacherId();
                timetableController.exportTeacherScheduleAsJson(teacherId);
                break;
            }
            case 2: {
                String groupName = userInteraction.inputGroupName();
                timetableController.exportGroupScheduleAsJson(groupName);
                break;
            }
            case 3: {
                String roomId = userInteraction.inputRoomId();
                timetableController.exportRoomScheduleAsJson(roomId);
                break;
            }
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Use an inner class to handle user interaction
    private static class UserInteraction {

        private final Scanner scanner = new Scanner(System.in);

        int readOption() {
            int option = -1;
            do {
                try {
                    option = readInt();
                } catch (TimetableException exception) {
                    System.out.println("Invalid option! Try again!");
                }
            } while (option < 0 || option > 10);
            return option;
        }

        Student readStudentDetails() {
            System.out.print("Student ID: ");
            String studentId = scanner.next();
            System.out.print("First Name: ");
            String firstName = scanner.next();
            System.out.print("Last Name: ");
            String lastName = scanner.next();
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Year (1-4): ");
            int year = readYear();
            System.out.print("Major: ");
            String major = scanner.next();
            System.out.print("Group Name: ");
            String groupName = scanner.next();
            return new Student(studentId, firstName, lastName, email, year, major, groupName);
        }

        Teacher readTeacherDetails() {
            System.out.print("Teacher ID: ");
            String teacherId = scanner.next();
            System.out.print("First Name: ");
            String firstName = scanner.next();
            System.out.print("Last Name: ");
            String lastName = scanner.next();
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Department: ");
            String department = scanner.next();
            System.out.print("Title: ");
            String title = scanner.next();
            return new Teacher(teacherId, firstName, lastName, email, department, title);
        }

        Course readCourseDetails() {
            System.out.print("Course ID: ");
            String courseId = scanner.next();
            System.out.print("Course Name: ");
            String name = scanner.next();
            System.out.print("Description: ");
            String description = scanner.next();
            System.out.print("Credits: ");
            int credits = -1;
            do {
                try {
                    credits = readInt();
                } catch (TimetableException exception) {
                    System.out.println("Invalid input for credits! Try again!");
                }
            } while (credits <= 0);
            System.out.print("Department: ");
            String department = scanner.next();
            System.out.print("Year (1-4): ");
            int year = readYear();
            System.out.print("Semester: ");
            String semester = scanner.next();
            return new Course(courseId, name, description, credits, department, year, semester);
        }

        Room readRoomDetails() {
            System.out.print("Room ID: ");
            String roomId = scanner.next();
            System.out.print("Room Name: ");
            String name = scanner.next();
            System.out.print("Building: ");
            String building = scanner.next();
            System.out.print("Capacity: ");
            int capacity = -1;
            do {
                try {
                    capacity = readInt();
                } catch (TimetableException exception) {
                    System.out.println("Invalid input for capacity! Try again!");
                }
            } while (capacity <= 0);
            System.out.print("Type: ");
            String type = scanner.next();
            return new Room(roomId, name, building, capacity, type);
        }

        String inputStudentId() {
            System.out.print("Student ID: ");
            return scanner.next();
        }

        String inputTeacherId() {
            System.out.print("Teacher ID: ");
            return scanner.next();
        }

        String inputRoomId() {
            System.out.print("Room ID: ");
            return scanner.next();
        }

        String inputGroupName() {
            System.out.print("Group Name: ");
            return scanner.next();
        }

        void closeScanner() {
            scanner.close();
        }

        private int readYear() {
            int year = -1;
            do {
                try {
                    year = readInt();
                } catch (TimetableException exception) {
                    System.out.println("Invalid input for year! Try again!");
                }
            } while (year < 1 || year > 4);
            return year;
        }

        private int readInt() throws TimetableException {
            String line = scanner.next();
            if (line.matches("^\\d+$")) {
                return Integer.parseInt(line);
            } else {
                throw new TimetableException("Invalid number");
            }
        }

        private int readSimpleInt() {
            int choice = -1;
            do {
                try {
                    choice = readInt();
                } catch (TimetableException exception) {
                    System.out.println("Invalid input! Try again!");
                }
            } while (choice < 1 || choice > 4);
            return choice;
        }

        TimetableEntry readTimetableEntryDetails() {
            System.out.print("Timetable Entry ID: ");
            String entryId = scanner.next();
            System.out.print("Course ID: ");
            String courseId = scanner.next();
            System.out.print("Room ID: ");
            String roomId = scanner.next();
            System.out.print("Day of Week: ");
            String dayOfWeek = scanner.next();
            System.out.print("Start Time: ");
            LocalTime startTime = LocalTime.parse(scanner.next());
            System.out.print("End Time: ");
            LocalTime endTime = LocalTime.parse(scanner.next());
            return new TimetableEntry(entryId, courseId, roomId, dayOfWeek, startTime, endTime);
        }
    }
} 