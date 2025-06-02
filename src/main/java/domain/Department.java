package domain;

import java.util.*;

/**
 * Represents an academic department in the university
 */
public class Department {
    private String departmentId;
    private String name;
    private String code; // CS, SE, IT, etc.
    private String head; // Department head teacher ID
    private String description;
    private Set<String> teacherIds;
    private Set<String> courseIds;
    private String building;
    private String phone;
    private String email;

    public Department() {
        this.teacherIds = new HashSet<>();
        this.courseIds = new HashSet<>();
    }

    public Department(String departmentId, String name, String code, String head, 
                     String description, String building, String phone, String email) {
        this.departmentId = departmentId;
        this.name = name;
        this.code = code;
        this.head = head;
        this.description = description;
        this.building = building;
        this.phone = phone;
        this.email = email;
        this.teacherIds = new HashSet<>();
        this.courseIds = new HashSet<>();
    }

    // Getters and Setters
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTeacherIds() {
        return new HashSet<>(teacherIds);
    }

    public void setTeacherIds(Set<String> teacherIds) {
        this.teacherIds = new HashSet<>(teacherIds);
    }

    public Set<String> getCourseIds() {
        return new HashSet<>(courseIds);
    }

    public void setCourseIds(Set<String> courseIds) {
        this.courseIds = new HashSet<>(courseIds);
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Business methods
    public boolean addTeacher(String teacherId) {
        return teacherIds.add(teacherId);
    }

    public boolean removeTeacher(String teacherId) {
        return teacherIds.remove(teacherId);
    }

    public boolean addCourse(String courseId) {
        return courseIds.add(courseId);
    }

    public boolean removeCourse(String courseId) {
        return courseIds.remove(courseId);
    }

    public int getTeacherCount() {
        return teacherIds.size();
    }

    public int getCourseCount() {
        return courseIds.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", head='" + head + '\'' +
                ", description='" + description + '\'' +
                ", teacherCount=" + getTeacherCount() +
                ", courseCount=" + getCourseCount() +
                ", building='" + building + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
} 