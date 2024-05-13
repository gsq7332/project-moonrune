package com.example.runelogic.model;

public class User {
    
    private final String username;

    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String pass) {
        return pass.equals(password);
    }
}
