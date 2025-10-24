package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a facility.
 * Each CourtFacility stores its name, location and courts inside them.
 */
public class CourtFacility {
    private String facilityName;
    private AreaLocation facilityLocation;
    private List<CourtUnit> courts;

    // EFFECTS: create facility with given name, location, and empty court list
    public CourtFacility(String facilityName, AreaLocation facilityLocation) {
        this.facilityName = facilityName;
        this.facilityLocation = facilityLocation;
        courts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a court to the courts list
    public void addCourt(CourtUnit unit) {
        courts.add(unit);
    }

    // EFFECTS: return the available court at given start time and end time. Null if
    // not found
    public CourtUnit findAvailableCourt(LocalDateTime start, LocalDateTime end) {
        for (CourtUnit unit : courts) {
            if (unit.isAvailable(start, end)) {
                return unit;
            }
        }
        return null;
    }

    // EFFECTS: return the court with given Id, null if not found
    public CourtUnit findCourtById(String courtId) {
        for (CourtUnit court : courts) {
            if (court.getcourtID().equals(courtId)) {
                return court;
            }
        }
        return null;
    }

    // getters
    public String getFacilityName() {
        return facilityName;
    }

    public AreaLocation getFacilityLocation() {
        return facilityLocation;
    }

    public List<CourtUnit> getCourts() {
        return courts;
    }
}
