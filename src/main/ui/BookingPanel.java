package ui;

import javax.swing.JPanel;

import model.CourtFacilityManager;
import model.User;

public class BookingPanel extends JPanel{

    private User user;
    private CourtFacilityManager facilityManager;

    public BookingPanel(User user, CourtFacilityManager facilityManager) {
        this.user = user;
        this.facilityManager = facilityManager;
        //stub
    }
}
