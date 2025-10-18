package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonReader;
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

    // EFFECTS: converts this full app state to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userManager", userManager.toJson());
        json.put("communityManager", communityManager.toJson());
        json.put("sessionManager", sessionManager.toJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: loads the state of the app from JSON object
    public void loadFromJson(JSONObject jsonObject) {
        JSONObject userObject = jsonObject.getJSONObject("userManager");
        JSONArray userArray = userObject.getJSONArray("users");
        userManager.loadFromJson(userArray);

        JSONObject communitiesObject = jsonObject.getJSONObject("communityManager");
        JSONArray communitiesArray = jsonObject.getJSONArray("communities");
        communityManager.loadFromJson(communitiesArray, userManager);

        JSONObject sessionsObject = jsonObject.getJSONObject("sessionManager");
        JSONArray sessionsArray = jsonObject.getJSONArray("sessions");
        sessionManager.loadFromJson(sessionsArray, userManager);
    }
}
