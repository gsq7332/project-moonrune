package com.example.runelogic.model;

public class Game {
    
    private int num_questions;
    private int num_answers;
    private long sessionID;
    private String correct;
    
    public Game(int num_questions, int num_answers, long sessionID) {
        this.num_questions = num_questions;
        this.num_answers = num_answers;
        this.sessionID = sessionID;
    }

    public boolean getCorrect(String term) {
        return term.equals(correct);
    }

    public void setCorrect(String term) {
        correct = term;
    }

    

}
