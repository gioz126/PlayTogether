package ui;

import java.util.Scanner;

import model.AreaLocation;
import model.CommunityManager;
import model.CourtFacility;
import model.SessionManager;
import model.User;
import model.UserManager;

public class PlayTogetherApp {
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final CommunityManager communityManager;
    private final CourtFacility facility;
    private final Scanner input;
    private User currentUser;

    public PlayTogetherApp() {
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();
        
        //creates facility and add courts
        facility = new CourtFacility("UBC North Recreation", );
    }
}
