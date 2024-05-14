package com.example.runelogic.model;

public class User {
    
    private final String username;
    private String bio;

    public User(String username, String bio) {
        this.username = username;
        this.bio = bio;
    }

    public void editBio(String newBio) {
        bio = newBio;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }
}
