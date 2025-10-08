package model;

import java.time.LocalDateTime;

public class Session {
    private User owner;
    private CourtFacility facility;
    private CourtUnit courtUnit;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SportType sport;
    private String description;

    // EFFECTS: creates new session with user's owner, type of sport, court
    // facility, court Unit, start time for session, end time for session, and no
    // description.
    public Session(User owner, SportType sport, CourtFacility facility, CourtUnit courtUnit,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.owner = owner;
        this.sport = sport;
        this.facility = facility;
        this.courtUnit = courtUnit;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
}
