package domain;

/**
 * Represents an online course in the university timetable system
 * Extends Course to demonstrate inheritance with online-specific features
 */
public class OnlineCourse extends Course {
    private String platform; // Zoom, Teams, WebEx, etc.
    private String meetingUrl;
    private String meetingId;
    private String accessCode;
    private boolean requiresRegistration;
    private int maxParticipants;

    public OnlineCourse() {
        super();
    }

    public OnlineCourse(String courseId, String name, int credits, int year, String semester,
                       String platform, String meetingUrl, String meetingId) {
        super(courseId, name, credits, year, semester);
        this.platform = platform;
        this.meetingUrl = meetingUrl;
        this.meetingId = meetingId;
        this.requiresRegistration = false;
        this.maxParticipants = 100; // Default limit
    }

    // Getters and Setters for online-specific fields
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public boolean isRequiresRegistration() {
        return requiresRegistration;
    }

    public void setRequiresRegistration(boolean requiresRegistration) {
        this.requiresRegistration = requiresRegistration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    // Override method to include online-specific information
    @Override
    public String toString() {
        return "OnlineCourse{" +
                "courseId='" + getCourseId() + '\'' +
                ", name='" + getName() + '\'' +
                ", credits=" + getCredits() +
                ", year=" + getYear() +
                ", semester='" + getSemester() + '\'' +
                ", lectureHours=" + getLectureHours() +
                ", seminarHours=" + getSeminarHours() +
                ", labHours=" + getLabHours() +
                ", projectHours=" + getProjectHours() +
                ", platform='" + platform + '\'' +
                ", meetingUrl='" + meetingUrl + '\'' +
                ", meetingId='" + meetingId + '\'' +
                ", requiresRegistration=" + requiresRegistration +
                ", maxParticipants=" + maxParticipants +
                '}';
    }

    // Additional method specific to online courses
    public String getConnectionInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Platform: ").append(platform).append("\n");
        if (meetingUrl != null && !meetingUrl.isEmpty()) {
            info.append("Meeting URL: ").append(meetingUrl).append("\n");
        }
        if (meetingId != null && !meetingId.isEmpty()) {
            info.append("Meeting ID: ").append(meetingId).append("\n");
        }
        if (accessCode != null && !accessCode.isEmpty()) {
            info.append("Access Code: ").append(accessCode).append("\n");
        }
        return info.toString();
    }

    public boolean hasConflictWith(OnlineCourse other) {
        // Online courses on the same platform with overlapping times might conflict
        return this.platform.equals(other.platform) && 
               this.meetingId.equals(other.meetingId);
    }
} 