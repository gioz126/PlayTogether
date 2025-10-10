package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionManagerTest {
    private SessionManager sessionManagerTest;
    private Session session1;
    private Session session2;
    private User owner;
    private User user2;
    private CourtFacility facility;
    private CourtUnit court1;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime start1;
    private LocalDateTime end2;

    @BeforeEach
    public void runBefore() {
        owner = new User("Gio", "123456789", SportType.BADMINTON);
        user2 = new User("zio", "123", SportType.PADEL);

        facility = new CourtFacility("UBC NORTH REC", AreaLocation.VANCOUVER);
        court1 = new CourtUnit("B1", SportType.BADMINTON, LocalTime.of(8, 0),
                LocalTime.of(22, 0));
        facility.addCourt(court1);

        start = LocalDateTime.of(2025, 10, 3, 18, 0);
        end = LocalDateTime.of(2025, 10, 3, 19, 0);

        start1 = LocalDateTime.of(2025, 10, 3, 1, 0);
        end2 = LocalDateTime.of(2025, 10, 3, 2, 0);

        session1 = new Session(owner, SportType.BADMINTON, facility, court1, start, end);
        session2 = new Session(owner, SportType.PADEL, facility, court1, start1, end2);

        sessionManagerTest = new SessionManager();
    }

    @Test
    public void constructorTest() {
        assertTrue(sessionManagerTest.getActiveSession().isEmpty());
    }

    @Test
    public void addSessionTest() {
        sessionManagerTest.addSession(session1);
        assertTrue(sessionManagerTest.getActiveSession().contains(session1));
        assertEquals(1, sessionManagerTest.getActiveSession().size());

        sessionManagerTest.addSession(session2);
        assertTrue(sessionManagerTest.getActiveSession().contains(session2));
        assertEquals(2, sessionManagerTest.getActiveSession().size());
    }

    @Test
    public void removeSession() {
        sessionManagerTest.addSession(session1);
        assertTrue(sessionManagerTest.getActiveSession().contains(session1));

        // remove session called by the owner
        assertTrue(sessionManagerTest.removeSession(owner, session1));
        assertTrue(sessionManagerTest.getActiveSession().isEmpty());
        assertFalse(sessionManagerTest.getActiveSession().contains(session1));

        //remove session called by other user who is not the owner
        sessionManagerTest.addSession(session1);
        sessionManagerTest.addSession(session2);
        assertFalse(sessionManagerTest.removeSession(user2, session2));
        assertTrue(sessionManagerTest.getActiveSession().contains(session2));
        assertEquals(2, sessionManagerTest.getActiveSession().size());
    }

    @Test
    public void findSessionsBySportTest() {
        sessionManagerTest.addSession(session1);

        // Badminton session
        List<Session> sessionBadmintonFound = sessionManagerTest.findSessionsBySport(SportType.BADMINTON);
        assertEquals(1, sessionBadmintonFound.size());
        assertTrue(sessionBadmintonFound.contains(session1));

        // Padel session
        sessionManagerTest.addSession(session2);
        List<Session> sessionPadelFound = sessionManagerTest.findSessionsBySport(SportType.PADEL);
        assertEquals(1, sessionPadelFound.size());
        assertTrue(sessionPadelFound.contains(session2));
    }

    @Test
    public void joinSessionTest() {
        assertFalse(sessionManagerTest.joinSession(owner, session1));

        sessionManagerTest.addSession(session1);
        // still false since the owner is already a participant
        assertFalse(sessionManagerTest.joinSession(owner, session1));

        // false since session 2 is not active session
        assertFalse(sessionManagerTest.joinSession(user2, session2));

        // true since the user is not a participant yet and session 1 is active
        assertTrue(sessionManagerTest.joinSession(user2, session1));
    }

    @Test
    public void leaveSessionTest() {
        // false since user is the owner of the session
        sessionManagerTest.addSession(session1);
        assertFalse(sessionManagerTest.leaveSession(owner, session1));

        // false since user is not a participant
        assertFalse(sessionManagerTest.leaveSession(user2, session1));

        sessionManagerTest.joinSession(user2, session1);
        assertTrue(sessionManagerTest.leaveSession(user2, session1));
    }

}
