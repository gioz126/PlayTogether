package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String contactNumber;
    private SportType sportInterest;

    private List<Booking> bookings;
    private List<Session> sessionsJoined;
    private List<Session> sessionsCreated;
    private List<Community> communityJoined;
    private List<Community> communityLed;

    // EFFECTS: Create a user with given name, contact number, and sport interest;
    // initializes empty booking list, empty session joined, empty session created,
    // empty community joined, empty community leader
    public User(String name, String contactNumber, SportType sportInterest) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.sportInterest = sportInterest;
        this.bookings = new ArrayList<>();
        this.sessionsJoined = new ArrayList<>();
        this.sessionsCreated = new ArrayList<>();
        this.communityJoined = new ArrayList<>();
        this.communityLed = new ArrayList<>();
    }

    // REQUIRES: court is available at [start time, end time]
    // MODIFIES: this, court
    // EFFECTS: books the court at given time slot,
    // add booking to this user, return the booking
    public Booking bookCourt(CourtFacility c, LocalDateTime startTime, LocalDateTime endTime) {
        return null;
    }

    // REQUIRES: this user has already booked the court and time slot
    // MODIFIES: this, session
    // EFFECTS: open play session with time slot same as court booked
    // add session opened to this user, return the session
    public Session createSession(User user, String sport, CourtFacility court, LocalDateTime startTime, LocalDateTime endTime) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: users join the session opened
    public void joinSession(Session session) {
        // stub
    }

    // MODIFIES: this, community
    // EFFECTS: users create community
    // add community to community leader to this user, return the community
    public Community createCommunity(String communityName, String sport, String area, int maxMembers) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: users join community
    // add community to community joined to this user
    public void joinCommunity(String prefSport, String prefArea) {
        // stub
    }

    // getters
    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public SportType getSportInterest() {
        return sportInterest;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Session> getSessionsJoined() {
        return sessionsJoined;
    }

    public List<Session> getSessionsCreated() {
        return sessionsCreated;
    }

    public List<Community> getCommunityJoined() {
        return communityJoined;
    }

    public List<Community> getCommunityLed() {
        return communityLed;
    }

}
