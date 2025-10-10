package ui;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.CourtUnavailableException;
import model.AreaLocation;
import model.Booking;
import model.Community;
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

    // for user sport setup
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

    // for community setup
    private SportType chooseSportTypeCommunity() {
        while (true) {
            System.out.println("Select your community sport interest:");
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

    // for community search
    private SportType chooseSportTypeSearch() {
        while (true) {
            System.out.println("Select sport to search:");
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

    // for community setup
    private AreaLocation chooseAreaLocation() {
        while (true) {
            System.out.println("Select the Area:");
            System.out.println("1. Vancouver");
            System.out.println("2. Burnaby");
            System.out.println("3. Richmond");
            System.out.println("4. Surrey");
            System.out.println(">");

            int areaChoice = getIntInput();

            switch (areaChoice) {
                case 1 -> {
                    return AreaLocation.VANCOUVER;
                }
                case 2 -> {
                    return AreaLocation.BURNABY;
                }
                case 3 -> {
                    return AreaLocation.RICHMOND;
                }
                case 4 -> {
                    return AreaLocation.SURREY;
                }
                default -> {
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.\n");
                }
            }
        }
    }

    // for community search
    private AreaLocation chooseAreaLocationSearch() {
        while (true) {
            System.out.println("Select the Area to search:");
            System.out.println("1. Vancouver");
            System.out.println("2. Burnaby");
            System.out.println("3. Richmond");
            System.out.println("4. Surrey");
            System.out.println(">");

            int areaChoice = getIntInput();

            switch (areaChoice) {
                case 1 -> {
                    return AreaLocation.VANCOUVER;
                }
                case 2 -> {
                    return AreaLocation.BURNABY;
                }
                case 3 -> {
                    return AreaLocation.RICHMOND;
                }
                case 4 -> {
                    return AreaLocation.SURREY;
                }
                default -> {
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.\n");
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
            LocalDateTime end = LocalDateTime.of(year, month, day, endHour, 0);

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
        System.out.println("\n=== Join a session ===");

        System.out.println("Which sport do you want to join?");
        System.out.println("1. Badminton");
        System.out.println("2. Padel");

        SportType chosenSport;
        while (true) {
            int sportChoice = getIntInput();
            switch (sportChoice) {
                case 1 -> {
                    chosenSport = SportType.BADMINTON;
                    break;
                }
                case 2 -> {
                    chosenSport = SportType.PADEL;
                    break;
                }
                default -> {
                    System.out.println("Invalid input. Please enter 1 or 2");
                    continue;
                }
            }
            break;
        }
        List<Session> availableSessions = sessionManager.findSessionsBySport(chosenSport);

        if (availableSessions.isEmpty()) {
            System.out.println("No sessions available for " + chosenSport + ".");
            return;
        }

        System.out.println("Available " + chosenSport + " sessions: ");
        for (int i = 0; i < availableSessions.size(); i++) {
            Session s = availableSessions.get(i);
            System.out.println((i + 1) + ". " +
                    s.getSport() + " | " +
                    s.getFacility().getFacilityName() + " | " +
                    s.getStartDateTime().toLocalDate() + " " +
                    s.getStartDateTime().toLocalTime() + "-" +
                    s.getEndDateTime().toLocalTime() + " | Participants: " + s.getParticipant().size());
        }

        System.out.println("Enter the number of the session you want to join: ");
        int index = getIntInput() - 1;
        if (index < 0 || index >= availableSessions.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Session chosen = availableSessions.get(index);
        boolean joined = currentUser.joinSession(chosen);
        if (joined) {
            System.out.println("Joined session succesfully!");
        } else {
            System.out.println("You're already in that session.");
        }
    }

    private void leaveSessionUI() {
        System.out.println("\n=== Leave a Session ===");

        List<Session> joinedSession = currentUser.getSessionsJoined();

        if (joinedSession.isEmpty()) {
            System.out.println("You haven't joined any sessions yet.");
            return;
        }

        System.out.println("Select a session to leave: ");

        for (int i = 0; i < joinedSession.size(); i++) {
            Session s = joinedSession.get(i);
            System.out.println((i + 1) + ". " +
                    s.getSport() + " | " +
                    s.getFacility().getFacilityName() + " | " +
                    s.getStartDateTime().toLocalDate() + " " +
                    s.getStartDateTime().toLocalTime() + "-" +
                    s.getEndDateTime().toLocalTime());
        }

        System.out.println("Enter the number of session you want to leave: ");
        int index = getIntInput() - 1;

        if (index < 0 || index >= joinedSession.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Session selected = joinedSession.get(index);
        boolean removed = selected.removeParticipant(currentUser);

        if (removed) {
            currentUser.getSessionsJoined().remove(selected);
            System.out.println("You've successfully left the session");
        } else {
            System.out.println("Could not leave session (not a participant). ");
        }
    }

    // session created is not printed at view my session
    private void viewMySessionUI() {
        System.out.println("/n=== My Sessions ===");

        List<Session> joinedSessions = currentUser.getSessionsJoined();

        if (joinedSessions.isEmpty()) {
            System.out.println("You haven't joined any sessions yet.");
            return;
        }

        for (Session s : joinedSessions) {
            System.out.println("- " + s.getSport()
                    + " | Facility: " + s.getFacility().getFacilityName()
                    + " | Court: " + s.getCourtUnit().getcourtID()
                    + " | " + s.getStartDateTime().toLocalDate()
                    + " " + s.getStartDateTime().toLocalTime()
                    + "-" + s.getEndDateTime().toLocalTime()
                    + " | Participants: " + s.getParticipant().size());
        }
    }

    // EFFECTS: handle matters with community
    private void handleCommunityMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    === Community Menu ===
                    1. Create a Community
                    2. View All Active Communities
                    3. Find Community By Sport
                    4. Find Community By Location
                    5. Join a Community
                    6. View My Communities
                    7. Remove Community (as Leader)
                    8. Leave a Community
                    9. Back
                    """);
            int choice = getIntInput();
            switch (choice) {
                case 1 -> createCommunityUI();
                case 2 -> viewAllCommunitiesUI();
                case 3 -> findCommunityBySportUI();
                case 4 -> findCommunityByLocationUI();
                case 5 -> joinCommunityUI();
                case 6 -> viewMyCommunitiesUI();
                case 7 -> removeCommunityUI();
                case 8 -> leaveCommunityUI();
                case 9 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createCommunityUI() {
        System.out.println("\n=== Create a Community ===");

        System.out.println("Enter a community name: ");
        String name = input.nextLine().trim();

        SportType sport = chooseSportTypeCommunity();

        AreaLocation area = chooseAreaLocation();

        System.out.println("Enter max number of member: ");
        int max = getIntInput();

        Community community = currentUser.createCommunity(name, sport, area, max);
        communityManager.addCommunity(community);

        System.out.println("Community created successfully!");
    }

    private void viewAllCommunitiesUI() {
        System.out.println("\n=== All Active Communities ===");

        List<Community> all = communityManager.getActiveCommunities();
        if (all.isEmpty()) {
            System.out.println("No active communities found");
            return;
        }

        for (Community c : all) {
            System.out.println("- " + c.getCommunityName() +
                    " | Sport: " + c.getSport() +
                    " | Location: " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }
    }

    private void findCommunityBySportUI() {
        System.out.println("\n=== Find Community By Sport ===");
        SportType sport = chooseSportTypeSearch();

        List<Community> list = communityManager.findCommunityBySport(sport);
        if (list.isEmpty()) {
            System.out.println("No communities found for " + sport + ".");
            return;
        }

        for (Community c : list) {
            System.out.println("- " + c.getCommunityName() +
                    " | Sport: " + c.getSport() +
                    " | Location: " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }
    }

    private void findCommunityByLocationUI() {
        System.out.println("\n=== Find Community By Location ===");
        AreaLocation area = chooseAreaLocationSearch();

        List<Community> list = communityManager.findCommunityByLocation(area);
        if (list.isEmpty()) {
            System.out.println("No communities found in " + area + ".");
            return;
        }

        for (Community c : list) {
            System.out.println("- " + c.getCommunityName() +
                    " | Sport: " + c.getSport() +
                    " | Location: " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }
    }

    private void joinCommunityUI() {
        System.out.println("\n=== Join a Community ===");

        List<Community> all = communityManager.getActiveCommunities();
        if (all.isEmpty()) {
            System.out.println("No Communities available to join.");
            return;
        }

        for (int i = 0; i < all.size(); i++) {
            Community c = all.get(i);
            System.out.println((i + 1) + ". " + c.getCommunityName() +
                    " | " + c.getSport() + " | " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }

        System.out.println("Enter number of community to join");
        int index = getIntInput() - 1;
        if (index < 0 || index >= all.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Community selected = all.get(index);
        boolean joined = currentUser.joinCommunity(selected);
        if (joined) {
            System.out.println("Joined community successfully");
        } else {
            System.out.println("Could not joined (already a member of the community or community is full).");
        }
    }

    private void viewMyCommunitiesUI() {
        System.out.println("\n=== My Communities ===");

        List<Community> joined = currentUser.getCommunityJoined();
        List<Community> led = currentUser.getCommunityLed();

        if (joined.isEmpty() && led.isEmpty()) {
            System.out.println("You haven't joined or led any communities yet.");
            return;
        }

        if (!led.isEmpty()) {
            System.out.println("Community You Lead:");
            for (Community c : led) {
                System.out.println("- " + c.getCommunityName() +
                        " | " + c.getSport() +
                        " | " + c.getLocation() +
                        " | Members:" + c.getMembers().size() + "/" + c.getMaxMembers());
            }
        }

        if (!joined.isEmpty()) {
            System.out.println("\nCommunities You Joined: ");
            for (Community c : joined) {
                System.out.println("- " + c.getCommunityName() +
                        " | " + c.getSport() +
                        " | " + c.getLocation() +
                        " | Members:" + c.getMembers().size() + "/" + c.getMaxMembers());
            }
        }
    }

    private void removeCommunityUI() {
        System.out.println("\n=== Remove a Community (Leader Only) ===");

        List<Community> led = currentUser.getCommunityLed();

        if (led.isEmpty()) {
            System.out.println("You are not leading any community.");
            return;
        }

        for (int i = 0; i < led.size(); i++) {
            Community c = led.get(i);
            System.out.println((i + 1) + ". " + c.getCommunityName() +
                    " | " + c.getSport() + " | " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }

        System.out.println("Enter number to remove:");
        int index = getIntInput() - 1;
        if (index < 0 || index >= led.size()) {
            System.out.println("Invalid selection");
            return;
        }

        Community selected = led.get(index);
        communityManager.removeCommunity(currentUser, selected);
        currentUser.getCommunityLed().remove(selected);
        currentUser.getCommunityJoined().remove(selected);
        System.out.println("Community removed successfuly.");
    }

    private void leaveCommunityUI() {
        System.out.println("\n=== Leave a Community ===");

        List<Community> joined = currentUser.getCommunityJoined();
        if (joined.isEmpty()) {
            System.out.println("You haven't joined any community");
            return;
        }

        for (int i = 0; i < joined.size(); i++) {
            Community c = joined.get(i);
            System.out.println((i + 1) + ". " + c.getCommunityName() +
                    " | " + c.getSport() + " | " + c.getLocation() +
                    " | Members: " + c.getMembers().size() + "/" + c.getMaxMembers());
        }
        System.out.println("Enter number to leave:");
        int index = getIntInput() - 1;
        if (index < 0 || index >= joined.size()) {
            System.out.println("Invalid selection");
            return;
        }

        Community selected = joined.get(index);
        if (selected.getCommunityLeader().equals(currentUser)) {
            System.out.println("You are the leader of this community. Use 'Remove Community' instead.");
            return;
        }
        selected.removeMember(currentUser);
        currentUser.getCommunityJoined().remove(selected);
        System.out.println("You have left the community.");
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
