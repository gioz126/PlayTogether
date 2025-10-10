package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        
        //try to find zio (return null)
        assertNull(testUserManager.findUserByName("zio"));

        //try to find gio
        assertEquals(user1, testUserManager.findUserByName("gio"));

        // add user zio
        testUserManager.addUser(user2);

        //try to find zio
        assertEquals(user2, testUserManager.findUserByName("zio"));
    }

    @Test
    public void removeUserTest() {
        // remover user1, hasn't been added (false)
        assertFalse(testUserManager.removeUser(user1));

        //add user1
        testUserManager.addUser(user1);

        // remover user1
        assertTrue(testUserManager.removeUser(user1)); 
        assertTrue(testUserManager.getAllUsers().isEmpty());
        assertTrue(testUserManager.getUsersCount() == 0);
    }
}
