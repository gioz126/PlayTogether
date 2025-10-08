package model;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private List<Session> activeSession;

    // EFFECTS: constructs a new session manager with empty active session
    public SessionManager() {
        activeSession = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given session to the list of active session
    public void addSession(Session session) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes the given session from the list of active session
    public void removeSession(Session session) {
        // stub
    }

    // EFFECTS: returns a list of all currently active session with same sport as
    // given
    public List<Session> findSessionsBySport(SportType sport) {
        return null;
    }

    // MODIFIES: this, user, session
    // EFFECTS: if the session given is an active session and user is successfully
    // added return true. Return false if session is not active or user already a
    // participant
    public boolean joinSession(User user, Session session) {
        return false; // stub
    }

    // MODIFIES: this, user, session
    // EFFECTS: remove user from session if user is a participant. Returns true if
    // successful, false otherwise
    public boolean leaveSession(User user, Session session) {
        return false; // stub
    }

    // getters

    public List<Session> getActiveSession() {
        return new ArrayList<>(activeSession);
    }
}
