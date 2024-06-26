package com.example.runelogic.model.terms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreekLetter extends Term {
    private ArrayList<String> lower;
    private String name;
    public GreekLetter(@JsonProperty("term") String term, @JsonProperty("lower") ArrayList<String> lower, 
    @JsonProperty("name") String name, @JsonProperty("meanings") ArrayList<String> meanings, @JsonProperty("id") int id) {
        super(term, meanings, id);
        this.lower = lower;
        this.name = name;
    }

    public ArrayList<String> getLower() { return lower; }
    public String getName() { return name; }

    public void resetLower() {
        lower.clear();
    }

    public void addLower(String newLower) {
        lower.add(newLower);
    }

    public void setname(String newName) {
        name = newName;
    }

    @Override
    public String toString() {
        return super.toString() + ", lower: " + lower + ", name: " + name;
    }
}
