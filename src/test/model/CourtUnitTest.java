package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import exception.CourtUnavailableException;

import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
public class CourtUnitTest {
    private CourtUnit courtTest;
    private LocalDateTime start;
    private LocalDateTime end;
    private Booking booking1;
    private User gio;
    private CourtFacility facility;

    @BeforeEach
    public void runBefore() {
        courtTest = new CourtUnit("Badminton1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(20, 0));
        facility = new CourtFacility("Gym", AreaLocation.VANCOUVER);

        start = LocalDateTime.of(2025, 10, 15, 12, 0);
        end = LocalDateTime.of(2025, 10, 15, 14, 0);

        gio = new User("gio", "1234", SportType.BADMINTON);
        booking1 = new Booking(gio, facility, courtTest, start, end);
    }

    @Test
    public void constructorTest() {
        assertEquals("Badminton1", courtTest.getcourtID());
        assertEquals(SportType.BADMINTON, courtTest.getSport());
        assertEquals(LocalTime.of(8, 0), courtTest.getOpeningTime());
        assertEquals(LocalTime.of(20, 0), courtTest.getClosingTime());
        assertTrue(courtTest.getReservations().isEmpty());
    }

    @Test
    public void isAvailableSuccessTest() throws CourtUnavailableException {
        // reserve court booking for 12 - 14
        courtTest.reserve(booking1);

        // just at the bound (8 - 9)
        LocalDateTime startJustAtLowerBound = LocalDateTime.of(2025, 10,
                15, 8, 0);
        LocalDateTime endJustAtLowerBound = LocalDateTime.of(2025, 10,
                15, 9, 0);
        Boolean availableStartJust = courtTest.isAvailable(startJustAtLowerBound, endJustAtLowerBound);
        assertTrue(availableStartJust);

        // within time opening hours / closing hours (17 -18)
        LocalDateTime startMidBound = LocalDateTime.of(2025, 10, 15, 17, 0);
        LocalDateTime endMidBound = LocalDateTime.of(2025, 10, 15, 18, 0);
        Boolean availableMid = courtTest.isAvailable(startMidBound, endMidBound);
        assertTrue(availableMid);

        // just right after someone's booked time (14 - 15)
        LocalDateTime startJustAfterSomeone = LocalDateTime.of(2025, 10,
                15, 14, 15);
        LocalDateTime end15 = LocalDateTime.of(2025, 10,
                15, 15, 0);
        Boolean availableRightAfterSomone = courtTest.isAvailable(startJustAfterSomeone, end15);
        assertTrue(availableRightAfterSomone);

        // just right before someone's booked time (11 - 12)
        LocalDateTime startJustBeforeSomeone = LocalDateTime.of(2025, 10,
                15, 11, 15);
        LocalDateTime end14 = LocalDateTime.of(2025, 10,
                15, 12, 0);
        Boolean availableRightBeforeSomone = courtTest.isAvailable(startJustBeforeSomeone, end14);
        assertTrue(availableRightBeforeSomone);

        // end just at bound (19 - 20)
        LocalDateTime startJustAtUpperBound = LocalDateTime.of(2025, 10,
                15, 19, 0);
        LocalDateTime endJustAtUpperBound = LocalDateTime.of(2025, 10,
                15, 20, 0);
        Boolean availableEndJust = courtTest.isAvailable(startJustAtUpperBound, endJustAtUpperBound);
        assertTrue(availableEndJust);
    }

    @Test
    public void isAvailableFailedTest() throws CourtUnavailableException {
        // reserve court booking for 12 - 14
        courtTest.reserve(booking1);

        // not within opening hours (7 - 8)
        LocalDateTime startLower = LocalDateTime.of(2025, 10,
                15, 7, 0);
        LocalDateTime endLower = LocalDateTime.of(2025, 10,
                15, 8, 0);
        Boolean unavailableLower = courtTest.isAvailable(startLower, endLower);
        assertFalse(unavailableLower);

        // not within closing hours (20 - 21)
        LocalDateTime startTimeUpper = LocalDateTime.of(2025, 10,
                15, 20, 0);
        LocalDateTime endTimeUpper = LocalDateTime.of(2025, 10,
                15, 21, 0);
        Boolean unavailableUpper = courtTest.isAvailable(startTimeUpper, endTimeUpper);
        assertFalse(unavailableUpper);

        // Somone already booked at that time (overlap case)
        Boolean unavailableOverlap = courtTest.isAvailable(start, end);
        assertFalse(unavailableOverlap);

        // Someone already booked at that time (12 - 14) but overlap case2 (12 - 13)
        LocalDateTime start12 = LocalDateTime.of(2025, 10,
                15, 12, 0);
        LocalDateTime end13 = LocalDateTime.of(2025, 10,
                15, 13, 0);
        Boolean unavailableOverlapCase2 = courtTest.isAvailable(start12, end13);
        assertFalse(unavailableOverlapCase2);

        // Someone already booked at that time (12 - 14) but overlap case3 (11 - 13)
        LocalDateTime start11 = LocalDateTime.of(2025, 10,
                15, 11, 0);
        Boolean unavailableOverlapCase3 = courtTest.isAvailable(start11, end13);
        assertFalse(unavailableOverlapCase3);

    }

    @Test
    public void reserveSucessTest() throws CourtUnavailableException {
        courtTest.reserve(booking1);

        assertEquals(1, courtTest.getReservations().size());
        assertTrue(courtTest.getReservations().contains(booking1));
    }

    @Test
    public void reserveFailedTest() throws CourtUnavailableException {
        // reserve at booking time
        courtTest.reserve(booking1);

        // reserve again at booking time, should throw exception
        assertThrows(CourtUnavailableException.class, () -> {
            courtTest.reserve(booking1);
        });

    }
}
