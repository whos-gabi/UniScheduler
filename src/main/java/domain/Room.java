package domain;

import java.util.Objects;

/**
 * Represents a classroom/room in the university timetable system
 */
public class Room {
    private String roomId;
    private String name;
    private String building;
    private int capacity;
    private String type; // Lecture Hall, Laboratory, Seminar Room, etc.
    private String equipment; // Projector, Computers, etc.

    public Room() {}

    public Room(String roomId, String name, String building, int capacity, String type) {
        this.roomId = roomId;
        this.name = name;
        this.building = building;
        this.capacity = capacity;
        this.type = type;
    }

    // Getters and Setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getFullName() {
        return building + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", equipment='" + equipment + '\'' +
                '}';
    }
} 