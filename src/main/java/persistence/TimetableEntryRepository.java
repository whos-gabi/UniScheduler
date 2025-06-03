package persistence;

import domain.TimetableEntry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static persistence.util.DatabaseConnectionUtil.getDatabaseConnection;

/**
 * Repository for TimetableEntry entity
 * Handles CRUD operations for timetable entries in the university timetable system
 */
public class TimetableEntryRepository implements GenericRepository<TimetableEntry> {

    private final Map<String, TimetableEntry> storage = new HashMap<>();
    
    private static final String INSERT_TIMETABLE_ENTRY_SQL = 
        "INSERT INTO timetable_entries(entry_id, course_id, teacher_id, room_id, group_name, day_of_week, start_time, end_time, type, week_type, subgroup) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TIMETABLE_ENTRY_SQL = 
        "UPDATE timetable_entries SET course_id=?, teacher_id=?, room_id=?, group_name=?, day_of_week=?, start_time=?, end_time=?, type=?, week_type=?, subgroup=? WHERE entry_id=?";
    private static final String DELETE_TIMETABLE_ENTRY_SQL = 
        "DELETE FROM timetable_entries WHERE entry_id=?";
    private static final String FIND_TIMETABLE_ENTRY_BY_ID_SQL = 
        "SELECT * FROM timetable_entries WHERE entry_id=?";
    private static final String FIND_ALL_TIMETABLE_ENTRIES_SQL = 
        "SELECT * FROM timetable_entries";
    private static final String FIND_TIMETABLE_ENTRIES_BY_GROUP_SQL = 
        "SELECT * FROM timetable_entries WHERE group_name=?";
    private static final String FIND_TIMETABLE_ENTRIES_BY_TEACHER_SQL = 
        "SELECT * FROM timetable_entries WHERE teacher_id=?";
    private static final String FIND_TIMETABLE_ENTRIES_BY_ROOM_SQL = 
        "SELECT * FROM timetable_entries WHERE room_id=?";
    
    private final Connection connection;
    private static volatile TimetableEntryRepository instance;

    private TimetableEntryRepository() {
        this.connection = getDatabaseConnection();
    }

    public static TimetableEntryRepository getInstance() {
        if (instance == null) {
            synchronized (TimetableEntryRepository.class) {
                if (instance == null) {
                    instance = new TimetableEntryRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public TimetableEntry save(TimetableEntry entity) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_TIMETABLE_ENTRY_SQL)) {
            prepareStatement.setString(1, entity.getEntryId());
            prepareStatement.setString(2, entity.getCourseId());
            prepareStatement.setString(3, entity.getTeacherId());
            prepareStatement.setString(4, entity.getRoomId());
            prepareStatement.setString(5, entity.getGroupName());
            prepareStatement.setString(6, entity.getDayOfWeek());
            prepareStatement.setString(7, entity.getStartTime().toString());
            prepareStatement.setString(8, entity.getEndTime().toString());
            prepareStatement.setString(9, entity.getType());
            prepareStatement.setString(10, entity.getWeekType());
            prepareStatement.setString(11, entity.getSubgroup());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving timetable entry: " + e.getMessage(), e);
        }
        storage.put(entity.getEntryId(), entity);
        return entity;
    }

    @Override
    public List<TimetableEntry> findAll() {
        storage.clear(); // Clear cache before loading fresh data
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TIMETABLE_ENTRIES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all timetable entries: " + e.getMessage(), e);
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<TimetableEntry> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TIMETABLE_ENTRY_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding timetable entry by ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(TimetableEntry entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TIMETABLE_ENTRY_SQL)) {
            preparedStatement.setString(1, entity.getCourseId());
            preparedStatement.setString(2, entity.getTeacherId());
            preparedStatement.setString(3, entity.getRoomId());
            preparedStatement.setString(4, entity.getGroupName());
            preparedStatement.setString(5, entity.getDayOfWeek());
            preparedStatement.setString(6, entity.getStartTime().toString());
            preparedStatement.setString(7, entity.getEndTime().toString());
            preparedStatement.setString(8, entity.getType());
            preparedStatement.setString(9, entity.getWeekType());
            preparedStatement.setString(10, entity.getSubgroup());
            preparedStatement.setString(11, entity.getEntryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating timetable entry: " + e.getMessage(), e);
        }
        storage.put(entity.getEntryId(), entity);
    }

    @Override
    public void delete(TimetableEntry entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TIMETABLE_ENTRY_SQL)) {
            preparedStatement.setString(1, entity.getEntryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting timetable entry: " + e.getMessage(), e);
        }
        storage.remove(entity.getEntryId());
    }

    /**
     * Find timetable entries by group name
     */
    public List<TimetableEntry> findByGroupName(String groupName) {
        List<TimetableEntry> groupEntries = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TIMETABLE_ENTRIES_BY_GROUP_SQL)) {
            preparedStatement.setString(1, groupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TimetableEntry entry = extractTimetableEntryFromResultSet(resultSet);
                groupEntries.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding timetable entries by group: " + e.getMessage(), e);
        }
        return groupEntries;
    }

    /**
     * Find timetable entries by teacher ID
     */
    public List<TimetableEntry> findByTeacherId(String teacherId) {
        List<TimetableEntry> teacherEntries = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TIMETABLE_ENTRIES_BY_TEACHER_SQL)) {
            preparedStatement.setString(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TimetableEntry entry = extractTimetableEntryFromResultSet(resultSet);
                teacherEntries.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding timetable entries by teacher: " + e.getMessage(), e);
        }
        return teacherEntries;
    }

    /**
     * Find timetable entries by room ID
     */
    public List<TimetableEntry> findByRoomId(String roomId) {
        List<TimetableEntry> roomEntries = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TIMETABLE_ENTRIES_BY_ROOM_SQL)) {
            preparedStatement.setString(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TimetableEntry entry = extractTimetableEntryFromResultSet(resultSet);
                roomEntries.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding timetable entries by room: " + e.getMessage(), e);
        }
        return roomEntries;
    }

    private void extractResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            TimetableEntry entry = extractTimetableEntryFromResultSet(resultSet);
            storage.put(entry.getEntryId(), entry);
        }
    }

    private TimetableEntry extractTimetableEntryFromResultSet(ResultSet resultSet) throws SQLException {
        String entryId = resultSet.getString("entry_id");
        String courseId = resultSet.getString("course_id");
        String teacherId = resultSet.getString("teacher_id");
        String roomId = resultSet.getString("room_id");
        String groupName = resultSet.getString("group_name");
        String dayOfWeek = resultSet.getString("day_of_week");
        LocalTime startTime = LocalTime.parse(resultSet.getString("start_time"));
        LocalTime endTime = LocalTime.parse(resultSet.getString("end_time"));
        String type = resultSet.getString("type");
        String weekType = resultSet.getString("week_type");
        String subgroup = resultSet.getString("subgroup");
        
        TimetableEntry entry = new TimetableEntry(entryId, courseId, roomId, dayOfWeek, startTime, endTime);
        entry.setTeacherId(teacherId);
        entry.setGroupName(groupName);
        entry.setType(type);
        entry.setWeekType(weekType);
        entry.setSubgroup(subgroup);
        
        return entry;
    }
} 