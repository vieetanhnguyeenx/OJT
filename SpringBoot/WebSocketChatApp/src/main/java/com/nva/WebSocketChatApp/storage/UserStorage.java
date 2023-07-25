package com.nva.WebSocketChatApp.storage;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {

    private static UserStorage instance;
    private Set<String> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(String usersName) throws Exception {
        if (users.contains(usersName)) {
            throw new Exception("User aready exits with userName" + usersName);
        }
        users.add(usersName);
    }
}
