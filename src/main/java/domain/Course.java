package domain;

import domain.enums.CourseType;
import domain.enums.ExamType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a course/subject in the university timetable system
 * Loads course data from curriculum.json at initialization
 */
public class Course {
    private String courseId;
    private String name;
    private CourseType type;
    private int courseHours; // C - courses/lectures
    private int seminaryHours; // S - seminaries
    private int laboratoryHours; // L - laboratories
    private int projectHours; // P - projects (if exists)
    private ExamType examType;
    private int credits;
    private int year;
    private int semester;
    
    // Static collection to hold all courses loaded from JSON
    private static List<Course> allCourses = new ArrayList<>();
    private static boolean coursesLoaded = false;

    public Course() {}

    public Course(String name, CourseType type, int courseHours, int seminaryHours, 
                 int laboratoryHours, int projectHours, ExamType examType, int credits, 
                 int year, int semester) {
        this.courseId = generateCourseId(name, year, semester);
        this.name = name;
        this.type = type;
        this.courseHours = courseHours;
        this.seminaryHours = seminaryHours;
        this.laboratoryHours = laboratoryHours;
        this.projectHours = projectHours;
        this.examType = examType;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
    }

    // Static method to load all courses from JSON
    public static void loadCoursesFromJson() {
        if (coursesLoaded) return;
        
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/data/curriculum.json");
            JsonObject curriculum = gson.fromJson(reader, JsonObject.class);
            
            // Parse each year
            for (int year = 1; year <= 3; year++) {
                String yearKey = "year_" + year;
                if (curriculum.has(yearKey)) {
                    JsonObject yearData = curriculum.getAsJsonObject(yearKey);
                    
                    // Parse each semester
                    for (int semester = 1; semester <= 2; semester++) {
                        String semesterKey = "semester_" + semester;
                        if (yearData.has(semesterKey)) {
                            JsonArray courses = yearData.getAsJsonArray(semesterKey);
                            
                            for (JsonElement courseElement : courses) {
                                JsonObject courseJson = courseElement.getAsJsonObject();
                                Course course = parseCourseFromJson(courseJson, year, semester);
                                allCourses.add(course);
                            }
                        }
                    }
                }
            }
            
            coursesLoaded = true;
            reader.close();
            
        } catch (IOException e) {
            System.err.println("Error loading curriculum.json: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error parsing curriculum.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Course parseCourseFromJson(JsonObject courseJson, int year, int semester) {
        String name = courseJson.get("name").getAsString();
        CourseType type = CourseType.fromString(courseJson.get("type").getAsString());
        ExamType examType = ExamType.fromString(courseJson.get("exam").getAsString());
        int credits = courseJson.get("credits").getAsInt();
        
        // Parse hours object
        JsonObject hours = courseJson.getAsJsonObject("hours");
        int courseHours = hours.has("C") ? hours.get("C").getAsInt() : 0;
        int seminaryHours = hours.has("S") ? hours.get("S").getAsInt() : 0;
        int laboratoryHours = hours.has("L") ? hours.get("L").getAsInt() : 0;
        int projectHours = hours.has("P") ? hours.get("P").getAsInt() : 0;
        
        return new Course(name, type, courseHours, seminaryHours, laboratoryHours, 
                         projectHours, examType, credits, year, semester);
    }

    private String generateCourseId(String name, int year, int semester) {
        // Generate a simple course ID based on name, year, and semester
        String cleanName = name.replaceAll("[^a-zA-Z0-9]", "").substring(0, Math.min(name.length(), 10));
        return cleanName.toUpperCase() + "_Y" + year + "S" + semester;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public int getSeminaryHours() {
        return seminaryHours;
    }

    public void setSeminaryHours(int seminaryHours) {
        this.seminaryHours = seminaryHours;
    }

    public int getLaboratoryHours() {
        return laboratoryHours;
    }

    public void setLaboratoryHours(int laboratoryHours) {
        this.laboratoryHours = laboratoryHours;
    }

    public int getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(int projectHours) {
        this.projectHours = projectHours;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    // Computed properties
    public int getTotalHours() {
        return courseHours + seminaryHours + laboratoryHours + projectHours;
    }

    public String getDepartment() {
        // For now, determine department based on course type and year
        if (type == CourseType.DF) {
            return "Computer Science";
        } else if (type == CourseType.DS) {
            return "Software Engineering";
        } else {
            return "General Education";
        }
    }

    // Legacy getters for compatibility with existing code
    public String getDescription() {
        return type.getRomanianName() + " - " + examType.getRomanianName();
    }

    public int getLectureHours() {
        return courseHours;
    }

    public void setLectureHours(int lectureHours) {
        this.courseHours = lectureHours;
    }

    public int getSeminarHours() {
        return seminaryHours;
    }

    public void setSeminarHours(int seminarHours) {
        this.seminaryHours = seminarHours;
    }

    public int getLabHours() {
        return laboratoryHours;
    }

    public void setLabHours(int labHours) {
        this.laboratoryHours = labHours;
    }

    // Static methods to access loaded courses
    public static List<Course> getAllCourses() {
        if (!coursesLoaded) {
            loadCoursesFromJson();
        }
        return new ArrayList<>(allCourses);
    }

    public static List<Course> getCoursesByYear(int year) {
        return getAllCourses().stream()
                .filter(c -> c.getYear() == year)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static List<Course> getCoursesBySemester(int year, int semester) {
        return getAllCourses().stream()
                .filter(c -> c.getYear() == year && c.getSemester() == semester)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static List<Course> getCoursesByType(CourseType type) {
        return getAllCourses().stream()
                .filter(c -> c.getType() == type)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", courseHours=" + courseHours +
                ", seminaryHours=" + seminaryHours +
                ", laboratoryHours=" + laboratoryHours +
                ", projectHours=" + projectHours +
                ", examType=" + examType +
                ", credits=" + credits +
                ", year=" + year +
                ", semester=" + semester +
                '}';
    }
} 