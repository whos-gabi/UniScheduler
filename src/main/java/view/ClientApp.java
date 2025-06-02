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
                1. Add Student
                2. Show All Students
                3. Find Student by ID
                4. Show Students by Group
                5. Add Teacher
                6. Show All Teachers
                7. Add Course
                8. Show All Courses
                9. Add Room
                10. Show All Rooms
                11. View Timetable by Group
                12. View Timetable by Teacher
                13. View Timetable by Room
                0. Exit
                """);
    }
    
    private int readOption() {
        return userInteraction.readOption();
    }
    
    private void execute(int option) {
        switch (option) {
            case 1: {
                // Add student
                Student student = userInteraction.readStudentDetails();
                timetableController.addStudent(student);
                break;
            }
            case 2: {
                // Show all students
                timetableController.listAllStudents();
                break;
            }
            case 3: {
                // Find student by ID
                String studentId = userInteraction.inputStudentId();
                timetableController.findStudentById(studentId);
                break;
            }
            case 4: {
                // Show students by group
                String groupName = userInteraction.inputGroupName();
                timetableController.listStudentsByGroup(groupName);
                break;
            }
            case 5: {
                // Add teacher
                Teacher teacher = userInteraction.readTeacherDetails();
                timetableController.addTeacher(teacher);
                break;
            }
            case 6: {
                // Show all teachers
                timetableController.listAllTeachers();
                break;
            }
            case 7: {
                // Add course
                Course course = userInteraction.readCourseDetails();
                timetableController.addCourse(course);
                break;
            }
            case 8: {
                // Show all courses
                timetableController.listAllCourses();
                break;
            }
            case 9: {
                // Add room
                Room room = userInteraction.readRoomDetails();
                timetableController.addRoom(room);
                break;
            }
            case 10: {
                // Show all rooms
                timetableController.listAllRooms();
                break;
            }
            case 11: {
                // View timetable by group
                String groupName = userInteraction.inputGroupName();
                timetableController.viewTimetableByGroup(groupName);
                break;
            }
            case 12: {
                // View timetable by teacher
                String teacherId = userInteraction.inputTeacherId();
                timetableController.viewTimetableByTeacher(teacherId);
                break;
            }
            case 13: {
                // View timetable by room
                String roomId = userInteraction.inputRoomId();
                timetableController.viewTimetableByRoom(roomId);
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
            } while (option < 0 || option > 13);
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
    }
} 