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
        assertEquals(2, c.getInt("maxMembers"));
        assertEquals("gio", c.getString("leaderName"));

        JSONArray members = c.getJSONArray("members");
        assertEquals(2, members.length());
        assertTrue(members.toList().contains("gio"));
        assertTrue(members.toList().contains("zio"));
    }

    @Test
    public void testLoadFromJson() {
        User member = new User("zio", "123", SportType.BADMINTON);
        UserManager userManager = new UserManager();

        userManager.addUser(owner);
        userManager.addUser(member);

        // make JSON manually
        JSONArray jsonArray = new JSONArray();

        JSONObject c1 = new JSONObject();
        c1.put("communityName", "Thunderbird");
        c1.put("sport", "BADMINTON");
        c1.put("location", "VANCOUVER");
        c1.put("maxMembers", 2);
        c1.put("leaderName", "gio");
        c1.put("members", new JSONArray().put("zio"));
        jsonArray.put(c1);

        testCommunityManager.loadFromJson(jsonArray, userManager);
        assertEquals(1, testCommunityManager.getActiveCommunities().size());
        Community loaded = testCommunityManager.getActiveCommunities().get(0);
        assertEquals("Thunderbird", loaded.getCommunityName());
        assertEquals(SportType.BADMINTON, loaded.getSport());
        assertEquals(AreaLocation.VANCOUVER, loaded.getLocation());
        assertEquals(2, loaded.getMaxMembers());
        assertEquals("gio", loaded.getCommunityLeader().getName());
    }

    @Test
    public void testLoadFromJsonNoMembersField() {
        User leader = new User("gio", "123", SportType.BADMINTON);
        UserManager userManager = new UserManager();
        userManager.addUser(leader);

        JSONArray jsonArray = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("communityName", "NoMembers");
        c1.put("sport", "BADMINTON");
        c1.put("location", "VANCOUVER");
        c1.put("maxMembers", 5);
        c1.put("leaderName", "gio");
        jsonArray.put(c1);

        testCommunityManager.loadFromJson(jsonArray, userManager);

        Community loaded = testCommunityManager.getActiveCommunities().get(0);
        assertEquals("NoMembers", loaded.getCommunityName());
        assertTrue(loaded.getMembers().contains(leader)); // only leader
        assertEquals(1, loaded.getMembers().size());
    }

    @Test
    public void testLoadFromJsonMemberNotFound() {
        User leader = new User("gio", "123", SportType.BADMINTON);
        UserManager userManager = new UserManager();
        userManager.addUser(leader);

        JSONArray jsonArray = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("communityName", "MemberNotFoundClub");
        c1.put("sport", "BADMINTON");
        c1.put("location", "RICHMOND");
        c1.put("maxMembers", 10);
        c1.put("leaderName", "gio");
        c1.put("members", new JSONArray().put("notFound")); // ghost not in userManager
        jsonArray.put(c1);

        testCommunityManager.loadFromJson(jsonArray, userManager);

        Community loaded = testCommunityManager.getActiveCommunities().get(0);
        assertEquals(1, loaded.getMembers().size()); // only leader, notFound ignored
    }

    @Test
    public void testLoadFromJsonDuplicateMemberIgnored() {
        User leader = new User("gio", "123", SportType.BADMINTON);
        User member = new User("zio", "555", SportType.BADMINTON);

        UserManager userManager = new UserManager();
        userManager.addUser(leader);
        userManager.addUser(member);

        JSONArray jsonArray = new JSONArray();
        JSONObject c1 = new JSONObject();
        c1.put("communityName", "DuplicateClub");
        c1.put("sport", "BADMINTON");
        c1.put("location", "VANCOUVER");
        c1.put("maxMembers", 5);
        c1.put("leaderName", "gio");
        c1.put("members", new JSONArray().put("zio").put("zio")); // duplicate
        jsonArray.put(c1);

        testCommunityManager.loadFromJson(jsonArray, userManager);

        Community loaded = testCommunityManager.getActiveCommunities().get(0);
        assertEquals(2, loaded.getMembers().size()); // leader + zio, not duplicated
    }

    @Test
    public void testReconnectUsersToCommunities() {
        // community vancouver owner is gio and has another participant
        User zio = new User("zio", "123", SportType.BADMINTON);
        communityVancouver.addMember(zio);

        // zio is already inside the community so not added back
        zio.addCommunityJoined(communityVancouver);

        testCommunityManager.addCommunity(communityVancouver);
        // check first gio's and zio's community is still empty
        assertFalse(owner.getCommunityLed().contains(communityVancouver));
        assertFalse(owner.getCommunityJoined().contains(communityVancouver));

        assertTrue(zio.getCommunityJoined().contains(communityVancouver));

        // reconnect it
        testCommunityManager.reconnectUsersToCommunities();

        // check gio's and zio's community
        assertTrue(owner.getCommunityLed().contains(communityVancouver));
        assertTrue(owner.getCommunityJoined().contains(communityVancouver));

        // check zio's list still 1 (not added again)
        assertEquals(1, zio.getCommunityJoined().size());
    }

    @Test
    public void testReconnectUsersToCommunitiesAlreadyInside() {
        Community school = new Community(owner, "UBC", SportType.BADMINTON, AreaLocation.VANCOUVER, 10);

        owner.addCommunityLed(school);
        // check the owner communityLed
        assertTrue(owner.getCommunityLed().contains(school));

        testCommunityManager.addCommunity(school);
        testCommunityManager.reconnectUsersToCommunities();

        // check the list still size 1
        assertEquals(1, owner.getCommunityLed().size());
    }

}
