package ui;

import javax.swing.JPanel;

import model.CourtFacilityManager;
import model.SessionManager;
import model.User;

public class SessionPanel extends JPanel {

    private User user;
    private SessionManager sessionManager;
    private CourtFacilityManager facilityManager;

    public SessionPanel(User user, SessionManager sessionManager, CourtFacilityManager facilityManager) {
        this.user = user;
        this.sessionManager = sessionManager;
        this.facilityManager = facilityManager;
        // stub
    }
}
