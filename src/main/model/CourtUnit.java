package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import exception.CourtUnavailableException;

public class CourtUnit {
    private String courtId;
    private SportType sport;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<Booking> reservations;

    // EFFECTS: creates court unit with given id, sport, opening time, closing time.
    // Don't have reservation at start
    public CourtUnit(String courtId, SportType sport, LocalTime openTime, LocalTime closeTime) {
        this.courtId = courtId;
        this.sport = sport;
        this.openingTime = openTime;
        this.closingTime = closeTime;
        this.reservations = new ArrayList<>();
    }

    // EFFECTS: return true if start and end time is within opening hours
    // and not overlapping with previous bookings
    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        if (start.toLocalTime().isBefore(openingTime) || end.toLocalTime().isAfter(closingTime)) {
            return false;
        }
        for (Booking b : reservations) {
            boolean overlap = start.isBefore(b.getEndTime()) && end.isAfter(b.getStartTime());
            if (overlap) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: reserve the court for given booking
    // throws CourtUnavailableException if court not available
    public void reserve(Booking booking) throws CourtUnavailableException {
        LocalDateTime start = booking.getStartTime();
        LocalDateTime end = booking.getEndTime();

        if (!isAvailable(start, end)) {
            throw new CourtUnavailableException("Court " + courtId + " is not available at this time.");
        } else {
            reservations.add(booking);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an existing booking to reservation without availability check
    // (used to restore data JSON)
    public void addReservation(Booking b) {
        if (!reservations.contains(b)) {
            reservations.add(b);
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

    public List<Booking> getReservations() {
        return reservations;
    }

}
