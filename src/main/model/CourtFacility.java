package model;

import java.time.LocalDateTime;
import java.util.List;

public class CourtFacility {
    private String facilityName;
    private String facilityLocation;
    private List<CourtUnit> courts;

    // EFFECTS: create facility with given name, location, and empty court list
    public CourtFacility(String facilityName, String facilityLocation) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a court to the courts list
    public void addCourt(CourtUnit unit) {
        // stub
    }

    // EFFECTS: return the available court at given start time and end time. Null if
    // not found
    public CourtUnit findAvailableCourt(LocalDateTime start, LocalDateTime end) {
        return null; // stub
    }

    // getters
    public String getFacilityName() {
        return facilityName;
    }

    public String getFacilityLocation() {
        return facilityLocation;
    }

    public List<CourtUnit> getCourts() {
        return courts;
    }
}
