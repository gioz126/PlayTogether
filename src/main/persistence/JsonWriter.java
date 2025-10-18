package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.PlayTogetherState;

//Writes JSON representation of playTogetherState to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened
    public void open() throws FileNotFoundException {
        // TODO stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PlayTogetherState to file
    public void write(PlayTogetherState state) {
        // TODO stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // TODO stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        // TODO stub
    }

}
