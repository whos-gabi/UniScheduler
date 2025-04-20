package unischedule.model.enums;

public enum RoomType {
    // amphitheater, auditorium, computerLab
    AMPHITHEATER("Amfiteatru"),
    AUDITORIUM("Auditoriu"),
    LAB("Laborator");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
