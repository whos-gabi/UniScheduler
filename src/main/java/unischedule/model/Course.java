package unischedule.model;

import unischedule.model.enums.CourseType;


public class Course {
    private String name;
    private String code; //abbr
    private CourseType type;
    private Professor professor;

    public Course() {}

    public Course(String name, String code, CourseType type, Professor professor) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.professor = professor;
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

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return name + " (" + type.getDisplayName() + ") - " + professor.getName();
    }
} 