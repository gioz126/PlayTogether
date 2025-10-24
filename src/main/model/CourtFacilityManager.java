package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage all facilities in PlayTogether app
 * CourtFacilityManager responsible for maintaining active facilities
 * available in the app and find facilities by name
 */
public class CourtFacilityManager {
    private List<CourtFacility> facilities;

    public CourtFacilityManager() {
        facilities = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds facility if not already in the list
    public void addFacility(CourtFacility facility) {
        facilities.add(facility);
    }

    // EFFECTS: return facility with given name, null if not found
    public CourtFacility findFacilityByName(String name) {
        for (CourtFacility facility : facilities) {
            if (facility.getFacilityName().equals(name)) {
                return facility;
            }
        }
        return null;
    }

    // getters
    public List<CourtFacility> getAllFacilities() {
        return new ArrayList<>(facilities);
    }

}
