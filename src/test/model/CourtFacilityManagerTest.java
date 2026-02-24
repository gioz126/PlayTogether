package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CourtFacilityManagerTest {
    private CourtFacilityManager testFacilityManager;
    private CourtFacility facility1;
    private CourtUnit court1;

    @BeforeEach
    public void runBefore() {
        testFacilityManager = new CourtFacilityManager();

        facility1 = new CourtFacility("UBC Badminton", AreaLocation.VANCOUVER);

        court1 = new CourtUnit("Badminton 1", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0));

        facility1.addCourt(court1);
    }

    @Test
    public void addFacilityTest() {
        testFacilityManager.addFacility(facility1);

        testFacilityManager.getAllFacilities().contains(facility1);
    }

    @Test
    public void findFacilityByNameTest() {
        testFacilityManager.addFacility(facility1);
        // not found return null
        assertEquals(null, testFacilityManager.findFacilityByName("Richmond Badminton"));

        // found, return the facility

        assertEquals(facility1, testFacilityManager.findFacilityByName("UBC Badminton"));
    }

}
