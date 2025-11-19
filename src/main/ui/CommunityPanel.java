package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.AreaLocation;
import model.Community;
import model.CommunityManager;
import model.SportType;
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

        createButton.addActionListener(e -> createCommunity());
        viewAllButton.addActionListener(e -> viewAllCommunities());
        searchBySportButton.addActionListener(e -> searchBySport());
        searchByLocation.addActionListener(e -> searchByLocation());
        joinButton.addActionListener(e -> joinCommunity());
        myCommunitiesButton.addActionListener(e -> viewMyCommunities());
        leaveButton.addActionListener(e -> leaveCommunity());
        removeButton.addActionListener(e -> removeCommunity());
        refreshButton.addActionListener(e -> refreshDisplay());
    }

    // EFFECTS: let user createCommunity with members between 1 to 1000
    private void createCommunity() {
        String name = JOptionPane.showInputDialog(this, "Enter community name:");

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        SportType sportType = (SportType) JOptionPane.showInputDialog(this, "Select sport:", "Sport",
                JOptionPane.PLAIN_MESSAGE, null, SportType.values(), SportType.BADMINTON);

        if (sportType == null) {
            return;
        }

        AreaLocation areaLocation = (AreaLocation) JOptionPane.showInputDialog(this, "Select location:", "Location",
                JOptionPane.PLAIN_MESSAGE, null, AreaLocation.values(), AreaLocation.VANCOUVER);

        if (areaLocation == null) {
            return;
        }

        int maxMembers = promptForInt("Enter max number of members:", 1, 1000);

        Community c = user.createCommunity(name, sportType, areaLocation, maxMembers);
        communityManager.addCommunity(c);

        JOptionPane.showMessageDialog(this, "Community created successfully!");

        viewAllCommunities();
    }

    // EFFECTS: helper method to ask user max community members given the message,
    // minimum integer, and maximum integer
    private int promptForInt(String message, int min, int max) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, message);

            if (input == null) {
                return min;
            }
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                }
            }

            JOptionPane.showMessageDialog(this, "Please enter a valid number (" + min + " to " + max + ").");
        }
    }

    // EFFECTS: let user view all active communities
    private Object viewAllCommunities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewAllCommunities'");
    }

    // EFFECTS: let user search community by sport
    private Object searchBySport() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchBySport'");
    }

    // EFFECTS: let user search community by location
    private Object searchByLocation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByLocation'");
    }

    // EFFECTS: let user join available community
    private Object joinCommunity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinCommunity'");
    }

    // EFFECTS: let user view their joined/created community
    private Object viewMyCommunities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewMyCommunities'");
    }

    // EFFECTS: let user leave community that he/she already joined
    private Object leaveCommunity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaveCommunity'");
    }

    // EFFECTS: let user remove a community that he/she created
    private Object removeCommunity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCommunity'");
    }

    // EFFECTS: refresh community display
    private Object refreshDisplay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshDisplay'");
    }
}
