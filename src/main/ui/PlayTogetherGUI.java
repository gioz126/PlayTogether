package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import model.CommunityManager;
import model.CourtFacilityManager;
import model.PlayTogetherState;
import model.SessionManager;
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

        //setup managers
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();
        facilityManager = new CourtFacilityManager();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE, facilityManager);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayTogetherGUI::new);
    }

}
