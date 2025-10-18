package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import exception.CourtUnavailableException;

import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
public class CourtFacilityTest {
    private String facilityName;
    private AreaLocation facilityLoc;
    private CourtUnit courtUnit1;
    private CourtUnit courtUnit2;
    private CourtFacility courtFacilityTest;
    private User gio;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    public void runBefore() {
        facilityName = "New North Rec";
        facilityLoc = AreaLocation.VANCOUVER;

        courtUnit1 = new CourtUnit("Badminton1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(20, 0));
        courtUnit2 = new CourtUnit("Badminton 2", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(20, 0));

        gio = new User("Gio", "2342", SportType.BADMINTON);
        start = LocalDateTime.of(2025, 10, 15, 12, 0);
        end = LocalDateTime.of(2025, 10, 15, 14, 0);

        courtFacilityTest = new CourtFacility(facilityName, facilityLoc);
    }

    @Test
    public void constructorTest() {
        assertEquals("New North Rec", courtFacilityTest.getFacilityName());
        assertEquals(AreaLocation.VANCOUVER, courtFacilityTest.getFacilityLocation());
        assertEquals(0, courtFacilityTest.getCourts().size());
    }

    @Test
    public void addCourtTest() {
        courtFacilityTest.addCourt(courtUnit1);

        assertEquals(1, courtFacilityTest.getCourts().size());
        assertTrue(courtFacilityTest.getCourts().contains(courtUnit1));
    }

    @Test
    public void findAvailableCourtSuccessTest() throws CourtUnavailableException {
        courtFacilityTest.addCourt(courtUnit1);
        courtFacilityTest.addCourt(courtUnit2);

        // both courts still available
        CourtUnit available = courtFacilityTest.findAvailableCourt(start, end);
        assertTrue(available == courtUnit1 || available == courtUnit2);

        // simulate booking
        Booking booking = new Booking(gio, courtFacilityTest, available, start, end);
        available.reserve(booking);

        // next available has to be other court
        CourtUnit nextAvailable = courtFacilityTest.findAvailableCourt(start, end);
        assertNotEquals(available, nextAvailable);
    }

    @Test
    public void findAvailableCourtFailedTest() throws CourtUnavailableException {
        courtFacilityTest.addCourt(courtUnit1);
        courtFacilityTest.addCourt(courtUnit2);

        Booking booking1 = new Booking(gio, courtFacilityTest, courtUnit1, start, end);
        courtUnit1.reserve(booking1);

        Booking booking2 = new Booking(gio, courtFacilityTest, courtUnit2, start, end);
        courtUnit2.reserve(booking2);

        assertNull(courtFacilityTest.findAvailableCourt(start, end));
    }
}
