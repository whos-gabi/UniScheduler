package unischedule.util;

import java.time.LocalTime;

import unischedule.model.Course;
import unischedule.model.OnlineCourse;
import unischedule.model.Professor;
import unischedule.model.Room;
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
 * initializarea datelor de test
 */
public class DataInitializer {

    public static void initData(ProfessorService professorService,
            StudentGroupService studentGroupService,
            RoomService roomService,
            TimetableService timetableService) {

        initProfessors(professorService);

        initStudentGroups(studentGroupService);

        initRooms(roomService);
        // Generare orar simplu
        generateSimpleTimetable(professorService, studentGroupService, roomService, timetableService);
    }

    private static void initProfessors(ProfessorService professorService) {
        Professor prof1 = new Professor("Conf.dr.ing.", "Ion", "Popescu", "ion.popescu@unibuc.ro", "Informatica");
        Professor prof2 = new Professor("Prof.dr.", "Maria", "Ionescu", "maria.ionescu@unibuc.ro", "Matematica");
        Professor prof3 = new Professor("Lect.dr.", "Andrei", "Georgescu", "andrei.georgescu@unibuc.ro", "Informatica");
        Professor prof4 = new Professor("Prof.dr.ing.", "Elena", "Dumitrescu", "elena.dumitrescu@unibuc.ro", "Fizica");
        Professor prof5 = new Professor("Asist.dr.", "Mihai", "Vasilescu", "mihai.vasilescu@unibuc.ro", "Informatica");

        prof1.setPhone("0712345678");
        prof2.setPhone("0723456789");
        prof3.setPhone("0734567890");
        prof4.setPhone("0745678901");
        prof5.setPhone("0756789012");

        prof1.setId("P001");
        prof2.setId("P002");
        prof3.setId("P003");
        prof4.setId("P004");
        prof5.setId("P005");

        professorService.addProfessor(prof1);
        professorService.addProfessor(prof2);
        professorService.addProfessor(prof3);
        professorService.addProfessor(prof4);
        professorService.addProfessor(prof5);

        System.out.println("Profesori initializati: " + professorService.getProfessors().size());
    }

    private static void initStudentGroups(StudentGroupService studentGroupService) {
        StudentGroup group1 = new StudentGroup("A1", 1, 30);
        StudentGroup group2 = new StudentGroup("A2", 1, 25);
        StudentGroup group3 = new StudentGroup("B1", 2, 20);
        StudentGroup group4 = new StudentGroup("B2", 2, 22);
        StudentGroup group5 = new StudentGroup("C1", 3, 18);

        studentGroupService.addStudentGroup(group1);
        studentGroupService.addStudentGroup(group2);
        studentGroupService.addStudentGroup(group3);
        studentGroupService.addStudentGroup(group4);
        studentGroupService.addStudentGroup(group5);

        System.out.println("Grupe de studenti: " + studentGroupService.getStudentGroups().size());
    }

    private static void initRooms(RoomService roomService) {
        Room room1 = new Room("101", 100, RoomType.AMFITEATRU);
        Room room2 = new Room("102", 30, RoomType.SALA_SEMINAR);
        Room room3 = new Room("201", 25, RoomType.LABORATOR);
        Room room4 = new Room("202", 25, RoomType.LABORATOR);
        Room room5 = new Room("301", 150, RoomType.AMFITEATRU);

        roomService.addRoom(room1);
        roomService.addRoom(room2);
        roomService.addRoom(room3);
        roomService.addRoom(room4);
        roomService.addRoom(room5);

        System.out.println("Sali: " + roomService.getRooms().size());
    }

