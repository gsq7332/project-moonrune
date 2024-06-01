package com.example.runelogic.model.terms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CyrillicLetter extends Term {
    private String lower;
    public CyrillicLetter(@JsonProperty("term") String term, @JsonProperty("lower") String lower, 
    @JsonProperty("meanings") ArrayList<String> meanings, @JsonProperty("id") int id) {
        super(term, meanings, id);
        this.lower = lower;
    }

    public String getLower() { return lower; }

    public void setLower(String newLower) {
        lower = newLower;
    }
    
}
