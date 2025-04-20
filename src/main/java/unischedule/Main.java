package unischedule;

import java.util.Scanner;

import unischedule.service.ProfessorService;
import unischedule.service.RoomService;
import unischedule.service.StudentGroupService;
import unischedule.service.TimetableService;
import unischedule.util.DataInitializer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Sistem de Orar Universitar");
        
        // init services
        ProfessorService professorService = new ProfessorService();
        StudentGroupService studentGroupService = new StudentGroupService();
        RoomService roomService = new RoomService();
        TimetableService timetableService = new TimetableService();
        
        // init test data
        DataInitializer.initData(professorService, studentGroupService, roomService, timetableService);
        
        // display test data bulk
        DataInitializer.displayAllData(professorService, studentGroupService, roomService, timetableService);
        
        // CLI start here
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();
            
            switch (option) {
                case "1":
                    displayProfessors(professorService);
                    break;
                case "2":
                    displayStudentGroups(studentGroupService);
                    break;
                case "3":
                    displayRooms(roomService);
                    break;
                case "4":
                    displayTimetable(timetableService);
                    break;
                case "0":
                    System.out.println("La revedere!");
                    running = false;
                    break;
                default:
                    System.out.println("Optiune invalida. Va rugam selectati din nou.");
            }
        }
        
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n=== MENIU ===");
        System.out.println("1. Afiseaza profesori");
        System.out.println("2. Afiseaza grupe de studenti");
        System.out.println("3. Afiseaza sali");
        System.out.println("4. Afiseaza orar");
        System.out.println("0. Iesire");
        System.out.print("Selectati o optiune: ");
    }
    
    private static void displayProfessors(ProfessorService professorService) {
        System.out.println("\n=== PROFESORI ===");
        professorService.displayAllProfessors();
    }
    
    private static void displayStudentGroups(StudentGroupService studentGroupService) {
        System.out.println("\n=== GRUPE DE STUDENTI ===");
        studentGroupService.displayAllGroups();
    }
    
    private static void displayRooms(RoomService roomService) {
        System.out.println("\n=== SALI ===");
        roomService.displayAllRooms();
    }
    
    private static void displayTimetable(TimetableService timetableService) {
        System.out.println("\n=== ORAR ===");
        timetableService.displayTimetable();
    }
}