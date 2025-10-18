package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.PlayTogetherState;

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
        return null; // TODO stub
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return ""; //TODO stub
    }

    private PlayTogetherState parsePlayTogetherState(JSONObject jsonObject) {
        return null; //TODO stub
    }




}
