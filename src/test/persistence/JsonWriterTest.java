package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CommunityManager;
import model.PlayTogetherState;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyPlayTogetherState() {
        try {
            PlayTogetherState state = new PlayTogetherState(new UserManager(), new CommunityManager(),
                    new SessionManager());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayTogether.json");
            writer.open();
            writer.write(state);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayTogether.json");
            PlayTogetherState readState = reader.read();

            assertEquals(0, readState.getUserManager().getAllUsers().size());
            assertEquals(0, readState.getCommunityManager().getActiveCommunities().size());
            assertEquals(0, readState.getSessionManager().getActiveSession().size());


        } catch (IOException e) {
            fail("IOException should not have been catched");
        }
    }

    @Test
    public void testWriterGeneralPlayTogetherState() {
        try {
            UserManager um = new UserManager();
            um.addUser(new User("gio", "1234", SportType.BADMINTON));
            um.addUser(new User("zio", "2871", SportType.PADEL));

            PlayTogetherState state = new PlayTogetherState(um, new CommunityManager(), new SessionManager());
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayTogether.json");
            writer.open();
            writer.write(state);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayTogether.json");
            PlayTogetherState readState = reader.read();

            List<User> users = readState.getUserManager().getAllUsers();
            assertEquals(2, users.size());
            assertEquals("gio", users.get(0).getName());
            assertEquals("1234", users.get(0).getContactNumber());
            assertEquals(SportType.BADMINTON, users.get(0).getSportInterest());

            assertEquals("zio", users.get(1).getName());
            assertEquals("2871", users.get(1).getContactNumber());
            assertEquals(SportType.PADEL, users.get(1).getSportInterest());

            assertEquals(0, readState.getCommunityManager().getActiveCommunities().size());
            assertEquals(0, readState.getSessionManager().getActiveSession().size());

        } catch (IOException e) {
            fail("IOException should not have been catched");
        }
    }

}
