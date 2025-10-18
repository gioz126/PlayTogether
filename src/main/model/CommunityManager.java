package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class CommunityManager implements Writable {
    private List<Community> activeCommunity;

    // EFFECTS: constrcuts a new community manager with empty active community
    public CommunityManager() {
        activeCommunity = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given community to the list of active community
    public void addCommunity(Community community) {
        activeCommunity.add(community);
    }

    // MODIFIES: this
    // EFFECTS: removes the given community from the list of active community if and
    // only if the given user is the leader, returns true after removed. Returns
    // false otherwise
    public boolean removeCommunity(User user, Community community) {
        if (community.getCommunityLeader() != user) {
            return false;
        } else {
            activeCommunity.remove(community);
            return true;
        }
    }

    // EFFECTS: returns a list of all currently active community with same sport as
    // given
    public List<Community> findCommunityBySport(SportType sport) {
        List<Community> communitySportFound = new ArrayList<>();

        for (Community community : activeCommunity) {
            if (community.getSport() == sport) {
                communitySportFound.add(community);
            }
        }
        return communitySportFound;
    }

    // EFFECTS: returns a list of all currently active community with same location
    // as given
    public List<Community> findCommunityByLocation(AreaLocation location) {
        List<Community> communityLocationFound = new ArrayList<>();

        for (Community community : activeCommunity) {
            if (community.getLocation() == location) {
                communityLocationFound.add(community);
            }
        }
        return communityLocationFound;
    }

    // MODIFIES: this, community
    // EFFECTS: if the community given is an active community and user is
    // successfully added, return true. Return false if community is not an active
    // community or user already a member of community.
    public boolean joinCommunity(User user, Community community) {
        if (!activeCommunity.contains(community)) {
            return false;
        } else {
            return community.addMember(user);
        }
    }

    // MODIFIES: this, community
    // EFFECTS: remove user from the community if user is a member, returns true if
    // successful. False if user is not a member of the community or is the leader
    // of the community (handled inside Community class) or community is not an
    // active community.
    public boolean leaveCommunity(User user, Community community) {
        if (!activeCommunity.contains(community)) {
            return false;
        } else {
            return community.removeMember(user);
        }
    }

    // getters
    public List<Community> getActiveCommunities() {
        return new ArrayList<>(activeCommunity);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("communities", communitiesToJson());
        return json;
    }

    // EFFECTS: return active communities as JSON array
    private JSONArray communitiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Community c : activeCommunity) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: clears and loads communities from JSON array
    public void loadFromJson(JSONArray jsonArray, UserManager userManager) {
        activeCommunity.clear();
        for (Object obj : jsonArray) {
            JSONObject next = (JSONObject) obj;
            addCommunity(parseCommunity(next, userManager));
        }
    }

    // EFFECTS: parses a community from JSON object
    private Community parseCommunity(JSONObject jsonObject, UserManager userManager) {
        String name = jsonObject.getString("communityName");
        SportType sport = SportType.valueOf(jsonObject.getString("sport"));
        AreaLocation location = AreaLocation.valueOf(jsonObject.getString("location"));
        int maxMembers = jsonObject.getInt("maxMembers");
        String leaderName = jsonObject.getString("leaderName");

        User leader = userManager.findUserByName(leaderName);

        Community community = new Community(leader, name, sport, location, maxMembers);

        return community;
    }

}
