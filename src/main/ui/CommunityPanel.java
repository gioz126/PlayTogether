package ui;

import javax.swing.JPanel;

import model.CommunityManager;
import model.User;

public class CommunityPanel extends JPanel {

    private User user;
    private CommunityManager communityManager;

    public CommunityPanel(User user, CommunityManager communityManager) {
        this.user = user;
        this.communityManager = communityManager;
        // stub
    }
}
