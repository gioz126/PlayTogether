package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class CommunityManagerTest {
    private Community communityVancouver;
    private Community communityRichmond;
    private CommunityManager testCommunityManager;
    private User owner;

    @BeforeEach
    public void runBefore() {
        owner = new User("gio", "123", SportType.BADMINTON);

        communityVancouver = new Community(owner, "Thunderbird", SportType.BADMINTON, AreaLocation.VANCOUVER, 2);
        communityRichmond = new Community(owner, "Richmond", SportType.PADEL, AreaLocation.RICHMOND, 2);
        testCommunityManager = new CommunityManager();
    }

    @Test
    public void constructorTest() {
        assertTrue(testCommunityManager.getActiveCommunities().isEmpty());
    }

    @Test
    public void addCommunityTest() {
        // add 1 community
        testCommunityManager.addCommunity(communityVancouver);
        assertFalse(testCommunityManager.getActiveCommunities().isEmpty());
        assertTrue(testCommunityManager.getActiveCommunities().contains(communityVancouver));
        assertEquals(1, testCommunityManager.getActiveCommunities().size());

        // add another community, total = 2
        testCommunityManager.addCommunity(communityRichmond);
        assertTrue(testCommunityManager.getActiveCommunities().contains(communityRichmond));
        assertEquals(2, testCommunityManager.getActiveCommunities().size());
    }

    @Test
    public void removeCommunityTest() {
        // add community vancouver first
        testCommunityManager.addCommunity(communityVancouver);

        // remove community vancouver but not the owner (can't remove, return false)
        User user1 = new User("aaa", "123", SportType.BADMINTON);
        assertFalse(testCommunityManager.removeCommunity(user1, communityVancouver));
        assertTrue(testCommunityManager.getActiveCommunities().contains(communityVancouver));
        assertEquals(1, testCommunityManager.getActiveCommunities().size());

        // remove community vancouver with owner as the user
        assertTrue(testCommunityManager.removeCommunity(owner, communityVancouver));
        assertFalse(testCommunityManager.getActiveCommunities().contains(communityVancouver));
        assertTrue(testCommunityManager.getActiveCommunities().isEmpty());
    }

    @Test
    public void findCommunityBySportTest() {
        // find badminton community
        testCommunityManager.addCommunity(communityVancouver);
        List<Community> communityBadmintonFound = testCommunityManager.findCommunityBySport(SportType.BADMINTON);
        assertEquals(1, communityBadmintonFound.size());
        assertTrue(communityBadmintonFound.contains(communityVancouver));

        // find padel community
        testCommunityManager.addCommunity(communityRichmond);
        List<Community> communityPadelFound = testCommunityManager.findCommunityBySport(SportType.PADEL);
        assertEquals(1, communityPadelFound.size());
        assertTrue(communityPadelFound.contains(communityRichmond));
    }

    @Test
    public void findCommunityByLocationTest() {
        // find Vancouver community
        testCommunityManager.addCommunity(communityVancouver);
        List<Community> communityVancouverFound = testCommunityManager.findCommunityByLocation(AreaLocation.VANCOUVER);
        assertEquals(1, communityVancouverFound.size());
        assertTrue(communityVancouverFound.contains(communityVancouver));

        // find Richmond community
        testCommunityManager.addCommunity(communityRichmond);
        List<Community> communityRichmondFound = testCommunityManager.findCommunityByLocation(AreaLocation.RICHMOND);
        assertEquals(1, communityRichmondFound.size());
        assertTrue(communityRichmondFound.contains(communityRichmond));
    }

    @Test
    public void joinCommunityTest() {
        // user wants to join community that doesn't exist
        assertFalse(testCommunityManager.joinCommunity(owner, communityVancouver));

        // user is already a member of the community
        testCommunityManager.addCommunity(communityVancouver);
        assertFalse(testCommunityManager.joinCommunity(owner, communityVancouver));

        // user is not a member and community is active
        User userSuccess = new User("zio", "123", SportType.BADMINTON);
        assertTrue(testCommunityManager.joinCommunity(userSuccess, communityVancouver));
    }

    @Test
    public void leaveCommunityTest() {
        // community is not an active community (non-existing) and user wants to leave
        assertFalse(testCommunityManager.leaveCommunity(owner, communityVancouver));

        // user is the owner of the community
        testCommunityManager.addCommunity(communityVancouver);
        assertFalse(testCommunityManager.leaveCommunity(owner, communityVancouver));

        // user is not a participant
        User userFailed = new User("zio", "123", SportType.BADMINTON);
        assertFalse(testCommunityManager.leaveCommunity(userFailed, communityVancouver));

        // user is not a member yet and not the leader of the community
        testCommunityManager.joinCommunity(userFailed, communityVancouver);
        assertTrue(testCommunityManager.leaveCommunity(userFailed, communityVancouver));
    }

    @Test
    public void testToJson() {
        User member = new User("zio", "123", SportType.BADMINTON);
        communityVancouver.addMember(member);
        testCommunityManager.addCommunity(communityVancouver);

        JSONObject json = testCommunityManager.toJson();

        assertTrue(json.has("communities"));
        JSONArray array = json.getJSONArray("communities");
        assertEquals(1, array.length());

        JSONObject c = array.getJSONObject(0);
        assertEquals("Thunderbird", c.getString("communityName"));
        assertEquals("BADMINTON", c.getString("sport"));
        assertEquals("VANCOUVER", c.getString("location"));
        assertEquals(5, c.getString("maxMembers"));
        assertEquals("Gio", c.getString("leaderName"));

        JSONArray members = c.getJSONArray("members");
        assertEquals(2, members.length());
        assertTrue(members.toList().contains("Gio"));
        assertTrue(members.toList().contains("Zio"));
    }

}
