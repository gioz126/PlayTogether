package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    // EFFECTS: constructs a new user manager with empty user
    public UserManager() {
        users = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds user to users list if not already exists, return true if
    // addded, false otherwise
    public boolean addUser(User user) {
        return false; // stub
    }

    // EFFECTS: returns the user with given name if exists, otherwise null;
    public User findUserByName(String name) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: remove user if found, return true it removed, false if not found
    public boolean removeUser(User user) {
        return false; // stub
    }

    // getters
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public int getUsersCount() {
        return users.size();
    }
}
