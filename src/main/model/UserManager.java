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
}
