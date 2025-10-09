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
        activeCommunity.add(community);
    }

    // MODIFIES: this
    // EFFECTS: removes the given community from the list of active community
    public void removeCommunity(Community community) {
        activeCommunity.remove(community);
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

}
