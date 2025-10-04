package model;

import java.time.LocalDateTime;

public class Booking {
    private User user;
    private CourtFacility facility;
    private CourtUnit court;
    private LocalDateTime starTime;
    private LocalDateTime endTime;

    // EFFECTS: Creates a new booking with given user, facility, court, and time
    // slot
    public Booking(User user, CourtFacility facility, CourtUnit court, LocalDateTime startTime, LocalDateTime endTime) {
        // stub
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
        return starTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
