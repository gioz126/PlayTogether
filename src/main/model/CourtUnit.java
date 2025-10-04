package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class CourtUnit {
    private String courtId;
    private SportType sport;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Map<LocalDateTime, User> reservation;

    // EFFECTS: creates court unit with given id, sport, opening time, closing time.
    // Don't have reservation at start
    public CourtUnit(String courtId, SportType sport, LocalTime openTime, LocalTime closeTime) {
        // stub
    }

    // EFFECTS: return true if start and end time is within opening hours
    // and the court is not reserved at that time
    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: reserve the court for user at given time
    public void reserve(LocalDateTime start, LocalDateTime end, User user) {
        // stub
    }

    // getters
    public String getcourtID() {
        return courtId;
    }

    public SportType getSport() {
        return sport;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

}
