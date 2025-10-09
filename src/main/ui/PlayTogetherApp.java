package ui;

import java.time.LocalTime;
import java.util.Scanner;

import model.AreaLocation;
import model.CommunityManager;
import model.CourtFacility;
import model.CourtUnit;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

public class PlayTogetherApp {
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final CommunityManager communityManager;
    private final CourtFacility facility;
    private final Scanner input;
    private User currentUser;

    // EFFECTS: constructs and initializes the PlayTogether app
    public PlayTogetherApp() {
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();

        // creates facility and add courts
        facility = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        setupCourts();

        input = new Scanner(System.in);
    }

    // EFFECTS: setup court unit for court facility
    public void setupCourts() {
        facility.addCourt(new CourtUnit("Badminton 1", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facility.addCourt(new CourtUnit("Badminton 2", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));
    }
}
