package domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a time slot in the university timetable system
 * Implements Comparable to allow for sorted collections
 */
public class TimeSlot implements Comparable<TimeSlot> {
    private String timeSlotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek; // Monday, Tuesday, etc.
    private int duration; // in minutes
    private String period; // Morning, Afternoon, Evening
    
    // Static formatter for time display
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TimeSlot() {}

    public TimeSlot(String timeSlotId, LocalTime startTime, LocalTime endTime, String dayOfWeek) {
        this.timeSlotId = timeSlotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.duration = calculateDuration();
        this.period = determinePeriod();
    }

    public TimeSlot(String timeSlotId, String startTimeStr, String endTimeStr, String dayOfWeek) {
        this.timeSlotId = timeSlotId;
        this.startTime = LocalTime.parse(startTimeStr);
        this.endTime = LocalTime.parse(endTimeStr);
        this.dayOfWeek = dayOfWeek;
        this.duration = calculateDuration();
        this.period = determinePeriod();
    }

    // Getters and Setters
    public String getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        this.duration = calculateDuration();
        this.period = determinePeriod();
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        this.duration = calculateDuration();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDuration() {
        return duration;
    }

    protected void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPeriod() {
        return period;
    }

    protected void setPeriod(String period) {
        this.period = period;
    }

    // Business methods
    private int calculateDuration() {
        if (startTime != null && endTime != null) {
            return (int) java.time.Duration.between(startTime, endTime).toMinutes();
        }
        return 0;
    }

    private String determinePeriod() {
        if (startTime == null) return "Unknown";
        
        int hour = startTime.getHour();
        if (hour < 12) {
            return "Morning";
        } else if (hour < 17) {
            return "Afternoon";
        } else {
            return "Evening";
        }
    }

    public boolean overlaps(TimeSlot other) {
        if (!this.dayOfWeek.equals(other.dayOfWeek)) {
            return false;
        }
        
        return this.startTime.isBefore(other.endTime) && 
               this.endTime.isAfter(other.startTime);
    }

    public boolean isValidTimeSlot() {
        return startTime != null && endTime != null && 
               startTime.isBefore(endTime) && 
               dayOfWeek != null && !dayOfWeek.trim().isEmpty();
    }

    public String getFormattedTimeRange() {
        if (startTime != null && endTime != null) {
            return startTime.format(TIME_FORMATTER) + " - " + endTime.format(TIME_FORMATTER);
        }
        return "Invalid time range";
    }

    // Implementing Comparable for sorted collections
    @Override
    public int compareTo(TimeSlot other) {
        // First compare by day of week
        int dayComparison = getDayOrder(this.dayOfWeek) - getDayOrder(other.dayOfWeek);
        if (dayComparison != 0) {
            return dayComparison;
        }
        
        // Then compare by start time
        return this.startTime.compareTo(other.startTime);
    }

    private int getDayOrder(String day) {
        switch (day.toLowerCase()) {
            case "monday": return 1;
            case "tuesday": return 2;
            case "wednesday": return 3;
            case "thursday": return 4;
            case "friday": return 5;
            case "saturday": return 6;
            case "sunday": return 7;
            default: return 8;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(timeSlotId, timeSlot.timeSlotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId='" + timeSlotId + '\'' +
                ", startTime=" + (startTime != null ? startTime.format(TIME_FORMATTER) : "null") +
                ", endTime=" + (endTime != null ? endTime.format(TIME_FORMATTER) : "null") +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", duration=" + duration + " min" +
                ", period='" + period + '\'' +
                '}';
    }
} 