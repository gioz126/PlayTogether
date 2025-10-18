package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.CommunityManager;
import model.PlayTogetherState;
import model.SessionManager;
import model.UserManager;

//Reads PlayTogetherState from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PlayTogetherState from file and returns it; throws IOException
    // if an erorr occurs from reading data from file
    public PlayTogetherState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayTogetherState(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayTogetherState from JSON object
    private PlayTogetherState parsePlayTogetherState(JSONObject jsonObject) {
        UserManager userManager = new UserManager();
        CommunityManager communityManager = new CommunityManager();
        SessionManager sessionManager = new SessionManager();

        // JSONObject usersObject = jsonObject.getJSONObject("userManager");
        // JSONArray usersArray = usersObject.getJSONArray("users");
        // userManager.loadFromJson(usersArray);

        // JSONObject communitiesObject = jsonObject.getJSONObject("communityManager");
        // JSONArray communitiesArray = communitiesObject.getJSONArray("communities");
        // communityManager.loadFromJson(communitiesArray, userManager);

        // JSONObject sessionsObject = jsonObject.getJSONObject("sessionManager");
        // JSONArray sessionsArray = sessionsObject.getJSONArray("sessions");
        // sessionManager.loadFromJson(sessionsArray, userManager);

        PlayTogetherState state = new PlayTogetherState(userManager, communityManager, sessionManager);

        state.loadFromJson(jsonObject);

        return state;
    }

}
