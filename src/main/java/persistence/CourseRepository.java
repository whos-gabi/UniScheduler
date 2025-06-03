package persistence;

import domain.Course;
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
 * Repository for Course entity
 * Handles CRUD operations for courses in the university timetable system
 */
public class CourseRepository implements GenericRepository<Course> {

    private final Map<String, Course> storage = new HashMap<>();
    
    private static final String INSERT_COURSE_SQL = 
        "INSERT INTO courses(course_id, name, credits, year, semester, course_type, exam_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COURSE_SQL = 
        "UPDATE courses SET name=?, credits=?, year=?, semester=?, course_type=?, exam_type=? WHERE course_id=?";
    private static final String DELETE_COURSE_SQL = 
        "DELETE FROM courses WHERE course_id=?";
    private static final String FIND_COURSE_BY_ID_SQL = 
        "SELECT * FROM courses WHERE course_id=?";
    private static final String FIND_ALL_COURSES_SQL = 
        "SELECT * FROM courses";
    
    private final Connection connection;
    private static volatile CourseRepository instance;

    private CourseRepository() {
        this.connection = getDatabaseConnection();
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
            synchronized (CourseRepository.class) {
                if (instance == null) {
                    instance = new CourseRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Course save(Course entity) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_COURSE_SQL)) {
            prepareStatement.setString(1, entity.getCourseId());
            prepareStatement.setString(2, entity.getName());
            prepareStatement.setInt(3, entity.getCredits());
            prepareStatement.setInt(4, entity.getYear());
            prepareStatement.setString(5, entity.getSemester());
            prepareStatement.setString(6, entity.getType());
            prepareStatement.setString(7, entity.getExamType());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving course: " + e.getMessage(), e);
        }
        storage.put(entity.getCourseId(), entity);
        return entity;
    }

    @Override
    public List<Course> findAll() {
        storage.clear(); // Clear cache before loading fresh data
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COURSES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all courses: " + e.getMessage(), e);
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Course> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_COURSE_BY_ID_SQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            extractResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding course by ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(Course entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE_SQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getCredits());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setString(4, entity.getSemester());
            preparedStatement.setString(5, entity.getType());
            preparedStatement.setString(6, entity.getExamType());
            preparedStatement.setString(7, entity.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course: " + e.getMessage(), e);
        }
        storage.put(entity.getCourseId(), entity);
    }

    @Override
    public void delete(Course entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_SQL)) {
            preparedStatement.setString(1, entity.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting course: " + e.getMessage(), e);
        }
        storage.remove(entity.getCourseId());
    }

    public List<Course> findByYear(int year) {
        return findAll().stream()
                .filter(course -> course.getYear() == year)
                .collect(java.util.stream.Collectors.toList());
    }

    private void extractResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Course course = extractCourseFromResultSet(resultSet);
            storage.put(course.getCourseId(), course);
        }
    }

    private Course extractCourseFromResultSet(ResultSet resultSet) throws SQLException {
        String courseId = resultSet.getString("course_id");
        String courseName = resultSet.getString("name");
        int credits = resultSet.getInt("credits");
        int year = resultSet.getInt("year");
        String semester = resultSet.getString("semester");
        String courseType = resultSet.getString("course_type");
        String examType = resultSet.getString("exam_type");
        
        return new Course(courseId, courseName, credits, year, semester, courseType, examType, 2, 1, 1, 0);
    }
} 