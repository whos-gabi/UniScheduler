package controller;

import domain.TimetableEntry;
import service.AuditService;
import service.TimetableService;
import service.AutomaticScheduleGenerator;
import java.util.List;

/**
 * Controller responsible for schedule management and export operations
 * Handles timetable viewing and export functionality
 */
public class ScheduleController {
    
    private final TimetableService timetableService = new TimetableService();
    private final AuditService auditService = AuditService.getInstance();
    
    // Timetable viewing operations
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
    
    public void listAllTimetableEntries() {
        System.out.println("All timetable entries:");
        List<TimetableEntry> entries = timetableService.getAllTimetableEntries();
        if (entries.isEmpty()) {
            System.out.println("No timetable entries found!");
        } else {
            entries.forEach(System.out::println);
        }
        auditService.logAction("List All Timetable Entries");
    }
    
    // Export operations
    public void exportTeacherScheduleAsJson(String teacherId) {
        try {
            List<TimetableEntry> entries = timetableService.getTimetableForTeacher(teacherId);
            String jsonFileName = "output/teacher_" + teacherId + "_schedule.json";
            String imageFileName = "output/teacher_" + teacherId + "_timetable.png";
            
            exportScheduleAsJson(entries, jsonFileName, imageFileName, teacherId, "TEACHER");
            auditService.logAction("Export Teacher Schedule JSON");
        } catch (Exception e) {
            System.out.println("Error exporting teacher schedule: " + e.getMessage());
        }
    }
    
    public void exportGroupScheduleAsJson(String groupName) {
        try {
            List<TimetableEntry> entries = timetableService.getTimetableForGroup(groupName);
            String jsonFileName = "output/group_" + groupName + "_schedule.json";
            String imageFileName = "output/group_" + groupName + "_timetable.png";
            
            exportScheduleAsJson(entries, jsonFileName, imageFileName, groupName, "STUDENT_GROUP");
            auditService.logAction("Export Group Schedule JSON");
        } catch (Exception e) {
            System.out.println("Error exporting group schedule: " + e.getMessage());
        }
    }
    
    public void exportRoomScheduleAsJson(String roomId) {
        try {
            List<TimetableEntry> entries = timetableService.getTimetableForRoom(roomId);
            String jsonFileName = "output/room_" + roomId + "_schedule.json";
            String imageFileName = "output/room_" + roomId + "_timetable.png";
            
            exportScheduleAsJson(entries, jsonFileName, imageFileName, roomId, "ROOM");
            auditService.logAction("Export Room Schedule JSON");
        } catch (Exception e) {
            System.out.println("Error exporting room schedule: " + e.getMessage());
        }
    }
    
    private void exportScheduleAsJson(List<TimetableEntry> entries, String jsonFileName, String imageFileName, String name, String type) {
        try {
            // Create output directory if it doesn't exist
            java.io.File outputDir = new java.io.File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            
            // Write JSON file
            try (java.io.FileWriter writer = new java.io.FileWriter(jsonFileName)) {
                writer.write("{\n");
                writer.write("  \"name\": \"" + name + "\",\n");
                writer.write("  \"type\": \"" + type + "\",\n");
                writer.write("  \"entries\": [\n");
                
                for (int i = 0; i < entries.size(); i++) {
                    TimetableEntry entry = entries.get(i);
                    writer.write("    {\n");
                    writer.write("      \"day\": \"" + entry.getDayOfWeek() + "\",\n");
                    writer.write("      \"time\": \"" + entry.getTimeSlot() + "\",\n");
                    writer.write("      \"course\": \"" + entry.getCourseId() + "\",\n");
                    if ("TEACHER".equals(type)) {
                        writer.write("      \"room\": \"" + entry.getRoomId() + "\",\n");
                    } else if ("STUDENT_GROUP".equals(type)) {
                        writer.write("      \"teacher\": \"" + entry.getTeacherId() + "\",\n");
                        writer.write("      \"room\": \"" + entry.getRoomId() + "\",\n");
                    } else if ("ROOM".equals(type)) {
                        writer.write("      \"teacher\": \"" + entry.getTeacherId() + "\",\n");
                        writer.write("      \"group\": \"" + entry.getGroupName() + "\",\n");
                    }
                    writer.write("      \"type\": \"" + entry.getType() + "\"\n");
                    writer.write("    }" + (i < entries.size() - 1 ? "," : "") + "\n");
                }
                
                writer.write("  ]\n");
                writer.write("}\n");
            }
            
            System.out.println("JSON file created: " + jsonFileName);
            
            // Call Python script to generate image
            try {
                ProcessBuilder pb = new ProcessBuilder("python3", "timetable_image_generator.py", jsonFileName, imageFileName);
                Process process = pb.start();
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    System.out.println("Timetable image generated: " + imageFileName);
                    System.out.println("Full path: " + new java.io.File(imageFileName).getAbsolutePath());
                } else {
                    System.out.println("Error generating image. Make sure Python script and matplotlib are available.");
                }
            } catch (Exception e) {
                System.out.println("Could not run Python script: " + e.getMessage());
                System.out.println("You can manually run: python3 timetable_image_generator.py " + jsonFileName + " " + imageFileName);
            }
        } catch (Exception e) {
            System.out.println("Error creating JSON file: " + e.getMessage());
        }
    }
    
    // Automatic schedule generation
    public void generateAutomaticSchedule() {
        System.out.println("Starting automatic schedule generation...");
        
        try {
            // Clear existing timetable entries
            clearAllTimetableEntries();
            
            // Generate new schedule
            AutomaticScheduleGenerator generator = new AutomaticScheduleGenerator(timetableService);
            List<TimetableEntry> generatedEntries = generator.generateCompleteSchedule();
            
            // Save all generated entries
            for (TimetableEntry entry : generatedEntries) {
                try {
                    timetableService.addTimetableEntry(entry);
                } catch (Exception e) {
                    System.out.println("Warning: Failed to save entry " + entry.getEntryId() + ": " + e.getMessage());
                }
            }
            
            System.out.println("Generated " + generatedEntries.size() + " timetable entries");
            auditService.logAction("Generate Automatic Schedule");
            
        } catch (Exception e) {
            System.out.println("Error in automatic schedule generation: " + e.getMessage());
            throw e;
        }
    }
    
    private void clearAllTimetableEntries() {
        try {
            timetableService.clearAllTimetableEntries();
            System.out.println("Cleared existing timetable entries");
        } catch (Exception e) {
            System.out.println("Warning: Could not clear existing entries: " + e.getMessage());
        }
    }
} 