package main.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> registeredUsers;

    public UserManager() {
        registeredUsers = new HashMap<>();
    }

    public boolean isRegistered(String UserName) {
        return registeredUsers.containsKey(UserName);
    }

    public void registeringUser(User user) {
        registeredUsers.put(user.getUserName(), user);
    }

    public User getUser(String UserName) {
        return registeredUsers.get(UserName);
    }
}
