package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.AreaLocation;
import model.CommunityManager;
import model.CourtFacility;
import model.CourtUnit;
import model.PlayTogetherState;
import model.Session;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest {

    private List<CourtFacility> facilities;

    @BeforeEach
    public void runBefore() {
        facilities = new ArrayList<>();

        CourtFacility badmintonVancouver = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        badmintonVancouver.addCourt(new CourtUnit("Badminton 1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));

        CourtFacility padelRichmond = new CourtFacility("Badminton Richmond", AreaLocation.RICHMOND);
        padelRichmond.addCourt(new CourtUnit("Padel 1", SportType.PADEL,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));

        facilities.add(badmintonVancouver);
        facilities.add(padelRichmond);
    }

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
                    new SessionManager(), facilities);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayTogether.json");
            writer.open();
            writer.write(state);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayTogether.json", facilities);
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
            User user1 = new User("gio", "1234", SportType.BADMINTON);
            User user2 = new User("zio", "2871", SportType.PADEL);
            um.addUser(user1);
            um.addUser(user2);

            SessionManager sm = new SessionManager();
            Session session = new Session(user1, SportType.BADMINTON, facilities.get(0),
                    facilities.get(0).getCourts().get(0),
                    LocalDateTime.of(2025, 10, 20, 14, 0),
                    LocalDateTime.of(2025, 10, 20, 15, 0));
            sm.addSession(session);

            PlayTogetherState state = new PlayTogetherState(um, new CommunityManager(), sm,
                    facilities);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayTogether.json");
            writer.open();
            writer.write(state);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayTogether.json", facilities);
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

            // verify the session
            List<Session> sessions = readState.getSessionManager().getActiveSession();
            assertEquals(1, sessions.size());
            Session readSession = sessions.get(0);
            assertEquals("gio", readSession.getOwner().getName());
            assertEquals(SportType.BADMINTON, readSession.getSport());
            assertEquals("UBC North Recreation", readSession.getFacility().getFacilityName());
            assertEquals("Badminton 1", readSession.getCourtUnit().getcourtID());
            assertEquals(LocalDateTime.of(2025, 10, 20, 14, 0), readSession.getStartDateTime());
            assertEquals(LocalDateTime.of(2025, 10, 20, 15, 0), readSession.getEndDateTime());

        } catch (IOException e) {
            fail("IOException should not have been catched");
        }
    }

}
