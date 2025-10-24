package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class UserManager implements Writable {
    private List<User> users;

    // EFFECTS: constructs a new user manager with empty user
    public UserManager() {
        users = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds user to users list if not already exists, return true if
    // addded, false otherwise
    public boolean addUser(User user) {
        if (users.contains(user)) {
            return false;
        } else {
            users.add(user);
            return true;
        }
    }

    // EFFECTS: returns the user with given name if exists, otherwise null;
    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: remove user if found, return true it removed, false if not found
    public boolean removeUser(User user) {
        if (!users.contains(user)) {
            return false;
        } else {
            users.remove(user);
            return true;
        }
    }

    // getters
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public int getUsersCount() {
        return users.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (User u : users) {
            jsonArray.put(u.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: load users from JSON array
    public void loadFromJson(JSONArray jsonArray, CourtFacilityManager facilityManager) {
        users.clear();
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(parseUser(nextUser, facilityManager));
        }
    }

    // EFFECTS: parses a user from JSON object
    private User parseUser(JSONObject jsonObject, CourtFacilityManager facilityManager) {
        String name = jsonObject.getString("name");
        String contactNumber = jsonObject.getString("contactNumber");
        SportType sportInterest = SportType.valueOf(jsonObject.getString("sportInterest"));

        User user = new User(name, contactNumber, sportInterest);

        JSONArray bookingsArray = jsonObject.getJSONArray("bookings");
        for (Object obj : bookingsArray) {
            JSONObject bookingJson = (JSONObject) obj;
            Booking booking = parseBooking(bookingJson, facilityManager, user);
            user.addBooking(booking);
        }

        return user;
    }

    // EFFECTS: parses booking from json object
    private Booking parseBooking(JSONObject bookingJson, CourtFacilityManager facilityManager, User user) {
        String facilityName = bookingJson.getString("facilityName");
        String courtId = bookingJson.getString("courtId");
        // String userName = bookingJson.getString("userName");
        LocalDateTime startTime = LocalDateTime.parse(bookingJson.getString("startTime"));
        LocalDateTime endTime = LocalDateTime.parse(bookingJson.getString("endTime"));

        CourtFacility facility = facilityManager.findFacilityByName(facilityName);
        CourtUnit court = (facility != null) ? facility.findCourtById(courtId) : null;

        return new Booking(user, facility, court, startTime, endTime);
    }

    //MODIFIES: facilityManager
    //EFFECTS: get the court reservation back based on user bookings
    public void restoreCourtReservations() {
        for (User u : users) {
            for (Booking b : u.getBookings()) {
                CourtFacility facility = b.getFacility();
                CourtUnit court = b.getCourt();
                if (facility != null && court != null) {
                    court.addReservation(b);
                }
            }
        }
    }

}
