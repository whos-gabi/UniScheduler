package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Audit service for logging operations to CSV file
 * Required for Etapa II - tracks all system operations
 * Format: nume_actiune, timestamp
 */
public class AuditService {
    
    private static final String AUDIT_FILE_NAME = "audit_log.csv";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static volatile AuditService instance;
    private final String auditFilePath;
    
    private AuditService() {
        // Use project root directory for audit file
        String projectRoot = System.getProperty("user.dir");
        this.auditFilePath = Paths.get(projectRoot, AUDIT_FILE_NAME).toString();
        
        // Print audit file location for debugging
        System.out.println("Audit log file location: " + this.auditFilePath);
        
        // Initialize CSV file with headers if it doesn't exist
        initializeAuditFile();
    }
    
    public static AuditService getInstance() {
        if (instance == null) {
            synchronized (AuditService.class) {
                if (instance == null) {
                    instance = new AuditService();
                }
            }
        }
        return instance;
    }
    
    /**
     * Log an action to the CSV audit file
     * @param menuOptionName The name of the menu option performed (e.g. "Add Student")
     */
    public void logAction(String menuOptionName) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(auditFilePath, true))) {
            // Write: nume_actiune, timestamp
            writer.printf("%s,%s%n", menuOptionName, timestamp);
            
            // Debug output
            System.out.println("Audit logged: " + menuOptionName + " at " + timestamp);
            
        } catch (IOException e) {
            System.err.println("Error writing to audit log: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize the audit CSV file with headers
     */
    private void initializeAuditFile() {
        try {
            Path auditPath = Paths.get(auditFilePath);
            
            // Check if file exists
            if (!Files.exists(auditPath)) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(auditFilePath))) {
                    // Write CSV headers
                    writer.println("nume_actiune,timestamp");
                    System.out.println("Created new audit log file: " + auditFilePath);
                }
            } else {
                System.out.println("Using existing audit log file: " + auditFilePath);
            }
        } catch (IOException e) {
            System.err.println("Error initializing audit log file: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 