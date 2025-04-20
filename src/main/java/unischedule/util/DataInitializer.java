package unischedule.util;

import java.time.LocalTime;
import java.util.List;

import unischedule.model.Course;
import unischedule.model.Professor;
import unischedule.model.Room;
import unischedule.model.ScheduleEntry;
import unischedule.model.StudentGroup;
import unischedule.model.TimeSlot;
import unischedule.model.enums.CourseType;
import unischedule.model.enums.DayOfWeek;
import unischedule.model.enums.RoomType;
import unischedule.service.ProfessorService;
import unischedule.service.RoomService;
import unischedule.service.StudentGroupService;
import unischedule.service.TimetableService;

/**
 * Clasa utilitara pentru initializarea datelor
 */
public class DataInitializer {
    
    public static void initializeData(ProfessorService professorService, 
                                     StudentGroupService studentGroupService,
                                     RoomService roomService,
                                     TimetableService timetableService) {
        
        // Adauga profesori
        Professor prof1 = new Professor("Popescu Ion", "Informatica");
        Professor prof2 = new Professor("Ionescu Maria", "Matematica");
        Professor prof3 = new Professor("Georgescu Andrei", "Informatica");
        
        professorService.addProfessor(prof1);
        professorService.addProfessor(prof2);
        professorService.addProfessor(prof3);
        
        // Adauga grupe de studenti
        StudentGroup group1 = new StudentGroup("A1", 1, 30);
        StudentGroup group2 = new StudentGroup("A2", 1, 25);
        StudentGroup group3 = new StudentGroup("B1", 2, 20);
        
        studentGroupService.addStudentGroup(group1);
        studentGroupService.addStudentGroup(group2);
        studentGroupService.addStudentGroup(group3);
        
        // Adauga sali
        Room room1 = new Room("101", 100, RoomType.AMFITEATRU);
        Room room2 = new Room("102", 30, RoomType.SALA_SEMINAR);
        Room room3 = new Room("201", 25, RoomType.LABORATOR);
        
        roomService.addRoom(room1);
        roomService.addRoom(room2);
        roomService.addRoom(room3);
        
        // Adauga cursuri si programeaza-le
        Course course1 = new Course("Programare Java", "CS101", CourseType.CURS, prof1);
        Course course2 = new Course("Algoritmi", "CS102", CourseType.CURS, prof3);
        Course course3 = new Course("Matematica", "M101", CourseType.CURS, prof2);
        Course course4 = new Course("Laborator Java", "CS101L", CourseType.LABORATOR, prof1);
        
        // Programeaza orarul
        // Luni
        TimeSlot slot1 = new TimeSlot(DayOfWeek.LUNI, LocalTime.of(8, 0), LocalTime.of(10, 0));
        TimeSlot slot2 = new TimeSlot(DayOfWeek.LUNI, LocalTime.of(10, 0), LocalTime.of(12, 0));
        TimeSlot slot3 = new TimeSlot(DayOfWeek.LUNI, LocalTime.of(14, 0), LocalTime.of(16, 0));
        
        // Marti
        TimeSlot slot4 = new TimeSlot(DayOfWeek.MARTI, LocalTime.of(10, 0), LocalTime.of(12, 0));
        TimeSlot slot5 = new TimeSlot(DayOfWeek.MARTI, LocalTime.of(12, 0), LocalTime.of(14, 0));
        
        // Programeaza cursurile
        timetableService.scheduleEntry(course1, room1, group1, slot1);
        timetableService.scheduleEntry(course3, room1, group2, slot2);
        timetableService.scheduleEntry(course2, room1, group3, slot3);
        timetableService.scheduleEntry(course4, room3, group1, slot4);
        timetableService.scheduleEntry(course4, room3, group2, slot5);
    }
    
    public static void displayAllData(ProfessorService professorService, 
                                     StudentGroupService studentGroupService,
                                     RoomService roomService,
                                     TimetableService timetableService) {
        
        System.out.println("\n=== DATE INITIALIZATE ===\n");
        
        professorService.displayAllProfessors();
        System.out.println();
        
        studentGroupService.displayAllGroups();
        System.out.println();
        
        roomService.displayAllRooms();
        System.out.println();
        
        timetableService.displayTimetable();
        System.out.println();
        
        // Demonstreaza functionalitati
        System.out.println("=== INTEROGARI ===\n");
        
        Professor prof = professorService.findProfessorByName("Popescu Ion");
        if (prof != null) {
            System.out.println("Orarul profesorului " + prof.getName() + ":");
            List<ScheduleEntry> profSchedule = timetableService.getProfessorSchedule(prof);
            for (ScheduleEntry entry : profSchedule) {
                System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot());
            }
            System.out.println();
        }
        
        StudentGroup group = studentGroupService.findGroupByName("A1");
        if (group != null) {
            System.out.println("Orarul grupei " + group.getName() + ":");
            List<ScheduleEntry> groupSchedule = timetableService.getStudentGroupSchedule(group);
            for (ScheduleEntry entry : groupSchedule) {
                System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot());
            }
            System.out.println();
        }
        
        Room room = roomService.findRoomByNumber("101");
        if (room != null) {
            System.out.println("Programul salii " + room.getRoomNumber() + ":");
            List<ScheduleEntry> roomSchedule = timetableService.getRoomSchedule(room);
            for (ScheduleEntry entry : roomSchedule) {
                System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot());
            }
        }
    }
} 