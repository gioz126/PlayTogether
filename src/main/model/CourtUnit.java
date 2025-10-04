package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import exception.CourtUnavailableException;

public class CourtUnit {
    private String courtId;
    private SportType sport;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Map<LocalDateTime, User> reservation;

    // EFFECTS: creates court unit with given id, sport, opening time, closing time.
    // Don't have reservation at start
    public CourtUnit(String courtId, SportType sport, LocalTime openTime, LocalTime closeTime) {
        this.courtId = courtId;
        this.sport = sport;
        this.openingTime = openTime;
        this.closingTime = closeTime;
        this.reservation = new HashMap<>();
    }

    // EFFECTS: return true if start and end time is within opening hours
    // and the court is not reserved at that time
    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        if (start.toLocalTime().isBefore(openingTime) || end.toLocalTime().isAfter(closingTime)) {
            return false;
        }
        return !reservation.containsKey(start);

    }

    // MODIFIES: this
    // EFFECTS: reserve the court for user at given time
    // throws CourtUnavailableException if court not available
    public void reserve(LocalDateTime start, LocalDateTime end, User user)
            throws CourtUnavailableException {
        if (!isAvailable(start, end)) {
            throw new CourtUnavailableException("Court" + courtId + " is not available at this time.");
        } else {
            reservation.put(start, user);
        }
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
