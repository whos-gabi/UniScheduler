package unischedule.model;

import java.time.LocalTime;

import unischedule.model.enums.DayOfWeek;

/**
 * interval de timp dintr-o zi
 */
public class TimeSlot {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot() {}

    public TimeSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
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

    public boolean overlaps(TimeSlot other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }

    @Override
    public String toString() {
        return day.getDisplayName() + ", " + startTime + " - " + endTime;
    }
} 