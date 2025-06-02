package domain;

import java.util.*;

/**
 * Represents a group of students in the university timetable system
 */
public class StudentGroup {
    private String groupId;
    private String groupName;
    private String major;
    private int year;
    private String semester;
    private int maxCapacity;
    private Set<String> studentIds; // Using Set to avoid duplicates
    private String specialization;

    public StudentGroup() {
        this.studentIds = new HashSet<>();
    }

    public StudentGroup(String groupId, String groupName, String major, int year, 
                       String semester, int maxCapacity, String specialization) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.major = major;
        this.year = year;
        this.semester = semester;
        this.maxCapacity = maxCapacity;
        this.specialization = specialization;
        this.studentIds = new HashSet<>();
    }

    // Getters and Setters
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<String> getStudentIds() {
        return new HashSet<>(studentIds); // Return defensive copy
    }

    public void setStudentIds(Set<String> studentIds) {
        this.studentIds = new HashSet<>(studentIds);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Business methods
    public boolean addStudent(String studentId) {
        if (studentIds.size() >= maxCapacity) {
            return false;
        }
        return studentIds.add(studentId);
    }

    public boolean removeStudent(String studentId) {
        return studentIds.remove(studentId);
    }

    public int getCurrentSize() {
        return studentIds.size();
    }

    public boolean isFull() {
        return studentIds.size() >= maxCapacity;
    }

    public boolean hasStudent(String studentId) {
        return studentIds.contains(studentId);
    }

    public List<String> getStudentIdsList() {
        return new ArrayList<>(studentIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", major='" + major + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", currentSize=" + getCurrentSize() +
                ", specialization='" + specialization + '\'' +
                '}';
    }
} 