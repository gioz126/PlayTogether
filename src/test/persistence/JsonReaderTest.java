package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.PlayTogetherState;
import model.User;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayTogetherState state = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test 
    public void testReaderEmptyPlayTogeterState() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayTogether.json");
        try {
            PlayTogetherState state = reader.read();
            assertNotNull(state);
            assertEquals(0, state.getUserManager().getAllUsers().size());
            assertEquals(0, state.getCommunityManager().getActiveCommunities().size());
            assertEquals(0, state.getSessionManager().getActiveSession().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralPlayTogetherState() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayTogether.json");
        try {
            PlayTogetherState state = reader.read();
            assertNotNull(state);

            List<User> users = state.getUserManager().getAllUsers();
            assertEquals(2, users.size());
            assertEquals("gio", users.get(0).getName());
            assertEquals("zio", users.get(1).getName());

            assertEquals(0, state.getCommunityManager().getActiveCommunities().size());
            assertEquals(0, state.getSessionManager().getActiveSession().size());
        } catch(IOException e) {
            fail("Couldn't read from file");
        }
    }
}
