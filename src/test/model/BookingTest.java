package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
public class BookingTest {

    private Booking bookingTest;
    private User testUser;
    private CourtFacility facility;
    private CourtUnit court1;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    public void runBefore() {
        testUser = new User("John", "1293", SportType.BADMINTON);
        facility = new CourtFacility("GYM", AreaLocation.VANCOUVER);
        court1 = new CourtUnit("BADMINTON1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0));

        start = LocalDateTime.of(2025, 10, 15, 12, 0);
        end = LocalDateTime.of(2025, 10, 15, 14, 0);
        bookingTest = new Booking(testUser, facility, court1, start, end);

    }

    @Test
    public void constructorTest() {
        assertEquals(testUser, bookingTest.getUser());
        assertEquals(facility, bookingTest.getFacility());
        assertEquals(court1, bookingTest.getCourt());
        assertEquals(start, bookingTest.getStartTime());
        assertEquals(end, bookingTest.getEndTime());
    }

    @Test 
    public void testToJson() {
        JSONObject json = bookingTest.toJson();

        assertEquals("GYM", json.get("facilityName"));
        assertEquals("BADMINTON1", json.get("courtId"));
        assertEquals("John", json.get("userName"));
        assertEquals("2025-10-15T12:00", json.get("startTime"));
        assertEquals("2025-10-15T14:00", json.get("endTime"));
    }

}
