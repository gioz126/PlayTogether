package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.io.IOException;
import java.time.LocalTime;

import model.AreaLocation;
import model.CommunityManager;
import model.CourtFacility;
import model.CourtFacilityManager;
import model.CourtUnit;
import model.PlayTogetherState;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;
import persistence.JsonReader;
import persistence.JsonWriter;

public class PlayTogetherGUI extends JFrame {

    private static final String JSON_STORE = "./data/playtogether.json";

    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final CommunityManager communityManager;
    private final CourtFacilityManager facilityManager;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private User currentUser;
    private PlayTogetherState appState;

    public PlayTogetherGUI() {
        super("🏸 PlayTogether");

        // setup frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 650);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        // setup managers
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();
        facilityManager = new CourtFacilityManager();

        setupCourts();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE, facilityManager);

        // ask to load
        askToLoadState();

        // ask user to login or register
        askUserToLogin();

    }

    private void askUserToLogin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'askUserToLogin'");
    }

    private void askToLoadState() {
        int choice = JOptionPane.showConfirmDialog(this, "Would you like to load your previous saved data?",
                "Load data", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            loadAppState();
        }
    }

    // EFFECTS: load app state from JSON file
    private void loadAppState() {
        try {
            PlayTogetherState loadedState = jsonReader.read();
            this.appState = loadedState;

            //syncing back
            syncManagersFromState(loadedState);

            JOptionPane.showMessageDialog(this, "✅ Loaded from: " + JSON_STORE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this ,"⚠️ Unable to read from " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: update all managers from loaded PlayTogetherState
    private void syncManagersFromState(PlayTogetherState loadedState) {
        userManager.loadFromJson(loadedState.getUserManager().toJson().getJSONArray("users"),
                loadedState.getFacilityManager());
        communityManager.loadFromJson(loadedState.getCommunityManager().toJson().getJSONArray("communities"),
                userManager);
        sessionManager.loadFromJson(loadedState.getSessionManager().toJson().getJSONArray("sessions"),
                userManager, loadedState.getFacilityManager());

        // reconnect session and community to its user
        sessionManager.reconnectUsersToSession();
        communityManager.reconnectUsersToCommunities();

        // sync court reservation back
        userManager.restoreCourtReservations();
    }

    private void setupCourts() {
        CourtFacility badmintonVancouver = new CourtFacility("UBC North Recreation Badminton", AreaLocation.VANCOUVER);
        badmintonVancouver.addCourt(new CourtUnit("Badminton 1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));
        badmintonVancouver.addCourt(new CourtUnit("Badminton 2", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));

        CourtFacility padelRichmond = new CourtFacility("Padel Richmond", AreaLocation.RICHMOND);
        padelRichmond.addCourt(new CourtUnit("Padel 1", SportType.PADEL, LocalTime.of(8, 0), LocalTime.of(22, 0)));
        padelRichmond.addCourt(new CourtUnit("Padel 2", SportType.PADEL, LocalTime.of(8, 0), LocalTime.of(22, 0)));

        facilityManager.addFacility(badmintonVancouver);
        facilityManager.addFacility(padelRichmond);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayTogetherGUI::new);
    }

}
