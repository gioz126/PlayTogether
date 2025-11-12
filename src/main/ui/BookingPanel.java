package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.CourtFacilityManager;
import model.User;

public class BookingPanel extends JPanel {

    private User user;
    private CourtFacilityManager facilityManager;

    private JTextArea bookingDisplayArea;

    public BookingPanel(User user, CourtFacilityManager facilityManager) {
        this.user = user;
        this.facilityManager = facilityManager;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // creates button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton bookButton = new JButton("🆕 Book Court");
        JButton viewButton = new JButton("📒 View My Bookings");
        JButton refreshButton = new JButton("🔄 Refresh");

        // add buttons to the frame
        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        this.add(buttonPanel, BorderLayout.NORTH);

        bookingDisplayArea = new JTextArea();
        bookingDisplayArea.setEditable(false);
        bookingDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(bookingDisplayArea);
        this.add(scrollPane, BorderLayout.CENTER);

        // buttons actions
        bookButton.addActionListener(e -> bookCourt());
        viewButton.addActionListener(e -> displayMyBookings());
        refreshButton.addActionListener(e -> refreshDisplay());

    }

    // EFFECTS: let user choose facility, start and end hour, the book court
    private Object bookCourt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bookCourt'");
    }

    // EFFECTS: display user's current bookings
    private Object displayMyBookings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayMyBookings'");
    }

    // EFFECTS: refresh booking display
    private Object refreshDisplay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshDisplay'");
    }
}
