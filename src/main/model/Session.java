package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonReader;
import persistence.Writable;

public class Session implements Writable {
    private User owner;
    private CourtFacility facility;
    private CourtUnit courtUnit;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SportType sport;
    private String description;
    private List<User> participants;

    // EFFECTS: creates new session with owner, type of sport, court
    // facility, court Unit, start time for session, end time for session, and no
    // description, and only the owner as initial participant.
    public Session(User owner, SportType sport, CourtFacility facility, CourtUnit courtUnit,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.owner = owner;
        this.sport = sport;
        this.facility = facility;
        this.courtUnit = courtUnit;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.participants = new ArrayList<>();
        this.participants.add(owner);
        this.description = "";
    }

    // MODIFIES: this
    // EFFECTS: adds a participants to the session if have not joined, return true
    // if added, false otherwise
    public boolean addParticipant(User user) {
        if (participants.contains(user)) {
            return false;
        } else {
            participants.add(user);
            return true;
        }

    }

    // MODIFIES: this
    // EFFECTS: removes a participant from the session, return true if removed,
    // false if participant not found
    public boolean removeParticipant(User user) {
        if (participants.contains(user)) {
            participants.remove(user);
            return true;
        } else {
            return false;
        }
    }

    // setters
    public void setDescription(String description) {
        this.description = description;
    }

    // getters
    public User getOwner() {
        return owner;
    }

    public SportType getSport() {
        return sport;
    }

    public CourtFacility getFacility() {
        return facility;
    }

    public CourtUnit getCourtUnit() {
        return courtUnit;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getParticipant() {
        return new ArrayList<>(participants);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ownerName", owner.getName());
        json.put("sport", sport.toString());
        json.put("facilityName", facility.getFacilityName());
        json.put("courtID", courtUnit.getcourtID());
        json.put("startDateTime", startDateTime.toString());
        json.put("endDateTime", endDateTime.toString());
        json.put("description", description);

        JSONArray participantsArray = new JSONArray();
        for(User u : participants) {
            participantsArray.put(u.getName());
        }
        json.put("participants", participantsArray);

        return json;
    }
}
