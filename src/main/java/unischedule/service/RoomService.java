package unischedule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unischedule.model.Room;
import unischedule.model.enums.RoomType;

/**
 * Serviciu pentru gestionarea salilor
 */
public class RoomService {
    private List<Room> rooms;
    
    public RoomService() {
        this.rooms = new ArrayList<>();
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public void removeRoom(Room room) {
        rooms.remove(room);
    }
    
    public Room findRoomByNumber(String roomNumber) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);
    }
    
    public List<Room> findRoomsByType(RoomType type) {
        return rooms.stream()
                .filter(r -> r.getType().equals(type))
                .collect(Collectors.toList());
    }
    
    public List<Room> findRoomsByMinCapacity(int minCapacity) {
        return rooms.stream()
                .filter(r -> r.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }
    
    public void displayAllRooms() {
        System.out.println("Lista salilor:");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
} 