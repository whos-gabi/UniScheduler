package java.unischedule.model.enums;

public enum RoomType {
    // amphitheater, auditorium, computerLab
    AMPHITHEATER("Amphitheater"),
    AUDITORIUM("Auditorium"),
    COMPUTER_LAB("Computer Lab");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
