package domain;

import java.util.Objects;

/**
 * Represents a course/subject in the university timetable system
 */
public class Course {
    private String courseId;
    private String name;
    private String description;
    private int credits;
    private String department;
    private int year; // Which year students take this course
    private String semester; // Fall, Spring, etc.
    private int lectureHours;
    private int seminarHours;
    private int labHours;
    private int projectHours;

    public Course() {}

    public Course(String courseId, String name, String description, int credits, 
                 String department, int year, String semester) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.department = department;
        this.year = year;
        this.semester = semester;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", department='" + department + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", lectureHours=" + lectureHours +
                ", seminarHours=" + seminarHours +
                ", labHours=" + labHours +
                ", projectHours=" + projectHours +
                '}';
    }
} 