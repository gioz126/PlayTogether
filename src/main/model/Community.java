package model;

import java.util.ArrayList;
import java.util.List;

public class Community {
    private String communityName;
    private SportType sport;
    private AreaLocation location;
    private int maxMembers;
    private User communityLeader;
    private List<User> communityMember;

    // EFFECTS: creates community with given name, sport, location, max member of
    // the community, community leader is the user who created it, and 1 member of
    // community which is the leader
    public Community(User leaderUser, String name, SportType sport, AreaLocation location, int maxMembers) {
        this.communityName = name;
        this.sport = sport;
        this.location = location;
        this.maxMembers = maxMembers;
        this.communityLeader = leaderUser;
        this.communityMember = new ArrayList<>();
        communityMember.add(leaderUser);
    }

    // MODIFIES: this
    // EFFETCS: if user not already a member and community is not full, adds user to
    // the community and return true
    public boolean addMember(User user) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: removes user from the community member if present. Return true if
    // removed, false otherwise. Cannot remove leader from the community, will
    // return false if leader wants to be removed
    public boolean removerMember(User user) {
        return false; // stub
    }

    // EFFECTS: returns true if user is a member in this community
    public boolean hasMember(User user) {
        return false; // stub
    }

    // EFFECTS: returns true if the community has reached maxMembers
    public boolean isFull() {
        return false;
    }

    // getters
    public String getCommunityName() {
        return communityName;
    }

    public SportType getSport() {
        return sport;
    }

    public AreaLocation getLocation() {
        return location;
    }

    public List<User> getMembers() {
        return new ArrayList<>(communityMember);
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public User getCommunityLeader() {
        return communityLeader;
    }

}
