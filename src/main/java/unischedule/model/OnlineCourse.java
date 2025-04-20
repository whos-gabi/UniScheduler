package unischedule.model;

import unischedule.model.enums.CourseType;

/**
 * Reprezinta un curs online cu atribute specifice
 */
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
    
    /**
     * Genereaza un email de invitatie cu link-ul cursului
     */
    public String generateInvitationEmail(String studentEmail) {
        return "Catre: " + studentEmail + "\n" +
               "Subiect: Invitatie curs " + getName() + "\n\n" +
               "Buna ziua,\n\n" +
               "Va invitam sa participati la cursul online \"" + getName() + "\".\n" +
               "Platforma: " + platformName + "\n" +
               "Link de acces: " + meetingLink + "\n" +
               "Profesor: " + getProfessor().getName() + "\n" +
               "Cerinte tehnice: " + technicalRequirements + "\n\n" +
               "Va multumim,\n" +
               "Universitatea";
    }
    
    /**
     * Verifica daca un curs online are loc in acelasi timp cu alt curs online
     * pentru a evita supraincarcarea platformei
     */
    public boolean overlapsPlatformCapacity(OnlineCourse otherCourse, TimeSlot timeSlot) {
        if (!platformName.equals(otherCourse.getPlatformName())) {
            // Daca sunt pe platforme diferite, nu exista conflict
            return false;
        }
        
        // Obține slot-urile de timp programate pentru celalalt curs
        for (TimeSlot otherSlot : otherCourse.getProfessor().getBookedTimeSlots()) {
            if (otherSlot.overlaps(timeSlot)) {
                // Verifică dacă suma participanților depășește capacitatea
                return (this.maxParticipants + otherCourse.getMaxParticipants() > 150);
            }
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Curs Online: " + platformName + ", " +
                (isRecorded ? "Inregistrat" : "Neinregistrat") + ", " +
                "Max participanti: " + maxParticipants + "]";
    }
} 