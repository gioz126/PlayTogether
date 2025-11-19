package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.Booking;
import model.CourtFacilityManager;
import model.Session;
import model.SessionManager;
import model.SportType;
import model.User;

public class SessionPanel extends JPanel {

    private User user;
    private SessionManager sessionManager;
    private CourtFacilityManager facilityManager;

    private JTextArea sessionDisplay;

    public SessionPanel(User user, SessionManager sessionManager, CourtFacilityManager facilityManager) {
        this.user = user;
        this.sessionManager = sessionManager;
        this.facilityManager = facilityManager;

        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton createButton = new JButton("🆕 Create Session (from booking)");
        JButton joinButton = new JButton("🧑‍🧒 Join Session");
        JButton leaveButton = new JButton("🚪 Leave Session");
        JButton viewButton = new JButton("🗒️ View My Sessions");
        JButton refreshButton = new JButton("🔄 Refresh");

        buttonPanel.add(createButton);
        buttonPanel.add(joinButton);
        buttonPanel.add(leaveButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);

        this.add(buttonPanel, BorderLayout.NORTH);

        // create text area to display sessions
        sessionDisplay = new JTextArea();
        sessionDisplay.setEditable(false);
        sessionDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.add(new JScrollPane(sessionDisplay), BorderLayout.CENTER);

        // Buttons actions
        createButton.addActionListener(e -> createSession());
        joinButton.addActionListener(e -> joinSession());
        leaveButton.addActionListener(e -> leaveSession());
        viewButton.addActionListener(e -> viewMySessions());
        refreshButton.addActionListener(e -> refreshDisplay());

    }

    private void createSession() {
        List<Booking> bookings = user.getBookings();

        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must have booking to create a session.");
            return;
        }

        String[] bookingOptions = new String[bookings.size()];
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            bookingOptions[i] = b.getCourt().getcourtID() + " | "
                    + b.getStartTime().toLocalTime() + "-"
                    + b.getEndTime().toLocalTime();
        }

        String chosen = (String) JOptionPane.showInputDialog(this, "Select a booking to create session from:",
                "Create Session", JOptionPane.PLAIN_MESSAGE, null, bookingOptions, bookingOptions[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(bookingOptions).indexOf(chosen);

        Session sessionToMake = user.createSession(user, user.getSportInterest(), index);

        sessionManager.addSession(sessionToMake);

        JOptionPane.showMessageDialog(this, "Session created successfully!");
        viewMySessions();
    }

    private void joinSession() {
        SportType[] sports = SportType.values();
        SportType sport = (SportType) JOptionPane.showInputDialog(this, "Choose sport:", "Join Session",
                JOptionPane.PLAIN_MESSAGE,
                null, sports, sports[0]);

        if (sport == null) {
            return;
        }

        List<Session> availableSessions = sessionManager.findSessionsBySport(sport);

        if (availableSessions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No sessions available.");
            return;
        }

        String[] sessionNames = new String[availableSessions.size()];
        for (int i = 0; i < availableSessions.size(); i++) {
            Session s = availableSessions.get(i);
            sessionNames[i] = s.getFacility().getFacilityName() + " | "
                    + s.getStartDateTime().toLocalDate() + " "
                    + s.getStartDateTime().toLocalTime() + "-"
                    + s.getEndDateTime().toLocalTime() + " | Participants: "
                    + s.getParticipant().size();
        }

        String chosen = (String) JOptionPane.showInputDialog(this, "Select a session to join:", "Join",
                JOptionPane.PLAIN_MESSAGE, null, sessionNames, sessionNames[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(sessionNames).indexOf(chosen);

        boolean join = user.joinSession(availableSessions.get(index));

        if (join) {
            JOptionPane.showMessageDialog(this, "Joined successully!");
        } else {
            JOptionPane.showMessageDialog(this, "You are already in this session.");
        }

        viewMySessions();
    }

    private void leaveSession() {
        List<Session> joined = user.getSessionsJoined();

        if (joined.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You haven't joined any sessions.");
            return;
        }

        String[] options = new String[joined.size()];

        for (int i = 0; i < joined.size(); i++) {
            Session s = joined.get(i);
            options[i] = s.getSport() + " | "
                    + s.getFacility().getFacilityName() + " | "
                    + s.getStartDateTime().toLocalDate() + " "
                    + s.getStartDateTime().toLocalTime() + "-"
                    + s.getEndDateTime().toLocalTime();
        }

        String chosen = (String) JOptionPane.showInputDialog(this, "Choose a session to leave", "Leave Session",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(options).indexOf(chosen);
        Session s = joined.get(index);

        if (s.getOwner().equals(user)) {
            JOptionPane.showMessageDialog(this, "You are the owner of this session. Cannot leave session you owned.");
            return;
        }

        boolean removed = s.removeParticipant(user);

        if (removed) {
            user.getSessionsJoined().remove(s);
            JOptionPane.showMessageDialog(this, "You've successfully left the session");
        } else {
            JOptionPane.showMessageDialog(this, "Could not leave session (not a participant).");
        }

        viewMySessions();
    }

    private Object viewMySessions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewMySessions'");
    }

    private Object refreshDisplay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshDisplay'");
    }
}
