package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.CommunityManager;
import model.User;

public class CommunityPanel extends JPanel {

    private User user;
    private CommunityManager communityManager;

    private JTextArea communityDisplay;

    public CommunityPanel(User user, CommunityManager communityManager) {
        this.user = user;
        this.communityManager = communityManager;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create button panel breaks into 3 rows
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton createButton = new JButton("🆕 Create Community");
        JButton viewAllButton = new JButton("🗒️ View All");
        JButton searchBySportButton = new JButton("🔍 Search By Sport");
        JButton searchByLocation = new JButton("🔍 Search By Location");
        JButton joinButton = new JButton("➕ Join Community");
        JButton myCommunitiesButton = new JButton("🧑‍🧑‍🧒 View My Communities");
        JButton leaveButton = new JButton("🚪 Leave Community");
        JButton removeButton = new JButton("❌ Remove (Leader)");
        JButton refreshButton = new JButton("🔄 Refresh");

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.add(createButton);
        row1.add(viewAllButton);
        row1.add(searchBySportButton);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.add(searchByLocation);
        row2.add(joinButton);
        row2.add(myCommunitiesButton);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row3.add(leaveButton);
        row3.add(removeButton);
        row3.add(refreshButton);

        buttonPanel.add(row1);
        buttonPanel.add(row2);
        buttonPanel.add(row3);

        this.add(buttonPanel, BorderLayout.NORTH);

        // creates text area
        communityDisplay = new JTextArea();
        communityDisplay.setEditable(false);
        communityDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.add(new JScrollPane(communityDisplay), BorderLayout.CENTER);

        
    }
}
