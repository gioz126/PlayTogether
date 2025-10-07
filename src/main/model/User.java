package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exception.CourtUnavailableException;

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

    // REQUIRES: facility has at least one available court at given [start time, end
    // time]
    // MODIFIES: this, courtUnit
    // EFFECTS: finds an available court in the facility, creates a booking for this
    // user, reserves the court, adds booking to the user, return booking
    public Booking bookCourt(CourtFacility facility, LocalDateTime startTime, LocalDateTime endTime)
            throws CourtUnavailableException {
        CourtUnit availableCourt = facility.findAvailableCourt(startTime, endTime);

        if (availableCourt == null) {
            throw new CourtUnavailableException("No courts available at given time");
        }
        Booking booking = new Booking(this, facility, availableCourt, startTime, endTime);
        bookings.add(booking);
        availableCourt.reserve(booking);
        return booking;
    }

    // REQUIRES: this user has already booked the court and time slot
    // MODIFIES: this, session
    // EFFECTS: creates a new play session for this user with time slot and court
    // same as court booked inside booking. Add session created to this user, return
    // the created session
    public Session createSession(User user, String sport, Booking booking) {
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
