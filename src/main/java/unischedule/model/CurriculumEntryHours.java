package unischedule.model;

public class CurriculumEntryHours {
    private int lectureHours;
    private int seminarHours;
    private int labHours;
    private int projectHours;

    public CurriculumEntryHours(int lectureHours, int seminarHours, int labHours, int projectHours) {
        this.lectureHours = lectureHours;
        this.seminarHours = seminarHours;
        this.labHours = labHours;
        this.projectHours = projectHours;
    }

    public CurriculumEntryHours() {
        this.lectureHours = 0;
        this.seminarHours = 0;
        this.labHours = 0;
        this.projectHours = 0;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public int getSeminarHours() {
        return seminarHours;
    }

    public int getLabHours() {
        return labHours;
    }

    public int getProjectHours() {
        return projectHours;
    }
}
