package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents the full state of PlayTogether App
public class PlayTogetherState implements Writable {
    private UserManager userManager;
    private CommunityManager communityManager;
    private SessionManager sessionManager;

    // EFFECTS: contructs an app state given managers
    public PlayTogetherState(UserManager um, CommunityManager cm, SessionManager sm) {
        this.userManager = um;
        this.communityManager = cm;
        this.sessionManager = sm;
    }

    // getters
    public UserManager getUserManager() {
        return userManager;
    }

    public CommunityManager getCommunityManager() {
        return communityManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    //EFFECTS: converts this full app state to JSON
    @Override
    public JSONObject toJson() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toJson'");
    }

}
