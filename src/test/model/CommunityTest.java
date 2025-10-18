package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class CommunityTest {
    private User owner;
    private Community testCommunity;
    private User user1;

    @BeforeEach
    public void runBefore() {
        owner = new User("Gio", "123456789", SportType.BADMINTON);
        user1 = new User("zio", "1234", SportType.BADMINTON);

        testCommunity = new Community(owner, "Thunderbird", SportType.BADMINTON, AreaLocation.VANCOUVER, 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Thunderbird", testCommunity.getCommunityName());
        assertEquals(SportType.BADMINTON, testCommunity.getSport());
        assertEquals(AreaLocation.VANCOUVER, testCommunity.getLocation());
        assertEquals(2, testCommunity.getMaxMembers());
        assertEquals(owner, testCommunity.getCommunityLeader());
        assertEquals(1, testCommunity.getMembers().size());
        assertTrue(testCommunity.getMembers().contains(owner));
    }

    @Test
    public void testAddMember() {
        // user is not inside the community and the member is only 1, which is less than
        // max members 2
        testCommunity.addMember(user1);
        assertEquals(2, testCommunity.getMembers().size());
        assertTrue(testCommunity.getMembers().contains(user1));

        // max member is 2, community members is already 2, add another user should be
        // false / cannot
        User user2 = new User("userfail", "123", SportType.PADEL);
        assertFalse(testCommunity.addMember(user2));

        // user is already inside the community
        assertFalse(testCommunity.addMember(user1));
    }

    @Test
    public void removerMemberTest() {
        testCommunity.addMember(user1);

        //remove leader should return false
        assertFalse(testCommunity.removeMember(owner));

        //remove user who is not inside the community should return false
        User user2 = new User("userfail", "123", SportType.PADEL);
        assertFalse(testCommunity.removeMember(user2));

        //remove user who is inside the community and is not leader should return true
        assertTrue(testCommunity.removeMember(user1));
    }
}
