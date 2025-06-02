package domain;

import java.util.Objects;

/**
 * Represents a student in the university timetable system
 * Extends Person to demonstrate inheritance
 */
public class Student extends Person {
    private int year;
    private String major;
    private String groupName;

    public Student() {
        super();
    }

    public Student(String studentId, String firstName, String lastName, String email, 
                  int year, String major, String groupName) {
        super(studentId, firstName, lastName, email);
        this.year = year;
        this.major = major;
        this.groupName = groupName;
    }

    // Getters and Setters for Student-specific fields
    public String getStudentId() {
        return getId();
    }

    public void setStudentId(String studentId) {
        setId(studentId);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    // Implementation of abstract method from Person
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + getId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
} 