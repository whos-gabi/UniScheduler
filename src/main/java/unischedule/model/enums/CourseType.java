package unischedule.model.enums;

/**
 * Tipurile de cursuri
 */
public enum CourseType {
    CURS("Curs"),
    SEMINAR("Seminar"),
    LABORATOR("Laborator");

    private final String displayName;

    CourseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 