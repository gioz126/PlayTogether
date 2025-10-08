package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private User owner;
    private CourtFacility facility;
    private CourtUnit courtUnit;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SportType sport;
    private String description;
    private List<User> participants;

    // EFFECTS: creates new session with owner, type of sport, court
    // facility, court Unit, start time for session, end time for session, and no
    // description, and only the owner as initial participant.
    public Session(User owner, SportType sport, CourtFacility facility, CourtUnit courtUnit,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.owner = owner;
        this.sport = sport;
        this.facility = facility;
        this.courtUnit = courtUnit;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.participants = new ArrayList<>();
        this.participants.add(owner);
    }

    //MODIFIES: this
    //EFFECTS: adds a participants to the session if have not joined
    public void addParticipant(User user) {
        //stub
    }

    //MODIFIES: this
    //EFFECTS: removes a participant from the session
    public void removeParticipant(User user) {
        //stub
    }

    // setters
    public void setDescription(String description) {
        this.description = description;
    }

    // getters
    public User getOwner() {
        return owner;
    }

    public SportType getSport() {
        return sport;
    }

    public CourtFacility getFacility() {
        return facility;
    }

    public CourtUnit getCourtUnit() {
        return courtUnit;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getParticipant() {
        return new ArrayList<>(participants);
    }
}
