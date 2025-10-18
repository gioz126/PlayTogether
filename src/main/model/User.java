package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import exception.CourtUnavailableException;
import persistence.Writable;

public class User implements Writable{
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

    // REQUIRES: this user has at least one booking, index is a valid index in this
    // user's list of booking
    // MODIFIES: this, session
    // EFFECTS: creates a new play session using the booking at the given index
    // (starts at 0), adds the created session to this user's created session and
    // session joined, and returns the created
    // session
    public Session createSession(User user, SportType sport, int bookingIndex) {
        Booking booking = bookings.get(bookingIndex);
        Session session = new Session(user, sport, booking.getFacility(), booking.getCourt(), booking.getStartTime(),
                booking.getEndTime());
        sessionsCreated.add(session);
        sessionsJoined.add(session);
        return session;
    }

    // MODIFIES: this, session
    // EFFECTS: if this user is not already a participant of the given session, adds
    // this user to the session's list of participants and adds the session to this
    // user's list of session joined. Returns true if successfully joined, false
    // otherwise
    public boolean joinSession(Session session) {
        if (session.getParticipant().contains(this)) {
            return false;
        } else {
            session.addParticipant(this);
            sessionsJoined.add(session);
            return true;
        }
    }

    // MODIFIES: this, community
    // EFFECTS: users create community with community name, sport, area, and max
    // number of members. Add community to community led to this user and add
    // community to communityJoined to this user, return the
    // community
    public Community createCommunity(String communityName, SportType sport, AreaLocation area, int maxMembers) {
        Community community = new Community(this, communityName, sport, area, maxMembers);
        communityLed.add(community);
        communityJoined.add(community);
        return community;
    }

    // MODIFIES: this, community
    // EFFECTS: if this user is not a member of the given community and the
    // community is not full and this user has not joined the specific community
    // given, adds this user to the community, add givencommunity to community
    // joined and returns true. Otherwise, returns false
    public boolean joinCommunity(Community community) {
        if (community.hasMember(this) || community.isFull() || communityJoined.contains(community)) {
            return false;
        } else {
            community.addMember(this);
            communityJoined.add(community);
            return true;
        }
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("contactNumber", contactNumber);
        json.put("sportInterest", sportInterest.toString());

        json.put("bookings", listToJson(bookings));
        json.put("sessionsJoined", listToJson(sessionsJoined));
        json.put("sessionsCreated", listToJson(sessionsCreated));
        json.put("communityJoined", listToJson(communityJoined));
        json.put("communityLed", listToJson(communityLed));

    }

    //EFFECTS: converts a list of Writable objects to JSON array
    private <T extends Writable> org.json.JSONArray listToJson(List<T> list) {
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for(T items : list) {
            jsonArray.put(items.toJson());
        }
        return jsonArray;
    }

}