    private static void generateSimpleTimetable(ProfessorService professorService,
            StudentGroupService studentGroupService,
            RoomService roomService,
            TimetableService timetableService) {

        StudentGroup groupA1 = studentGroupService.findGroupByName("A1");
        Professor prof1 = professorService.findProfessorById("P001");
        Professor prof2 = professorService.findProfessorById("P002");
        Professor prof3 = professorService.findProfessorById("P003");
        Room amphitheater = roomService.findRoomByType(RoomType.AMFITEATRU).get(0);
        Room lab = roomService.findRoomByType(RoomType.LABORATOR).get(0);
        Room seminar = roomService.findRoomByType(RoomType.SALA_SEMINAR).get(0);

        Course javaCourse = new Course("Programare in Java", "CS101", CourseType.CURS, prof1);
        TimeSlot mondayMorning = new TimeSlot(DayOfWeek.LUNI, LocalTime.of(8, 0), LocalTime.of(10, 0));
        timetableService.scheduleEntry(javaCourse, amphitheater, groupA1, mondayMorning);

        Course javaLab = new Course("Laborator Java", "CS101L", CourseType.LABORATOR, prof1);
        TimeSlot mondayNoon = new TimeSlot(DayOfWeek.LUNI, LocalTime.of(12, 0), LocalTime.of(14, 0));
        timetableService.scheduleEntry(javaLab, lab, groupA1, mondayNoon);

        Course mathCourse = new Course("Matematica", "MATH101", CourseType.CURS, prof2);
        TimeSlot tuesdayMorning = new TimeSlot(DayOfWeek.MARTI, LocalTime.of(10, 0), LocalTime.of(12, 0));
        timetableService.scheduleEntry(mathCourse, amphitheater, groupA1, tuesdayMorning);

        Course mathSeminar = new Course("Seminar Matematica", "MATH101S", CourseType.SEMINAR, prof2);
        TimeSlot tuesdayAfternoon = new TimeSlot(DayOfWeek.MARTI, LocalTime.of(14, 0), LocalTime.of(16, 0));
        timetableService.scheduleEntry(mathSeminar, seminar, groupA1, tuesdayAfternoon);

        // Curs online de programare avansata
        OnlineCourse onlineCourse = new OnlineCourse(
                "Programare Avansata",
                "CS201",
                CourseType.CURS,
                prof3,
                "Zoom",
                "https://zoom.us/j/1234567890"
        );
        onlineCourse.setMaxParticipants(50);
        onlineCourse.setTechnicalRequirements("Conexiune internet stabila, camera, microfon, IDE instalat");

        TimeSlot wednesdayMorning = new TimeSlot(DayOfWeek.MIERCURI, LocalTime.of(9, 0), LocalTime.of(11, 0));
        timetableService.scheduleEntry(onlineCourse, null, groupA1, wednesdayMorning);

      
        System.out.println("Orar generat cu " + timetableService.getTimetable().getEntries().size() + " intrari");
    }

    public static void displayAllData(ProfessorService professorService,
            StudentGroupService studentGroupService,
            RoomService roomService,
            TimetableService timetableService) {

        System.out.println("\n=== DATE INITIALIZATE ===\n");

        System.out.println("=== PROFESORI ===");
        professorService.displayAllProfessors();
        System.out.println();

        System.out.println("=== GRUPE DE STUDENTI ===");
        studentGroupService.displayAllGroups();
        System.out.println();

        System.out.println("=== SALI ===");
        roomService.displayAllRooms();
        System.out.println();

        System.out.println("=== ORAR ===");
        timetableService.displayTimetable();
        System.out.println();

        // Demo interogari
        // System.out.println("=== INTEROGARI ===\n");

        // Orarul unui profesor
        // Professor prof = professorService.findProfessorById("P001");
        // if (prof != null) {
        //     System.out.println("Orarul profesorului " + prof.getName() + ":");
        //     List<ScheduleEntry> profSchedule = timetableService.getProfessorSchedule(prof);
        //     for (ScheduleEntry entry : profSchedule) {
        //         System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot());
        //     }
        //     System.out.println();
        // }
        
        // Orarul unei grupe
        // StudentGroup group = studentGroupService.findGroupByName("A1");
        // if (group != null) {
        //     System.out.println("Orarul grupei " + group.getName() + ":");
        //     List<ScheduleEntry> groupSchedule = timetableService.getStudentGroupSchedule(group);
        //     for (ScheduleEntry entry : groupSchedule) {
        //         String location = entry.getRoom() != null ? entry.getRoom().getRoomNumber() : "Online";
        //         System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot() + " (Sala: " + location + ")");
        //     }
        //     System.out.println();
        // }
        // Orarul unei sali
        // Room room = roomService.findRoomByNumber("101");
        // if (room != null) {
        //     System.out.println("Programul salii " + room.getRoomNumber() + ":");
        //     List<ScheduleEntry> roomSchedule = timetableService.getRoomSchedule(room);
        //     for (ScheduleEntry entry : roomSchedule) {
        //         System.out.println("- " + entry.getCourse().getName() + " - " + entry.getTimeSlot());
        //     }
        // }
    }
}
