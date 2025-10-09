package ui;

import java.time.LocalTime;
import java.util.Scanner;

import model.AreaLocation;
import model.CommunityManager;
import model.CourtFacility;
import model.CourtUnit;
import model.SessionManager;
import model.SportType;
import model.User;
import model.UserManager;

public class PlayTogetherApp {
    private final UserManager userManager;
    private final SessionManager sessionManager;
    private final CommunityManager communityManager;
    private final CourtFacility facility;
    private final Scanner input;
    private User currentUser;

    // EFFECTS: constructs and initializes the PlayTogether app
    public PlayTogetherApp() {
        userManager = new UserManager();
        sessionManager = new SessionManager();
        communityManager = new CommunityManager();

        // creates facility and add courts
        facility = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        setupCourts();

        input = new Scanner(System.in);
    }

    // EFFECTS: setup court unit for court facility
    public void setupCourts() {
        facility.addCourt(new CourtUnit("Badminton 1", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facility.addCourt(new CourtUnit("Badminton 2", SportType.BADMINTON, LocalTime.of(8, 0), LocalTime.of(22, 0)));
    }

    // EFFECTS: runs the app
    public void runPlayTogether() {
        System.out.println("🏸 Welcome to PlayTogether!");
        loginOrRegister();

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

    //EFFECTS: ask the user to input number only. Will ask continously if string is not a number
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
