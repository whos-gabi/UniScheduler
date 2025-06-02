package domain;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents an academic semester in the university
 */
public class Semester {
    private String semesterId;
    private int year;
    private int semesterNumber; // 1 or 2
    private String name; // "Fall 2024", "Spring 2025", etc.
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate examStartDate;
    private LocalDate examEndDate;
    private List<String> courseIds;
    private boolean isActive;

    public Semester() {
        this.courseIds = new ArrayList<>();
    }

    public Semester(String semesterId, int year, int semesterNumber, String name,
                   LocalDate startDate, LocalDate endDate, LocalDate examStartDate, LocalDate examEndDate) {
        this.semesterId = semesterId;
        this.year = year;
        this.semesterNumber = semesterNumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examStartDate = examStartDate;
        this.examEndDate = examEndDate;
        this.courseIds = new ArrayList<>();
        this.isActive = false;
    }

    // Getters and Setters
    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getExamStartDate() {
        return examStartDate;
    }

    public void setExamStartDate(LocalDate examStartDate) {
        this.examStartDate = examStartDate;
    }

    public LocalDate getExamEndDate() {
        return examEndDate;
    }

    public void setExamEndDate(LocalDate examEndDate) {
        this.examEndDate = examEndDate;
    }

    public List<String> getCourseIds() {
        return new ArrayList<>(courseIds);
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = new ArrayList<>(courseIds);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Business methods
    public boolean addCourse(String courseId) {
        if (!courseIds.contains(courseId)) {
            return courseIds.add(courseId);
        }
        return false;
    }

    public boolean removeCourse(String courseId) {
        return courseIds.remove(courseId);
    }

    public int getCourseCount() {
        return courseIds.size();
    }

    public boolean isCurrentlyActive() {
        LocalDate now = LocalDate.now();
        return isActive && 
               startDate != null && endDate != null &&
               !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    public boolean isExamPeriod() {
        LocalDate now = LocalDate.now();
        return examStartDate != null && examEndDate != null &&
               !now.isBefore(examStartDate) && !now.isAfter(examEndDate);
    }

    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return Objects.equals(semesterId, semester.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterId);
    }

    @Override
    public String toString() {
        return "Semester{" +
                "semesterId='" + semesterId + '\'' +
                ", year=" + year +
                ", semesterNumber=" + semesterNumber +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", examStartDate=" + examStartDate +
                ", examEndDate=" + examEndDate +
                ", courseCount=" + getCourseCount() +
                ", isActive=" + isActive +
                '}';
    }
} 