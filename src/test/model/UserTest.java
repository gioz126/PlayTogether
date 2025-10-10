package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.CourtUnavailableException;

public class UserTest {

    private User testUser;
    private CourtFacility facility;
    private CourtUnit court1;
    private LocalDateTime start;
    private LocalDateTime end;

    private Session session1;

    @BeforeEach
    public void runBefore() {
        testUser = new User("Gio", "123456789", SportType.BADMINTON);

        facility = new CourtFacility("UBC NORTH REC", AreaLocation.VANCOUVER);
        court1 = new CourtUnit("B1", SportType.BADMINTON, LocalTime.of(8, 0),
                LocalTime.of(22, 0));
        facility.addCourt(court1);

        start = LocalDateTime.of(2025, 10, 3, 18, 0);
        end = LocalDateTime.of(2025, 10, 3, 19, 0);

        session1 = new Session(testUser, SportType.BADMINTON, facility, court1, start, end);

    }

    @Test
    public void constructorTest() {
        assertEquals("Gio", testUser.getName());
        assertEquals("123456789", testUser.getContactNumber());
        assertEquals(SportType.BADMINTON, testUser.getSportInterest());

        assertTrue(testUser.getBookings().isEmpty());
        assertTrue(testUser.getSessionsJoined().isEmpty());
        assertTrue(testUser.getSessionsCreated().isEmpty());
        assertTrue(testUser.getCommunityJoined().isEmpty());
        assertTrue(testUser.getCommunityLed().isEmpty());
    }

    @Test
    public void bookCourtSuccessTest() throws CourtUnavailableException {
        Booking booking = testUser.bookCourt(facility, start, end);

        assertEquals(1, testUser.getBookings().size());
        assertTrue(testUser.getBookings().contains(booking));

        assertFalse(court1.isAvailable(start, end));
    }

    @Test
    public void bookCourtFailedTest() throws CourtUnavailableException {
        testUser.bookCourt(facility, start, end);

        assertThrows(CourtUnavailableException.class, () -> {
            testUser.bookCourt(facility, start, end);
        });

    }

    @Test
    public void createSessionTest() throws CourtUnavailableException {
        Booking booking = testUser.bookCourt(facility, start, end);

        Session session = testUser.createSession(testUser, SportType.BADMINTON, 0);

        // check inside created session
        assertEquals(1, testUser.getSessionsCreated().size());
        assertTrue(testUser.getSessionsCreated().contains(session));
        // check inside joined session
        assertEquals(1, testUser.getSessionsJoined().size());
        assertTrue(testUser.getSessionsJoined().contains(session));
    }

    @Test
    public void joinSessionTest() {

        // testUser is the owner, so he's a participant inside the session
        assertFalse(testUser.joinSession(session1));

        User user1 = new User("zio", "123", SportType.BADMINTON);
        assertTrue(user1.joinSession(session1));

        // user 1 tries to join again
        assertFalse(user1.joinSession(session1));
    }

    @Test
    public void createCommunityTest() {
        // user create a community
        Community communityCreated = testUser.createCommunity("Thunderbird", SportType.BADMINTON,
                AreaLocation.VANCOUVER, 50);
        assertTrue(testUser.getCommunityLed().contains(communityCreated));
        assertTrue(testUser.getCommunityJoined().contains(communityCreated));
        assertEquals(1, testUser.getCommunityLed().size());

        // check if the user creates a community, the user automaticaly becomes the
        // leader
        assertEquals(testUser, communityCreated.getCommunityLeader());
    }

    @Test
    public void joinCommunityTest() {
        User user1 = new User("zio", "123", SportType.BADMINTON);
        Community community = testUser.createCommunity("thunderbird", SportType.BADMINTON, AreaLocation.VANCOUVER, 3);

        // user join community in which the user already a member, return false
        assertFalse(testUser.joinCommunity(community));

        // user try to join the community twice
        assertTrue(user1.joinCommunity(community));
        assertFalse(user1.joinCommunity(community));

        // user join community already full
        Community fullCommunity = testUser.createCommunity("aaa", SportType.BADMINTON, AreaLocation.VANCOUVER, 1);
        User user2 = new User("aaa", "123", SportType.PADEL);
        assertFalse(user2.joinCommunity(fullCommunity));

        User newUser = new User("bbb", "123", SportType.BADMINTON);
        Community openCommunity = testUser.createCommunity("add", SportType.PADEL, AreaLocation.RICHMOND, 3);
        CommunityManager cm = new CommunityManager();
        cm.addCommunity(openCommunity);

        // user join community
        assertTrue(newUser.joinCommunity(openCommunity));

        // user leave using community manager, but does not remove from user's
        // communityJoined
        assertTrue(cm.leaveCommunity(newUser, openCommunity));

        // hasmember false, community full false, community joined true
        assertFalse(newUser.joinCommunity(openCommunity));
    }

}
