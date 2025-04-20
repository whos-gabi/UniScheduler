package unischedule.model.enums;


public enum CourseType {
    CURS("C", "Curs"),
    SEMINAR("S", "Seminar"),
    LABORATOR("L", "Laborator"),
    PROIECT("P", "Proiect");

    private final String code;
    private final String displayName;

    CourseType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static CourseType fromCode(String code) {
        for (CourseType type : CourseType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Cod invalid: " + code);
    }
} 