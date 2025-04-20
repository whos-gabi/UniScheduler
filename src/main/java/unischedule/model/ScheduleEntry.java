package unischedule.model;

/**
 * Intrare in orar
 */
public class ScheduleEntry {
    private Course course;
    private Room room;
    private StudentGroup studentGroup;
    private TimeSlot timeSlot;

    public ScheduleEntry() {}

    public ScheduleEntry(Course course, Room room, StudentGroup studentGroup, TimeSlot timeSlot) {
        this.course = course;
        this.room = room;
        this.studentGroup = studentGroup;
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    /**
     * Verifica daca aceasta intrare este pentru un curs online
     */
    public boolean isOnlineCourse() {
        return course instanceof OnlineCourse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(course.getName()).append(" (").append(course.getType().getDisplayName()).append(")\n");
        
        if (room != null) {
            sb.append("Sala: ").append(room.getRoomNumber()).append("\n");
        } else if (isOnlineCourse()) {
            OnlineCourse onlineCourse = (OnlineCourse) course;
            sb.append("Curs Online: ").append(onlineCourse.getPlatformName()).append("\n");
            sb.append("Link: ").append(onlineCourse.getMeetingLink()).append("\n");
        } else {
            sb.append("Locatie: Nespecificata\n");
        }
        
        sb.append("Grupa: ").append(studentGroup.getName()).append("\n");
        sb.append("Profesor: ").append(course.getProfessor().getName()).append("\n");
        sb.append("Interval: ").append(timeSlot.toString());
        
        return sb.toString();
    }
} 