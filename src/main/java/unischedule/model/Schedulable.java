package unischedule.model;

/**
 * Interfata pentru obiectele care pot fi programate in orar
 */
public interface Schedulable {
    String getName();
    boolean isAvailable(TimeSlot timeSlot);
} 