package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class SessionManager implements Writable {
    private List<Session> activeSession;

    // EFFECTS: constructs a new session manager with empty active session
    public SessionManager() {
        activeSession = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given session to the list of active session
    public void addSession(Session session) {
        activeSession.add(session);
    }

    // MODIFIES: this
    // EFFECTS: removes the given session from the list of active session if and
    // only if the user is the owner of the session, returns true if removed.
    // Returns false otherwise
    public boolean removeSession(User user, Session session) {
        if (user != session.getOwner()) {
            return false;
        } else {
            activeSession.remove(session);
            return true;
        }
    }

    // EFFECTS: returns a list of all currently active session with same sport as
    // given
    public List<Session> findSessionsBySport(SportType sport) {
        List<Session> result = new ArrayList<>();
        for (Session s : activeSession) {
            if (s.getSport() == sport) {
                result.add(s);
            }
        }
        return result;
    }

    // MODIFIES: this, user, session
    // EFFECTS: if the session given is an active session and user is successfully
    // added return true. Return false if session is not active or user already a
    // participant
    public boolean joinSession(User user, Session session) {
        if (!activeSession.contains(session) || session.getParticipant().contains(user)) {
            return false;
        } else {
            session.addParticipant(user);
            return true;
        }
    }

    // MODIFIES: this, user, session
    // EFFECTS: remove user from session if user is a participant, returns true if
    // successful. False if user is not a participant or is an owner
    public boolean leaveSession(User user, Session session) {
        if (!(session.getParticipant().contains(user)) || session.getOwner() == user) {
            return false;
        } else {
            session.removeParticipant(user);
            return true;
        }
    }

    // getters
    public List<Session> getActiveSession() {
        return new ArrayList<>(activeSession);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sessions", sessionsToJson());
        return json;
    }

    // EFFECTS: returns all active sessions as JSON Array
    private JSONArray sessionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Session s : activeSession) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: clears and loads sessions from JSON array
    public void loadFromJson(JSONArray jsonArray, UserManager userManager) {
        activeSession.clear();
        for (Object obj : jsonArray) {
            JSONObject jsonSession = (JSONObject) obj;
            addSession(parseSession(jsonSession, userManager));
        }
    }

    // EFFECTS: parse session from JSON object
    private Session parseSession(JSONObject json, UserManager userManager) {
        String ownerName = json.getString("ownerName");
        SportType sport = SportType.valueOf(json.getString("sport"));
        String facilityName = json.getString("facilityName");
        String courtId = json.getString("courtId");
        String start = json.getString("startDateTime");
        String end = json.getString("endDateTime");
        String description = json.getString("description");

        User owner = userManager.findUserByName(ownerName);
        // TODO can add null user if want (if owner not found)

        // TODO, facility and courtUnit still null
        Session session = new Session(owner, sport, null, null, java.time.LocalDateTime.parse(start),
                java.time.LocalDateTime.parse(end));

        return session;
    }
}
