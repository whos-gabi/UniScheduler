package persistence.util;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLite Database Connection Utility for University Timetable System
 * Implements singleton pattern for database connections
 */
public class DatabaseConnectionUtil {

    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String DB_NAME = "university_timetable.db";
    private static volatile DatabaseConnectionUtil instance;
    private Connection connection;
    private String dbUrl;

    public static Connection getDatabaseConnection() {
        if (instance == null) {
            synchronized (DatabaseConnectionUtil.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionUtil();
                }
            }
        }
        return instance.connection;
    }

    private DatabaseConnectionUtil() {
        try {
            // Get project root directory (where pom.xml is located)
            String projectRoot = System.getProperty("user.dir");
            String dbPath = Paths.get(projectRoot, DB_NAME).toString();
            this.dbUrl = "jdbc:sqlite:" + dbPath;
            
            System.out.println("Database will be created/used at: " + dbPath);
            
            Class.forName(SQLITE_JDBC_DRIVER);
            connection = DriverManager.getConnection(dbUrl);
            
            // Enable auto-commit for SQLite
            connection.setAutoCommit(true);
            
            initializeTables();
            System.out.println("SQLite connection established successfully");
            
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite driver not available: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Could not connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize database tables if they don't exist
     */
    private void initializeTables() {
        try (Statement statement = connection.createStatement()) {
            
            // Students table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    student_id TEXT PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    email TEXT,
                    year INTEGER NOT NULL,
                    major TEXT NOT NULL,
                    group_name TEXT NOT NULL
                )
            """);

            // Teachers table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS teachers (
                    teacher_id TEXT PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    email TEXT,
                    department TEXT NOT NULL,
                    title TEXT NOT NULL
                )
            """);

            // Courses table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS courses (
                    course_id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    description TEXT,
                    credits INTEGER NOT NULL,
                    department TEXT NOT NULL,
                    year INTEGER NOT NULL,
                    semester TEXT NOT NULL,
                    lecture_hours INTEGER DEFAULT 0,
                    seminar_hours INTEGER DEFAULT 0,
                    lab_hours INTEGER DEFAULT 0,
                    project_hours INTEGER DEFAULT 0
                )
            """);

            // Rooms table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS rooms (
                    room_id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    building TEXT NOT NULL,
                    capacity INTEGER NOT NULL,
                    type TEXT NOT NULL,
                    equipment TEXT
                )
            """);

            // Timetable entries table
            statement.execute("""
                CREATE TABLE IF NOT EXISTS timetable_entries (
                    entry_id TEXT PRIMARY KEY,
                    course_id TEXT NOT NULL,
                    teacher_id TEXT NOT NULL,
                    room_id TEXT NOT NULL,
                    group_name TEXT NOT NULL,
                    day_of_week TEXT NOT NULL,
                    start_time TEXT NOT NULL,
                    end_time TEXT NOT NULL,
                    type TEXT NOT NULL,
                    week_type TEXT NOT NULL,
                    subgroup TEXT,
                    FOREIGN KEY (course_id) REFERENCES courses(course_id),
                    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
                    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
                )
            """);

            // Teacher subjects table (many-to-many relationship)
            statement.execute("""
                CREATE TABLE IF NOT EXISTS teacher_subjects (
                    teacher_id TEXT NOT NULL,
                    subject TEXT NOT NULL,
                    PRIMARY KEY (teacher_id, subject),
                    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id)
                )
            """);

            // Audit table for tracking operations
            statement.execute("""
                CREATE TABLE IF NOT EXISTS audit_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    action_name TEXT NOT NULL,
                    timestamp TEXT NOT NULL,
                    user_type TEXT,
                    details TEXT
                )
            """);

            System.out.println("Database tables initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
} 