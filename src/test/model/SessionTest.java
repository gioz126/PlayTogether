package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest {
    private Session testSession;
    private User owner;
    private CourtFacility facility;
    private CourtUnit court1;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    public void runBefore() {
        owner = new User("Gio", "123456789", SportType.BADMINTON);

        facility = new CourtFacility("UBC NORTH REC", "VANCOUVER");
        court1 = new CourtUnit("B1", SportType.BADMINTON, LocalTime.of(8, 0),
                LocalTime.of(22, 0));
        facility.addCourt(court1);

        start = LocalDateTime.of(2025, 10, 3, 18, 0);
        end = LocalDateTime.of(2025, 10, 3, 19, 0);

        testSession = new Session(owner, SportType.BADMINTON, facility, court1, start, end);
    }

    @Test
    public void constructorTest() {
        assertEquals(owner, testSession.getOwner());
        assertEquals(SportType.BADMINTON, testSession.getSport());
        assertEquals(facility, testSession.getFacility());
        assertEquals(court1, testSession.getCourtUnit());
        assertEquals(start, testSession.getStartDateTime());
        assertEquals(end, testSession.getEndDateTime());
        assertEquals(1, testSession.getParticipant().size());
        assertTrue(testSession.getParticipant().contains(owner));
        assertTrue(testSession.getDescription().isEmpty());
    }

    @Test
    public void addParticipantTest() {
        //initialize new user
        User participant1 = new User("zio", "1234", SportType.BADMINTON);

        //add participant who is not already a participant in the session
        testSession.addParticipant(participant1);
        assertEquals(2, testSession.getParticipant().size());
        assertTrue(testSession.getParticipant().contains(participant1));

        // add same participant, should not be added
        assertFalse(testSession.addParticipant(participant1));
        assertEquals(2, testSession.getParticipant().size());
    }

    @Test
    public void removeParticipantTest() {
        //initialize new user
        User participant1 = new User("zio", "1234", SportType.BADMINTON);

        // participant not found
        assertFalse(testSession.removeParticipant(participant1));

        // participant to be removed found
        testSession.addParticipant(participant1);
        assertTrue(testSession.getParticipant().contains(participant1));
        assertTrue(testSession.removeParticipant(participant1));
        assertFalse(testSession.getParticipant().contains(participant1));
    }

    @Test
    public void setDescriptiontest() {
        testSession.setDescription("Play at October 3'rd 2025");
        assertEquals("Play at October 3'rd 2025", testSession.getDescription());
    }
}
