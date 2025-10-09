package ui;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.CourtUnavailableException;
import model.AreaLocation;
import model.Booking;
import model.CommunityManager;
import model.CourtFacility;
import model.CourtUnit;
import model.Session;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

public class PlayTogetherApp {
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final CommunityManager communityManager;
    private final List<CourtFacility> facilities;
    private final Scanner input;
    private User currentUser;

    // EFFECTS: constructs and initializes the PlayTogether app
    public PlayTogetherApp() {
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();
        facilities = new ArrayList<>();

        // creates facility and add courts
        // facility = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        setupCourts();

        input = new Scanner(System.in);
    }

    // EFFECTS: setup court unit for court facility
    public void setupCourts() {
        CourtFacility UBC = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        UBC.addCourt(new CourtUnit("Badminton 1", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));
        UBC.addCourt(new CourtUnit("Badminton 2", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));

        CourtFacility padelRichmond = new CourtFacility("Badminton Richmond", AreaLocation.RICHMOND);
        padelRichmond.addCourt(new CourtUnit("Padel 1", SportType.PADEL, LocalTime.of(8, 0), LocalTime.of(22, 0)));
        padelRichmond.addCourt(new CourtUnit("Padel 2", SportType.PADEL, LocalTime.of(8, 0), LocalTime.of(22, 0)));

        facilities.add(UBC);
        facilities.add(padelRichmond);
    }

    // EFFECTS: runs the app
    public void runPlayTogether() {
        System.out.println("🏸 Welcome to PlayTogether!");
        loginOrRegister();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1 -> handleCourtMenu();
                case 2 -> handleSessionMenu();
                case 3 -> handleCommunityMenu();
                case 4 -> {
                    System.out.println("Goodbye, " + currentUser.getName() + "!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again");
            }
        }
    }

    // EFFECTS: asks user to login (users has been registered before) or register as
    // a new user
    public void loginOrRegister() {
        System.out.println("=== Login / Register ===");

        System.out.println("Enter your name: ");
        String name = input.nextLine();

        System.out.println("Enter your contact number: ");
        String phone = input.nextLine();

        // Check if user already exists (only with name)
        User existUser = userManager.findUserByName(name);
        if (existUser != null) {
            currentUser = existUser;
            System.out.println("\n✅ Welcome back, " + currentUser.getName() + "!");
            return;
        }

        SportType sport = chooseSportType();

        currentUser = new User(name, phone, sport);
        userManager.addUser(currentUser);
        System.out.println("New account created for " + currentUser.getName() + "!\n");
    }

    private SportType chooseSportType() {
        while (true) {
            System.out.println("Select your sport interest:");
            System.out.println("1. Badminton");
            System.out.println("2. Padel");
            System.out.println(">");

            int sportChoice = getIntInput();

            switch (sportChoice) {
                case 1 -> {
                    return SportType.BADMINTON;
                }
                case 2 -> {
                    return SportType.PADEL;
                }
                default -> {
                    System.out.println("Invalid choice. Please enter 1 or 2.\n");
                }
            }
        }
    }

    // EFFECTS: display main menu
    private void displayMainMenu() {
        System.out.println("""
                ====================
                MAIN MENU
                1. Book Court
                2. Session
                3. Community
                4. Exit
                ====================
                """);
    }

    // EFFECTS: handle matters wuth court
    private void handleCourtMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    === Court Menu ===
                    1. Book a court
                    2. View my bookings
                    3. Back
                    """);
            int choice = getIntInput();
            switch (choice) {
                case 1 -> bookCourtUI();
                case 2 -> viewMyBookings();
                case 3 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void bookCourtUI() {
        System.out.println("\n === Book a Court ===");

        if (facilities.isEmpty()) {
            System.out.println("No facilities available");
            return;
        }
        System.out.println("Choose a facility: ");
        for (int i = 0; i < facilities.size(); i++) {
            CourtFacility f = facilities.get(i);
            System.out.println((i + 1) + ". " + f.getFacilityName() + " (" + f.getFacilityLocation() + ")");
        }

        int facilityIndex = getIntInput() - 1;
        if (facilityIndex < 0 || facilityIndex >= facilities.size()) {
            System.out.println("Invalid facility choice.");
            return;
        }

        CourtFacility selectedCourtFacility = facilities.get(facilityIndex);

        try {
            System.out.println("Enter a year (e.g. 2025): ");
            int year = getIntInput();

            System.out.println("Enter month (1-12): ");
            int month = getIntInput();

            System.out.println("Enter a day of month: ");
            int day = getIntInput();

            System.out.println("Enter start hour (24h): ");
            int startHour = getIntInput();

            System.out.println("Enter end hour (24h): ");
            int endHour = getIntInput();

            LocalDateTime start = LocalDateTime.of(year, month, day, startHour, 0);
            LocalDateTime end = LocalDateTime.of(year, month, month, endHour, 0);

            Booking booking = currentUser.bookCourt(selectedCourtFacility, start, end);
            System.out.println("Court booked successfully: ");
            System.out.println(" ⚪️ " + booking.getCourt().getcourtID());
            System.out.println(" ⚪️ Facility: " + booking.getFacility().getFacilityName());
            System.out.println(" ⚪️ Location: " + booking.getFacility().getFacilityLocation());
            System.out.println(" ⚪️ Date: " + day + "/" + month + "/" + year);
            System.out.println(" ⚪️ Time: " + startHour + ":00 - " + endHour + ":00");
        } catch (CourtUnavailableException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private void viewMyBookings() {
        List<Booking> bookings = currentUser.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("You have no booking yet.");
        } else {
            System.out.println("=== Your Bookings ===");
            for (Booking b : bookings) {
                System.out.println("- " + b.getFacility().getFacilityName()
                        + " (" + b.getFacility().getFacilityLocation() + ")"
                        + " | Court: " + b.getCourt().getcourtID()
                        + " | " + b.getStartTime().toLocalDate()
                        + " " + b.getStartTime().toLocalTime()
                        + "-" + b.getEndTime().toLocalTime());
            }
        }
    }

    // EFFECTS: handle matters with session
    private void handleSessionMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    === Session Menu ===
                    1. Create Session (from booking)
                    2. Join Session
                    3. Leave Session
                    4. View My Session
                    5. Back
                    """);
            int choice = getIntInput();
            switch (choice) {
                case 1 -> createSessionUI();
                case 2 -> joinSessionUI();
                case 3 -> leaveSessionUI();
                case 4 -> viewMySessionUI();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createSessionUI() {
        List<Booking> bookings = currentUser.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("You must have a booking to create a session.");
            return;
        }

        System.out.println("Select a booking to create session from: ");
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            System.out.println((i + 1) + ". " + b.getCourt().getcourtID() + " | "
                    + b.getStartTime().toLocalTime() + "-" + b.getEndTime().toLocalTime());
        }

        int index = getIntInput();
        // TODO fix sport so that it matches the booking
        Session session = currentUser.createSession(currentUser, currentUser.getSportInterest(), index);
        sessionManager.addSession(session);
        System.out.println("Session created successfully");
    }

    private void joinSessionUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinSessionUI'");
    }

    private void leaveSessionUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaveSessionUI'");
    }

    private void viewMySessionUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewMySessionUI'");
    }

    // EFFECTS: handle matters with community
    private void handleCommunityMenu() {
        // stub
    }

    // EFFECTS: ask the user to input number only. Will ask continously if string is
    // not a number
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
