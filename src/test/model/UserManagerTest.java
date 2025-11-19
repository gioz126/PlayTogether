package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class UserManagerTest {
    private User user1;
    private User user2;
    private UserManager testUserManager;

    @BeforeEach
    public void runBefore() {
        user1 = new User("gio", "1234", SportType.BADMINTON);
        user2 = new User("zio", "1242", SportType.BADMINTON);
        testUserManager = new UserManager();
    }

    @Test
    public void constructorTest() {
        assertTrue(testUserManager.getAllUsers().isEmpty());
        assertTrue(testUserManager.getUsersCount() == 0);
    }

    @Test
    public void addUserTest() {
        // add user
        assertTrue(testUserManager.addUser(user1));
        assertTrue(testUserManager.getAllUsers().contains(user1));
        assertTrue(testUserManager.getUsersCount() == 1);

        // add user twice, should false
        assertFalse(testUserManager.addUser(user1));
        assertTrue(testUserManager.getUsersCount() == 1);
    }

    @Test
    public void findUserByNameTest() {
        // add user gio
        testUserManager.addUser(user1);

        // try to find zio (return null)
        assertNull(testUserManager.findUserByName("zio"));

        // try to find gio
        assertEquals(user1, testUserManager.findUserByName("gio"));

        // add user zio
        testUserManager.addUser(user2);

        // try to find zio
        assertEquals(user2, testUserManager.findUserByName("zio"));
    }

    @Test
    public void removeUserTest() {
        // remover user1, hasn't been added (false)
        assertFalse(testUserManager.removeUser(user1));

        // add user1
        testUserManager.addUser(user1);

        // remover user1
        assertTrue(testUserManager.removeUser(user1));
        assertTrue(testUserManager.getAllUsers().isEmpty());
        assertTrue(testUserManager.getUsersCount() == 0);
    }

    @SuppressWarnings("methodlength")
    @Test
    public void testLoadFromJsonNormal() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        facility.addCourt(new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0)));
        facilityManager.addFacility(facility);

        JSONObject bookingJson = new JSONObject();
        bookingJson.put("facilityName", "UBC Courts");
        bookingJson.put("courtId", "Court1");
        bookingJson.put("startTime", "2025-12-12T12:00");
        bookingJson.put("endTime", "2025-12-12T14:00");

        JSONArray bookings = new JSONArray().put(bookingJson);

        JSONObject userJson = new JSONObject();
        userJson.put("name", "Gio");
        userJson.put("contactNumber", "1234");
        userJson.put("sportInterest", "BADMINTON");
        userJson.put("bookings", bookings);

        JSONArray jsonArray = new JSONArray().put(userJson);

        UserManager userManager = new UserManager();
        userManager.loadFromJson(jsonArray, facilityManager);

        User u = userManager.getAllUsers().get(0);
        assertEquals("Gio", u.getName());
        assertEquals(1, u.getBookings().size());
        Booking b = u.getBookings().get(0);
        assertEquals(facility, b.getFacility());
        assertEquals(facility.findCourtById("Court1"), b.getCourt());
    }

    @Test
    public void testLoadFromJsonFacilityNotFound() {
        CourtFacilityManager facilityManager = new CourtFacilityManager(); // empty

        JSONObject bookingJson = new JSONObject();
        bookingJson.put("facilityName", "Nonexistent");
        bookingJson.put("courtId", "Court1");
        bookingJson.put("startTime", "2025-12-12T12:00");
        bookingJson.put("endTime", "2025-12-12T14:00");

        JSONArray bookings = new JSONArray().put(bookingJson);

        JSONObject userJson = new JSONObject();
        userJson.put("name", "Gio");
        userJson.put("contactNumber", "1234");
        userJson.put("sportInterest", "BADMINTON");
        userJson.put("bookings", bookings);

        UserManager userManager = new UserManager();
        userManager.loadFromJson(new JSONArray().put(userJson), facilityManager);

        Booking b = userManager.getAllUsers().get(0).getBookings().get(0);
        assertNull(b.getFacility());
        assertNull(b.getCourt());
    }

    @Test
    public void testLoadFromJsonCourtNotFound() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        facilityManager.addFacility(facility); // no courts added

        JSONObject bookingJson = new JSONObject();
        bookingJson.put("facilityName", "UBC Courts");
        bookingJson.put("courtId", "InvalidCourt");
        bookingJson.put("startTime", "2025-12-12T12:00");
        bookingJson.put("endTime", "2025-12-12T14:00");

        JSONArray bookings = new JSONArray().put(bookingJson);

        JSONObject userJson = new JSONObject();
        userJson.put("name", "Gio");
        userJson.put("contactNumber", "1234");
        userJson.put("sportInterest", "BADMINTON");
        userJson.put("bookings", bookings);

        UserManager userManager = new UserManager();
        userManager.loadFromJson(new JSONArray().put(userJson), facilityManager);

        Booking b = userManager.getAllUsers().get(0).getBookings().get(0);
        assertNotNull(b.getFacility());
        assertNull(b.getCourt());
    }

    @Test
    public void testRestoreCourtReservationsValidBooking() {
        // Setup facility, court, and manager
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC North Recreation", AreaLocation.VANCOUVER);
        CourtUnit court = new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0));
        facility.addCourt(court);
        facilityManager.addFacility(facility);

        // Create user + booking with valid facility/court
        User user = new User("gio", "1234", SportType.BADMINTON);
        Booking booking = new Booking(user, facility, court,
                java.time.LocalDateTime.of(2025, 12, 12, 12, 0),
                java.time.LocalDateTime.of(2025, 12, 12, 14, 0));
        user.addBooking(booking);

        // Add to manager and verify court has no reservation yet
        testUserManager.addUser(user);
        assertTrue(court.getReservations().isEmpty());

        // Call restore
        testUserManager.restoreCourtReservations();

        // Verify reservation added
        assertEquals(1, court.getReservations().size());
        assertEquals(booking, court.getReservations().get(0));
    }

    @Test
    public void testRestoreCourtReservationsFacilityNull() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        CourtUnit court = new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0));
        facility.addCourt(court);
        facilityManager.addFacility(facility);

        User user = new User("gio", "1234", SportType.BADMINTON);

        // booking's facility is null
        Booking booking = new Booking(user, null, court,
                java.time.LocalDateTime.of(2025, 12, 12, 12, 0),
                java.time.LocalDateTime.of(2025, 12, 12, 14, 0));
        user.addBooking(booking);
        testUserManager.addUser(user);

        testUserManager.restoreCourtReservations();

        // Court reservation list should remain empty
        assertTrue(court.getReservations().isEmpty());
    }

    @Test
    public void testRestoreCourtReservationsCourtNull() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        facilityManager.addFacility(facility);

        User user = new User("gio", "1234", SportType.BADMINTON);

        // booking's court is null
        Booking booking = new Booking(user, facility, null,
                java.time.LocalDateTime.of(2025, 12, 12, 12, 0),
                java.time.LocalDateTime.of(2025, 12, 12, 14, 0));
        user.addBooking(booking);
        testUserManager.addUser(user);

        testUserManager.restoreCourtReservations();

        // Nothing should be added (no null pointer)
        assertTrue(facility.getCourts().isEmpty()); // no reservations occurred
    }

    @Test
    public void testRestoreCourtReservationsNoBookings() {
        CourtFacilityManager facilityManager = new CourtFacilityManager();
        CourtFacility facility = new CourtFacility("UBC Courts", AreaLocation.VANCOUVER);
        CourtUnit court = new CourtUnit("Court1", SportType.BADMINTON,
                LocalTime.of(8, 0), LocalTime.of(22, 0));
        facility.addCourt(court);
        facilityManager.addFacility(facility);

        // User with no bookings
        User user = new User("gio", "1234", SportType.BADMINTON);
        testUserManager.addUser(user);

        testUserManager.restoreCourtReservations();

        // Still no reservations
        assertTrue(court.getReservations().isEmpty());
    }

}
