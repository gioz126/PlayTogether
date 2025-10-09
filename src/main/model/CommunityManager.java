package model;

import java.util.ArrayList;
import java.util.List;

public class CommunityManager {
    private List<Community> activeCommunity;

    // EFFECTS: constrcuts a new community manager with empty active community
    public CommunityManager() {
        activeCommunity = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given community to the list of active community
    public void addCommunity(Community community) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes the given community from the list of active community
    public void removeCommunity(Community community) {
        // stub
    }

    // EFFECTS: returns a list of all currently active community with same sport as
    // given
    public List<Community> findCommunityBySport(SportType sport) {
        return null; // stub
    }

    // EFFECTS: returns a list of all currently active community with same location
    // as given
    public List<Community> findCommunityByLocation(AreaLocation location) {
        return null; // stub
    }

    // MODIFIES: this, community
    // EFFECTS: if the community given is an active community and user is
    // successfully added, return true. Return false if community is not an active
    // community or user already a member of community.
    public boolean joinCommunity(User user, Community community) {
        return false;
    }

    // MODIFIES: this, community
    // EFFECTS: remove user from the community if user is a member, returns true if
    // successful. False if user is not a member of the community or is the leader
    // of the community (handled inside Community class).
    public boolean leaveCommunity(User user, Community community) {
        return false;
    }

    // getters
    public List<Community> getActiveCommunities() {
        return new ArrayList<>(activeCommunity);
    }

}
