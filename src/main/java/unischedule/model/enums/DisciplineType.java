package unischedule.model.enums;

/**
 * Represents the discipline type in the curriculum
 * DF – Discipline fundamentale (Fundamental disciplines)
 * DC – Discipline complementare (Complementary disciplines)
 * DS – Discipline de specializare (Specialization disciplines)
 */
public enum DisciplineType {
    DF("Discipline fundamentale"),
    DC("Discipline complementare"),
    DS("Discipline de specializare");

    private final String description;

    DisciplineType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 