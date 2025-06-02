package domain.enums;

/**
 * Enum representing different types of exams for courses
 */
public enum ExamType {
    E("Examen", "Exam"),
    V("Verificare", "Verification/Assessment");

    private final String romanianName;
    private final String englishName;

    ExamType(String romanianName, String englishName) {
        this.romanianName = romanianName;
        this.englishName = englishName;
    }

    public String getRomanianName() {
        return romanianName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public static ExamType fromString(String type) {
        if (type == null) return null;
        
        switch (type.toUpperCase()) {
            case "E": return E;
            case "V": return V;
            default: 
                throw new IllegalArgumentException("Unknown exam type: " + type);
        }
    }

    @Override
    public String toString() {
        return name() + " (" + romanianName + ")";
    }
} 