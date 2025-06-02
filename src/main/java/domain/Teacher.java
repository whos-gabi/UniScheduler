package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a teacher in the university timetable system
 * Extends Person to demonstrate inheritance
 */
public class Teacher extends Person {
    private String department;
    private String title; // Professor, Associate Professor, etc.
    private String officeNumber;
    private String phoneNumber;
    private String researchArea;
    private List<String> subjects; // Subjects the teacher can teach

    public Teacher() {
        super();
        this.subjects = new ArrayList<>();
    }

    public Teacher(String teacherId, String firstName, String lastName, String email,
                  String department, String title, String officeNumber, String phoneNumber, String researchArea) {
        super(teacherId, firstName, lastName, email);
        this.department = department;
        this.title = title;
        this.officeNumber = officeNumber;
        this.phoneNumber = phoneNumber;
        this.researchArea = researchArea;
        this.subjects = new ArrayList<>();
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

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public void removeSubject(String subject) {
        subjects.remove(subject);
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
                ", officeNumber='" + officeNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", researchArea='" + researchArea + '\'' +
                ", subjects=" + subjects +
                '}';
    }
} 