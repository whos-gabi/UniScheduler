package domain.enums;

/**
 * Enum representing different types of courses in the university curriculum
 */
public enum CourseType {
    DF("Disciplină Fundamentală", "Fundamental Subject"),
    DS("Disciplină de Specializare", "Specialization Subject"), 
    DC("Disciplină Complementară", "Complementary Subject");

    private final String romanianName;
    private final String englishName;

    CourseType(String romanianName, String englishName) {
        this.romanianName = romanianName;
        this.englishName = englishName;
    }

    public String getRomanianName() {
        return romanianName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public static CourseType fromString(String type) {
        if (type == null) return null;
        
        switch (type.toUpperCase()) {
            case "DF": return DF;
            case "DS": return DS;
            case "DC": return DC;
            default: 
                throw new IllegalArgumentException("Unknown course type: " + type);
        }
    }

    @Override
    public String toString() {
        return name() + " (" + romanianName + ")";
    }
} 