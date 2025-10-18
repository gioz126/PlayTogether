package persistence;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.CommunityManager;
import model.PlayTogetherState;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

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

            // TODO JSON reader

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

            //TODO JSON reader

        } catch (IOException e) {
            fail("IOException should not have been catched");
        }
    }

}
