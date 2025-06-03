package persistence;

import domain.Student;
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
 * Repository for Student entity
 * Handles CRUD operations for students in the university timetable system
 */
public class StudentRepository implements GenericRepository<Student> {

    private final Map<String, Student> storage = new HashMap<>();
    
    private static final String INSERT_STUDENT_SQL = 
        "INSERT INTO students(student_id, first_name, last_name, email, year, group_name) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STUDENT_SQL = 
        "UPDATE students SET first_name=?, last_name=?, email=?, year=?, group_name=? WHERE student_id=?";
    private static final String DELETE_STUDENT_SQL = 
        "DELETE FROM students WHERE student_id=?";
    private static final String FIND_STUDENT_BY_ID_SQL = 
        "SELECT student_id, first_name, last_name, email, year, group_name FROM students WHERE student_id=?";
    private static final String FIND_ALL_STUDENTS_SQL = 
        "SELECT * FROM students";
    private static final String FIND_STUDENTS_BY_GROUP_SQL = 
        "SELECT * FROM students WHERE group_name=?";
    
    private final Connection connection;
    private static volatile StudentRepository instance;

    private StudentRepository() {
        this.connection = getDatabaseConnection();
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            synchronized (StudentRepository.class) {
                if (instance == null) {
                    instance = new StudentRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Student save(Student entity) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            prepareStatement.setString(1, entity.getStudentId());
            prepareStatement.setString(2, entity.getFirstName());
            prepareStatement.setString(3, entity.getLastName());
            prepareStatement.setString(4, entity.getEmail());
            prepareStatement.setInt(5, entity.getYear());
            prepareStatement.setString(6, entity.getGroupName());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving student: " + e.getMessage(), e);
        }
        storage.put(entity.getStudentId(), entity);
        return entity;
    }

    @Override
    public List<Student> findAll() {
        storage.clear(); // Clear cache before loading fresh data
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_STUDENTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all students: " + e.getMessage(), e);
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Student> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENT_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student by ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(Student entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setInt(4, entity.getYear());
            preparedStatement.setString(5, entity.getGroupName());
            preparedStatement.setString(6, entity.getStudentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student: " + e.getMessage(), e);
        }
        storage.put(entity.getStudentId(), entity);
    }

    @Override
    public void delete(Student entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_SQL)) {
            preparedStatement.setString(1, entity.getStudentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
        storage.remove(entity.getStudentId());
    }

    /**
     * Find students by group name
     */
    public List<Student> findByGroupName(String groupName) {
        List<Student> groupStudents = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENTS_BY_GROUP_SQL)) {
            preparedStatement.setString(1, groupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = extractStudentFromResultSet(resultSet);
                groupStudents.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding students by group: " + e.getMessage(), e);
        }
        return groupStudents;
    }

    private void extractResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Student student = extractStudentFromResultSet(resultSet);
            storage.put(student.getStudentId(), student);
        }
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws SQLException {
        String studentId = resultSet.getString("student_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        int year = resultSet.getInt("year");
        String groupName = resultSet.getString("group_name");
        
        return new Student(studentId, firstName, lastName, email, year, groupName);
    }
} 