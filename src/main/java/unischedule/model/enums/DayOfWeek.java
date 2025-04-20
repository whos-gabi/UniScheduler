package unischedule.model.enums;

/**
 * Zilele saptamanii
 */
public enum DayOfWeek {
    LUNI("Luni"),
    MARTI("Marti"),
    MIERCURI("Miercuri"),
    JOI("Joi"),
    VINERI("Vineri");

    private final String displayName;

    DayOfWeek(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 