package com.example.runelogic.model;

public class User {
    
    private final String username;
    private String bio;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        bio = null;
    }

    public User(String username, String password, String bio) {
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

    public void editBio(String newBio) {
        bio = newBio;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String pass) {
        return pass.equals(password);
    }
}
