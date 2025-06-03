package persistence;

import domain.Teacher;
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
 * Repository for Teacher entity
 * Handles CRUD operations for teachers in the university timetable system
 */
public class TeacherRepository implements GenericRepository<Teacher> {

    private final Map<String, Teacher> storage = new HashMap<>();
    
    private static final String INSERT_TEACHER_SQL = 
        "INSERT INTO teachers(teacher_id, first_name, last_name, email, department, title) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TEACHER_SQL = 
        "UPDATE teachers SET first_name=?, last_name=?, email=?, department=?, title=? WHERE teacher_id=?";
    private static final String DELETE_TEACHER_SQL = 
        "DELETE FROM teachers WHERE teacher_id=?";
    private static final String FIND_TEACHER_BY_ID_SQL = 
        "SELECT * FROM teachers WHERE teacher_id=?";
    private static final String FIND_ALL_TEACHERS_SQL = 
        "SELECT * FROM teachers";
    
    private final Connection connection;
    private static volatile TeacherRepository instance;

    private TeacherRepository() {
        this.connection = getDatabaseConnection();
    }

    public static TeacherRepository getInstance() {
        if (instance == null) {
            synchronized (TeacherRepository.class) {
                if (instance == null) {
                    instance = new TeacherRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Teacher save(Teacher entity) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_TEACHER_SQL)) {
            prepareStatement.setString(1, entity.getTeacherId());
            prepareStatement.setString(2, entity.getFirstName());
            prepareStatement.setString(3, entity.getLastName());
            prepareStatement.setString(4, entity.getEmail());
            prepareStatement.setString(5, entity.getDepartment());
            prepareStatement.setString(6, entity.getTitle());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving teacher: " + e.getMessage(), e);
        }
        storage.put(entity.getTeacherId(), entity);
        return entity;
    }

    @Override
    public List<Teacher> findAll() {
        storage.clear(); // Clear cache before loading fresh data
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TEACHERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all teachers: " + e.getMessage(), e);
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Teacher> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TEACHER_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding teacher by ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(Teacher entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEACHER_SQL)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getDepartment());
            preparedStatement.setString(5, entity.getTitle());
            preparedStatement.setString(6, entity.getTeacherId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating teacher: " + e.getMessage(), e);
        }
        storage.put(entity.getTeacherId(), entity);
    }

    @Override
    public void delete(Teacher entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEACHER_SQL)) {
            preparedStatement.setString(1, entity.getTeacherId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting teacher: " + e.getMessage(), e);
        }
        storage.remove(entity.getTeacherId());
    }

    private void extractResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Teacher teacher = extractTeacherFromResultSet(resultSet);
            storage.put(teacher.getTeacherId(), teacher);
        }
    }

    private Teacher extractTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        String teacherId = resultSet.getString("teacher_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String department = resultSet.getString("department");
        String title = resultSet.getString("title");
        
        return new Teacher(teacherId, firstName, lastName, email, department, title);
    }
} 