package unischedule.model.enums;

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