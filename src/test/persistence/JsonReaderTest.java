package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.AreaLocation;
import model.CourtFacility;
import model.CourtUnit;
import model.PlayTogetherState;
import model.Session;
import model.SportType;
import model.User;

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest {

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
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json", facilities);
        try {
            PlayTogetherState state = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyPlayTogeterState() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayTogether.json", facilities);
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
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayTogether.json", facilities);
        try {
            PlayTogetherState state = reader.read();
            assertNotNull(state);

            List<User> users = state.getUserManager().getAllUsers();
            assertEquals(2, users.size());
            assertEquals("gio", users.get(0).getName());
            assertEquals("zio", users.get(1).getName());

            assertEquals(0, state.getCommunityManager().getActiveCommunities().size());

            assertEquals(1, state.getSessionManager().getActiveSession().size());

            Session loadedSession = state.getSessionManager().getActiveSession().get(0);
            assertEquals(SportType.BADMINTON, loadedSession.getSport());
            assertEquals("UBC North Recreation", loadedSession.getFacility().getFacilityName());
            assertEquals("Badminton 1", loadedSession.getCourtUnit().getcourtID());
            assertEquals("gio", loadedSession.getOwner().getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
