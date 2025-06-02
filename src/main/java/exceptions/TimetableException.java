package exceptions;

/**
 * Custom exception for the University Timetable System
 */
public class TimetableException extends Exception {
    
    public TimetableException(String message) {
        super(message);
    }
    
    public TimetableException(String message, Throwable cause) {
        super(message, cause);
    }
} 