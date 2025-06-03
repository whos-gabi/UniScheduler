package domain;

import java.util.*;

/**
 * Represents a course/subject in the university timetable system
 */
public class Course {
    private String courseId;
    private String name;
    private int credits;
    private int year;
    private String semester;
    private String type; // Changed from enum to String for simplicity
    private String examType; // Changed from enum to String for simplicity
    private int lectureHours;
    private int seminarHours;
    private int labHours;
    private int projectHours;

    public Course() {}

    // Simple constructor for basic course creation
    public Course(String courseId, String name, int credits, int year, String semester) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.type = "DS"; // Default type
        this.examType = "E"; // Default exam type
        this.lectureHours = 2; // Default values
        this.seminarHours = 1;
        this.labHours = 1;
        this.projectHours = 0;
    }

    // Advanced constructor with all fields
    public Course(String courseId, String name, int credits, int year, String semester, 
                 String type, String examType, int lectureHours, int seminarHours, 
                 int labHours, int projectHours) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.type = type;
        this.examType = examType;
        this.lectureHours = lectureHours;
        this.seminarHours = seminarHours;
        this.labHours = labHours;
        this.projectHours = projectHours;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(int lectureHours) {
        this.lectureHours = lectureHours;
    }

    public int getSeminarHours() {
        return seminarHours;
    }

    public void setSeminarHours(int seminarHours) {
        this.seminarHours = seminarHours;
    }

    public int getLabHours() {
        return labHours;
    }

    public void setLabHours(int labHours) {
        this.labHours = labHours;
    }

    public int getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(int projectHours) {
        this.projectHours = projectHours;
    }

    public int getTotalHours() {
        return lectureHours + seminarHours + labHours + projectHours;
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
                ", credits=" + credits +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", type=" + type +
                ", examType=" + examType +
                ", totalHours=" + getTotalHours() +
                '}';
    }
} 