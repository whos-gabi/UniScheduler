package unischedule.model.enums;

/**
 * E - Examen (Exam)
 * V - Verificare (Verbal/Verification)
 */
public enum EvaluationForm {
    E("Examen"),
    V("Verificare");

    private final String description;

    EvaluationForm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 