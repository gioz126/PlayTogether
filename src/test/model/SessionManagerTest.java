package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
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

        // remove session called by other user who is not the owner
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

    @Test
    public void testToJsonEmptyList() {
        JSONObject json = sessionManagerTest.toJson();
        assertTrue(json.has("sessions"));
        assertEquals(0, json.getJSONArray("sessions").length());
    }

    @Test
    public void testToJsonOneSessio() {
        sessionManagerTest.addSession(session1);

        JSONObject json = sessionManagerTest.toJson();
        JSONArray array = json.getJSONArray("sessions");

        assertEquals(1, array.length());
        JSONObject sessionJson = array.getJSONObject(0);
        assertEquals("BADMINTON", sessionJson.getString("sport"));
        assertEquals("Gio", sessionJson.getString("ownerName"));
    }

    @Test
    public void testLoadJson() {
        List<CourtFacility> facilities = new ArrayList<>();
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility ubcFacility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        ubcFacility.addCourt(new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facilityManager.addFacility(ubcFacility);

        UserManager userManager = new UserManager();
        userManager.addUser(owner);
        JSONObject sessionJson = new JSONObject();

        sessionJson.put("ownerName", "Gio");
        sessionJson.put("sport", "BADMINTON");
        sessionJson.put("facilityName", "UBC Courts");
        sessionJson.put("courtId", "Court1");
        sessionJson.put("startDateTime", "2025-10-18T10:00");
        sessionJson.put("endDateTime", "2025-10-18T12:00");
        sessionJson.put("description", "Morning match");

        JSONArray jsonArray = new JSONArray().put(sessionJson);

        sessionManagerTest.loadFromJson(jsonArray, userManager, facilityManager);

        assertEquals(1, sessionManagerTest.getActiveSession().size());
        Session loaded = sessionManagerTest.getActiveSession().get(0);
        assertEquals("Gio", loaded.getOwner().getName());
        assertEquals(SportType.BADMINTON, loaded.getSport());
        assertEquals(LocalDateTime.of(2025, 10, 18, 10, 0), loaded.getStartDateTime());
        assertEquals(LocalDateTime.of(2025, 10, 18, 12, 0), loaded.getEndDateTime());

    }

    @Test
    public void testLoadJsonFacilityNotFound() {
        // no facilities added
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        UserManager userManager = new UserManager();
        userManager.addUser(owner);

        JSONObject json = new JSONObject();
        json.put("ownerName", "Gio");
        json.put("sport", "BADMINTON");
        json.put("facilityName", "NonExisting");
        json.put("courtId", "Court1");
        json.put("startDateTime", "2025-10-18T10:00");
        json.put("endDateTime", "2025-10-18T12:00");
        json.put("description", "No facility");

        JSONArray jsonArray = new JSONArray().put(json);

        sessionManagerTest.loadFromJson(jsonArray, userManager, facilityManager);

        Session s = sessionManagerTest.getActiveSession().get(0);
        assertNull(s.getFacility());
        assertNull(s.getCourtUnit());
    }

    @Test
    public void testLoadJsonCourtNotFound() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility ubc = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        facilityManager.addFacility(ubc); // facility exists but has no matching court

        UserManager userManager = new UserManager();
        userManager.addUser(owner);

        JSONObject json = new JSONObject();
        json.put("ownerName", "Gio");
        json.put("sport", "BADMINTON");
        json.put("facilityName", "UBC Courts");
        json.put("courtId", "NonExistingCourt");
        json.put("startDateTime", "2025-10-18T10:00");
        json.put("endDateTime", "2025-10-18T12:00");
        json.put("description", "Court not found");

        JSONArray arr = new JSONArray().put(json);

        sessionManagerTest.loadFromJson(arr, userManager, facilityManager);
        Session s = sessionManagerTest.getActiveSession().get(0);
        assertNotNull(s.getFacility());
        assertNull(s.getCourtUnit());
    }

    @Test
    public void testLoadJsonWithParticipantsUserFound() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility ubc = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        ubc.addCourt(new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facilityManager.addFacility(ubc);

        UserManager userManager = new UserManager();
        userManager.addUser(owner);
        User zio = new User("zio", "123", SportType.BADMINTON);
        userManager.addUser(zio);

        JSONObject json = new JSONObject();
        json.put("ownerName", "Gio");
        json.put("sport", "BADMINTON");
        json.put("facilityName", "UBC Courts");
        json.put("courtId", "Court1");
        json.put("startDateTime", "2025-10-18T10:00");
        json.put("endDateTime", "2025-10-18T12:00");
        json.put("description", "Morning");
        json.put("participants", new JSONArray().put("zio"));

        JSONArray arr = new JSONArray().put(json);

        sessionManagerTest.loadFromJson(arr, userManager, facilityManager);
        Session s = sessionManagerTest.getActiveSession().get(0);
        assertTrue(s.getParticipant().contains(zio));
    }

    @Test
    public void testLoadJsonWithParticipantsUserNotFound() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility ubc = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        ubc.addCourt(new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facilityManager.addFacility(ubc);

        UserManager userManager = new UserManager();
        userManager.addUser(owner);

        JSONObject json = new JSONObject();
        json.put("ownerName", "Gio");
        json.put("sport", "BADMINTON");
        json.put("facilityName", "UBC Courts");
        json.put("courtId", "Court1");
        json.put("startDateTime", "2025-10-18T10:00");
        json.put("endDateTime", "2025-10-18T12:00");
        json.put("description", "Morning");
        json.put("participants", new JSONArray().put("UnknownUser"));

        JSONArray arr = new JSONArray().put(json);

        sessionManagerTest.loadFromJson(arr, userManager, facilityManager);
        Session s = sessionManagerTest.getActiveSession().get(0);

    }

    @Test
    public void testReconnectUsersToSession() {
        sessionManagerTest.addSession(session1);
        // owner is not inside
        assertFalse(owner.getSessionsCreated().contains(session1));
        assertFalse(owner.getSessionsJoined().contains(session1));

        // session 1 has participant
        session1.addParticipant(user2);

        // user2 already recognize this session
        user2.addSessionsJoined(session1);

        assertTrue(user2.getSessionsJoined().contains(session1));

        // reconnect session to its owner
        sessionManagerTest.reconnectUsersToSession();

        assertTrue(owner.getSessionsCreated().contains(session1));
        assertTrue(owner.getSessionsJoined().contains(session1));

        // doesn't add again to user2
        assertEquals(1, user2.getSessionsJoined().size());
    }

    @Test
    public void testReconnectUsersToSessionAlreadyInside() {
        sessionManagerTest.addSession(session1);
        // owner is already inside
        owner.addSessionCreated(session1);
        owner.addSessionsJoined(session1);

        assertTrue(owner.getSessionsJoined().contains(session1));
        assertTrue(owner.getSessionsJoined().contains(session1));

        // reconnect
        sessionManagerTest.reconnectUsersToSession();
        // doesn't add again
        assertEquals(1, owner.getSessionsJoined().size());
        assertEquals(1, owner.getSessionsCreated().size());
    }

}
