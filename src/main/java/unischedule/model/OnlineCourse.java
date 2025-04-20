package unischedule.model;

import unischedule.model.enums.CourseType;


public class OnlineCourse extends Course {
    private String platformName;
    private String meetingLink;
    private boolean isRecorded;
    private int maxParticipants;
    private String technicalRequirements;
    
    public OnlineCourse() {
        super();
    }
    
    public OnlineCourse(String name, String code, CourseType type, Professor professor, 
                        String platformName, String meetingLink) {
        super(name, code, type, professor);
        this.platformName = platformName;
        this.meetingLink = meetingLink;
        this.isRecorded = true;
        this.maxParticipants = 100;
        this.technicalRequirements = "Conexiune internet, camera web, microfon";
    }
    
    public String getPlatformName() {
        return platformName;
    }
    
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
    
    public String getMeetingLink() {
        return meetingLink;
    }
    
    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }
    
    public boolean isRecorded() {
        return isRecorded;
    }
    
    public void setRecorded(boolean isRecorded) {
        this.isRecorded = isRecorded;
    }
    
    public int getMaxParticipants() {
        return maxParticipants;
    }
    
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    
    public String getTechnicalRequirements() {
        return technicalRequirements;
    }
    
    public void setTechnicalRequirements(String technicalRequirements) {
        this.technicalRequirements = technicalRequirements;
    }
    
    
    @Override
    public String toString() {
        return super.toString() + " [Curs Online: " + platformName + ", " +
                (isRecorded ? "Inregistrat" : "Neinregistrat") + ", " +
                "Max participanti: " + maxParticipants + "]";
    }
} 