package domain;

import java.util.Objects;

/**
 * Represents a teacher in the university timetable system
 * Extends Person to demonstrate inheritance
 */
public class Teacher extends Person {
    private String department;
    private String title; // Professor, Associate Professor, etc.

    public Teacher() {
        super();
    }

    // Main constructor for teacher creation
    public Teacher(String teacherId, String firstName, String lastName, String email,
                  String department, String title) {
        super(teacherId, firstName, lastName, email);
        this.department = department;
        this.title = title;
    }

    // Getters and Setters for Teacher-specific fields
    public String getTeacherId() {
        return getId();
    }

    public void setTeacherId(String teacherId) {
        setId(teacherId);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Implementation of abstract method from Person
    @Override
    public String getRole() {
        return "Teacher";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + getId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
} 