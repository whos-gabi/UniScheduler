package persistence;

import domain.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static persistence.util.DatabaseConnectionUtil.getDatabaseConnection;

/**
 * Repository for Room entity
 * Handles CRUD operations for rooms in the university timetable system
 */
public class RoomRepository implements GenericRepository<Room> {

    private final Map<String, Room> storage = new HashMap<>();
    
    private static final String INSERT_ROOM_SQL = 
        "INSERT INTO rooms(room_id, name, building, capacity, type) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ROOM_SQL = 
        "UPDATE rooms SET name=?, building=?, capacity=?, type=? WHERE room_id=?";
    private static final String DELETE_ROOM_SQL = 
        "DELETE FROM rooms WHERE room_id=?";
    private static final String FIND_ROOM_BY_ID_SQL = 
        "SELECT * FROM rooms WHERE room_id=?";
    private static final String FIND_ALL_ROOMS_SQL = 
        "SELECT * FROM rooms";
    
    private final Connection connection;
    private static volatile RoomRepository instance;

    private RoomRepository() {
        this.connection = getDatabaseConnection();
    }

    public static RoomRepository getInstance() {
        if (instance == null) {
            synchronized (RoomRepository.class) {
                if (instance == null) {
                    instance = new RoomRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Room save(Room entity) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_ROOM_SQL)) {
            prepareStatement.setString(1, entity.getRoomId());
            prepareStatement.setString(2, entity.getName());
            prepareStatement.setString(3, entity.getBuilding());
            prepareStatement.setInt(4, entity.getCapacity());
            prepareStatement.setString(5, entity.getType());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving room: " + e.getMessage(), e);
        }
        storage.put(entity.getRoomId(), entity);
        return entity;
    }

    @Override
    public List<Room> findAll() {
        storage.clear(); // Clear cache before loading fresh data
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROOMS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all rooms: " + e.getMessage(), e);
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Room> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding room by ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(Room entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROOM_SQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getBuilding());
            preparedStatement.setInt(3, entity.getCapacity());
            preparedStatement.setString(4, entity.getType());
            preparedStatement.setString(5, entity.getRoomId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating room: " + e.getMessage(), e);
        }
        storage.put(entity.getRoomId(), entity);
    }

    @Override
    public void delete(Room entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_SQL)) {
            preparedStatement.setString(1, entity.getRoomId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting room: " + e.getMessage(), e);
        }
        storage.remove(entity.getRoomId());
    }

    private void extractResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Room room = extractRoomFromResultSet(resultSet);
            storage.put(room.getRoomId(), room);
        }
    }

    private Room extractRoomFromResultSet(ResultSet resultSet) throws SQLException {
        String roomId = resultSet.getString("room_id");
        String roomName = resultSet.getString("name");
        String building = resultSet.getString("building");
        int capacity = resultSet.getInt("capacity");
        String roomType = resultSet.getString("type");
        
        return new Room(roomId, roomName, building, capacity, roomType);
    }
} 