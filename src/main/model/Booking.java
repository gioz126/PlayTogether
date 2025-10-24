package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

import persistence.Writable;

public class Booking implements Writable {
    private User user;
    private CourtFacility facility;
    private CourtUnit court;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // EFFECTS: Creates a new booking with given user, facility, court, and time
    // slot
    public Booking(User user, CourtFacility facility, CourtUnit court, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.facility = facility;
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public CourtFacility getFacility() {
        return facility;
    }

    public CourtUnit getCourt() {
        return court;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("facilityName", facility.getFacilityName());
        json.put("courtId", court.getcourtID());
        json.put("userName", user.getName());
        json.put("startTime", startTime.toString());
        json.put("endTime", endTime.toString());
        return json;
    }
}
