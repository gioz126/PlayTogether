package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User testUser;

    @BeforeEach
    public void runBefore() {
        testUser = new User("Gio", "123456789", SportType.BADMINTON);
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
}
