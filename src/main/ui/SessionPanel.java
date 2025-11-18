package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.CourtFacilityManager;
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

        JLabel title = new JLabel("🤼 Sessions", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
        this.add(title, BorderLayout.NORTH);

        //create text area to display sessions
        sessionDisplay = new JTextArea();
        sessionDisplay.setEditable(false);
        sessionDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.add(new JScrollPane(sessionDisplay), BorderLayout.CENTER);


    }
}
