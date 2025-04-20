package unischedule.model.enums;

public enum WeekType {
    ALL("Toate Saptamanile"),
    SP("Saptamana Para"),
    SI("Saptamana Impara");

    private final String description;

    WeekType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}