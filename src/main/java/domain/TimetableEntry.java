package domain;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a single entry in the university timetable
 */
public class TimetableEntry {
    private String entryId;
    private String courseId;
    private String teacherId;
    private String roomId;
    private String groupName;
    private String dayOfWeek; // Monday, Tuesday, etc.
    private LocalTime startTime;
    private LocalTime endTime;
    private String type; // Lecture, Seminar, Laboratory, Project
    private String weekType; // ALL, ODD, EVEN
    private String subgroup; // For laboratory sessions that split groups

    public TimetableEntry() {}

    public TimetableEntry(String entryId, String courseId, String teacherId, String roomId,
                         String groupName, String dayOfWeek, LocalTime startTime, LocalTime endTime,
                         String type, String weekType) {
        this.entryId = entryId;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.roomId = roomId;
        this.groupName = groupName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.weekType = weekType;
    }

    // Getters and Setters
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getTimeSlot() {
        return startTime + " - " + endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimetableEntry that = (TimetableEntry) o;
        return Objects.equals(entryId, that.entryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId);
    }

    @Override
    public String toString() {
        return "TimetableEntry{" +
                "entryId='" + entryId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type='" + type + '\'' +
                ", weekType='" + weekType + '\'' +
                ", subgroup='" + subgroup + '\'' +
                '}';
    }
} 