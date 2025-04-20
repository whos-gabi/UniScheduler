package unischedule.model.enums;

public enum RoomType {
    AMFITEATRU("Amfiteatru"),
    SALA_SEMINAR("Sala de seminar"),
    LABORATOR("Laborator");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 