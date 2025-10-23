package model;

import java.util.ArrayList;
import java.util.List;

public class CourtFacilityManager {
    private List<CourtFacility> facilities;

    public CourtFacilityManager() {
        facilities = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds facility if not already in the list
    public void addFacility(CourtFacility facility) {
        // stub
    }

    // EFFECTS: return facility with given name, null if not found
    public CourtFacility findFacilityByName(String name) {
        return null; // stub
    }

    // getters
    public List<CourtFacility> getAllFacilities() {
        return new ArrayList<>(facilities);
    }

}
