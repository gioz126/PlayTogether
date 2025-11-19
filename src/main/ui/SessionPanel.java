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

        if(chosen == null) {
            return;
        }

        int index = java.util.Arrays.asList(bookingOptions).indexOf(chosen);

        Session sessionToMake = user.createSession(user, user.getSportInterest(), index);
        
        sessionManager.addSession(sessionToMake);

        JOptionPane.showMessageDialog(this, "Session created successfully!");
        viewMySessions();
    }

    private Object joinSession() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinSession'");
    }

    private Object leaveSession() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaveSession'");
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
