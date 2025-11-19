package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import exception.CourtUnavailableException;
import exception.EndTimeBeforeStartTimeException;
import model.Booking;
import model.CourtFacility;
import model.CourtFacilityManager;
import model.User;

@ExcludeFromJacocoGeneratedReport
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
    @SuppressWarnings("methodlength")
    private void bookCourt() {
        List<CourtFacility> facilities = facilityManager.getAllFacilities();

        if (facilities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No facilities available.");
            return;
        }
        String[] facilityNames = facilities.stream()
                .map(f -> f.getFacilityName() + " (" + f.getFacilityLocation() + ")").toArray(String[]::new);

        String chosen = (String) JOptionPane.showInputDialog(this, "Select a facility", "Book a court",
                JOptionPane.PLAIN_MESSAGE, null, facilityNames, facilityNames[0]);

        // handle null input
        if (chosen == null) {
            return;
        }
        int facilityIndex = java.util.Arrays.asList(facilityNames).indexOf(chosen);
        CourtFacility selectedCourtFacility = facilities.get(facilityIndex);

        try {
            int year = askForInt("Enter year (e.g. 2025):", 2025, 2030);
            int month = askForInt("Enter month (1-12):", 1, 12);
            int day = askForInt("Enter day (1-31):", 1, 31);
            int startHour = askForInt("Enter start hour (0-23):", 0, 23);
            int endHour = askForInt("Enter end hour (0-23)", 0, 23);

            LocalDateTime start = LocalDateTime.of(year, month, day, startHour, 0);
            LocalDateTime end = LocalDateTime.of(year, month, day, endHour, 0);

            // prevent booking for past
            if (start.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "Book failed. Cannot book a time in the past.");
                return;
            }

            Booking booking = user.bookCourt(selectedCourtFacility, start, end);

            JOptionPane.showMessageDialog(this, "Court booked successfully:\n"
                    + "⚫️ Court: " + booking.getCourt().getcourtID()
                    + "\n" + "⚫️ Facility: " + booking.getFacility().getFacilityName()
                    + "\n" + "⚫️ Location: " + booking.getFacility().getFacilityLocation()
                    + "\n" + "⚫️ Date: " + day + "/" + month + "/" + year
                    + "\n" + "⚫️ Time: " + startHour + ":00 - " + endHour + ":00");
            displayMyBookings();
        } catch (CourtUnavailableException | EndTimeBeforeStartTimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }

    // EFFECTS: display user's current bookings
    private void displayMyBookings() {
        List<Booking> bookings = user.getBookings();
        bookingDisplayArea.setText("");

        // handle empty bookinh
        if (bookings.isEmpty()) {
            bookingDisplayArea.setText("You have no bookings yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== Your Bookings === \n\n");
        for (Booking b : bookings) {
            sb.append("- ")
                    .append(b.getFacility().getFacilityName())
                    .append(" (").append(b.getFacility().getFacilityLocation()).append(")")
                    .append(" | Court: ").append(b.getCourt().getcourtID())
                    .append(" | ").append(b.getStartTime().toLocalDate())
                    .append(" ").append(b.getStartTime().toLocalTime())
                    .append("-").append(b.getEndTime().toLocalTime())
                    .append("\n");
        }
        bookingDisplayArea.setText(sb.toString());
    }

    // EFFECTS: refresh booking display
    private void refreshDisplay() {
        displayMyBookings();
        JOptionPane.showMessageDialog(this, "🔄 Booking list refreshed!");
    }

    // EFFECTS: helper to ask user for integers
    private int askForInt(String message, int min, int max) throws Exception {
        while (true) {
            String input = JOptionPane.showInputDialog(this, message);
            if (input == null) {
                throw new Exception("Cancelled");
            }

            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                }
            }

            JOptionPane.showMessageDialog(this, "Please enter valid number between " + min + " and " + max + ".");
        }
    }
}
