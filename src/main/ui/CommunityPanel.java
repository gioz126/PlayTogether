package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

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
    private void viewAllCommunities() {
        List<Community> allActiveCommunities = communityManager.getActiveCommunities();

        StringBuilder sb = new StringBuilder("=== All Communities === \n\n");

        if (allActiveCommunities.isEmpty()) {
            sb.append("No active communities found");
        } else {
            for (Community c : allActiveCommunities) {
                sb.append("- ").append(c.getCommunityName())
                        .append(" | Sport: ").append(c.getSport())
                        .append(" | Location: ").append(c.getLocation())
                        .append(" | Members: ").append(c.getMembers().size())
                        .append("/").append(c.getMaxMembers()).append("\n");
            }
        }
        communityDisplay.setText(sb.toString());

    }

    // EFFECTS: let user search community by sport
    private void searchBySport() {
        SportType sport = (SportType) JOptionPane.showInputDialog(this, "Select sport:", "Search By Sport",
                JOptionPane.PLAIN_MESSAGE, null, SportType.values(), SportType.BADMINTON);

        if (sport == null) {
            return;
        }

        List<Community> result = communityManager.findCommunityBySport(sport);

        StringBuilder sb = new StringBuilder("=== Communities for " + sport + " === \n\n");

        if (result.isEmpty()) {
            sb.append("No communities found");
        } else {
            for (Community c : result) {
                sb.append("- ").append(c.getCommunityName())
                        .append(" | Sport: ").append(c.getSport())
                        .append(" | Location: ").append(c.getLocation())
                        .append(" | Members: ").append(c.getMembers().size())
                        .append("/").append(c.getMaxMembers()).append("\n");
            }
        }
        communityDisplay.setText(sb.toString());

    }

    // EFFECTS: let user search community by location
    private void searchByLocation() {
        AreaLocation location = (AreaLocation) JOptionPane.showInputDialog(this, "Select location:",
                "Search By Location",
                JOptionPane.PLAIN_MESSAGE, null, AreaLocation.values(), AreaLocation.VANCOUVER);

        if (location == null) {
            return;
        }

        List<Community> result = communityManager.findCommunityByLocation(location);

        StringBuilder sb = new StringBuilder("=== Communities for " + location + " === \n\n");

        if (result.isEmpty()) {
            sb.append("No communities found");
        } else {
            for (Community c : result) {
                sb.append("- ").append(c.getCommunityName())
                        .append(" | Sport: ").append(c.getSport())
                        .append(" | Location: ").append(c.getLocation())
                        .append(" | Members: ").append(c.getMembers().size())
                        .append("/").append(c.getMaxMembers()).append("\n");
            }
        }
        communityDisplay.setText(sb.toString());
    }

    // EFFECTS: let user join available community
    private void joinCommunity() {
        List<Community> allCommunity = communityManager.getActiveCommunities();

        if (allCommunity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No communities available to join.");
            return;
        }

        String[] options = new String[allCommunity.size()];

        for (int i = 0; i < allCommunity.size(); i++) {
            Community c = allCommunity.get(i);

            options[i] = c.getCommunityName() + " | "
                    + c.getSport() + " | " + c.getLocation()
                    + " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers();
        }
        String chosen = (String) JOptionPane.showInputDialog(this, "Select a community:", "Join Community",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(options).indexOf(chosen);
        Community selected = allCommunity.get(index);

        boolean joined = user.joinCommunity(selected);
        if (joined) {
            JOptionPane.showMessageDialog(this, "Joined community successfully");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not joined (already a member of the community or community is full).");
        }

        viewMyCommunities();

    }

    // EFFECTS: let user view their joined/created community
    private void viewMyCommunities() {
        List<Community> communitiesJoined = user.getCommunityJoined();
        List<Community> communitiesLed = user.getCommunityLed();

        StringBuilder sb = new StringBuilder("=== My Communities === \n\n");

        if (communitiesJoined.isEmpty() && communitiesLed.isEmpty()) {
            sb.append("You are not in any communities");
        }

        if (!communitiesLed.isEmpty()) {
            sb.append("Communities You Lead:\n");

            for (Community c : communitiesLed) {
                sb.append("- ").append(c.getCommunityName())
                        .append(" | ").append(c.getSport())
                        .append(" | ").append(c.getLocation())
                        .append(" | Members:").append(c.getMembers().size())
                        .append("/").append(c.getMaxMembers()).append("\n");
            }
            sb.append("\n");
        }

        if (!communitiesJoined.isEmpty()) {
            sb.append("Communities You Joined:\n");

            for (Community c : communitiesJoined) {
                sb.append("- ").append(c.getCommunityName())
                        .append(" | ").append(c.getSport())
                        .append(" | ").append(c.getLocation())
                        .append(" | Members:").append(c.getMembers().size())
                        .append("/").append(c.getMaxMembers()).append("\n");
            }
        }
        communityDisplay.setText(sb.toString());
    }

    // EFFECTS: let user leave community that he/she already joined
    private void leaveCommunity() {
        List<Community> joined = user.getCommunityJoined();

        if (joined.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You haven't joined any communities.");
            return;
        }

        String[] options = new String[joined.size()];
        for (int i = 0; i < joined.size(); i++) {
            Community c = joined.get(i);

            options[i] = c.getCommunityName() + " | "
                    + c.getSport() + " | " + c.getLocation()
                    + " | Members: " + c.getMembers().size()
                    + "/" + c.getMaxMembers();
        }

        String chosen = (String) JOptionPane.showInputDialog(this, "Choose a community to leave:", "Leave Community",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(options).indexOf(chosen);

        Community selected = joined.get(index);

        if (selected.getCommunityLeader().equals(user)) {
            JOptionPane.showMessageDialog(this,
                    "You are the leader of this community. User 'Remove (Leader) button' instead.");

            return;
        }
        selected.removeMember(user);
        user.getCommunityJoined().remove(selected);
        JOptionPane.showMessageDialog(this, "You have left the community.");

        viewMyCommunities();
    }

    // EFFECTS: let user remove a community that he/she created
    private void removeCommunity() {
        List<Community> led = user.getCommunityLed();

        if (led.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You are not leading any communities.");
            return;
        }

        String[] options = new String[led.size()];
        for (int i = 0; i < led.size(); i++) {
            Community c = led.get(i);

            options[i] = c.getCommunityName() + " | "
                    + c.getSport() + " | " + c.getLocation()
                    + " | Members: " + c.getMembers().size()
                    + "/" + c.getMaxMembers();
        }

        String chosen = (String) JOptionPane.showInputDialog(this, "Select a community to remove:", "Remove Community",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(options).indexOf(chosen);
        Community selected = led.get(index);

        communityManager.removeCommunity(user, selected);
        user.getCommunityLed().remove(selected);
        user.getCommunityJoined().remove(selected);

        JOptionPane.showMessageDialog(this, "Community removed successfuly.");

        viewMyCommunities();
    }

    // EFFECTS: refresh active community display
    private void refreshDisplay() {
        viewAllCommunities();
        JOptionPane.showMessageDialog(this, "🔄 Available Communities list refreshed!");
    }
}
