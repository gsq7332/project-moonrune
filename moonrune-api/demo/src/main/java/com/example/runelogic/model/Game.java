package com.example.runelogic.model;

public class Game {
    
    private final int numQuestions;
    private final int numAnswers;
    private final long sessionID;
    private String correct;
    private int numCorrect;
    private int numAnswered;
    
    public Game(int numQuestions, int numAnswers, long sessionID) {
        this.numQuestions = numQuestions;
        this.numAnswers = numAnswers;
        this.sessionID = sessionID;
    }

    public long getSessionID() {
        return sessionID;
    }

    public int getNumAnswers() {
        return numAnswers;
    }

    public boolean isOver() {
        return (numQuestions > 0 && numAnswered >= numQuestions);
    }

    public boolean isCorrect(String term) {
        return term.equals(correct);
    }

    public void setCorrect(String term) {
        correct = term;
    }

    public void incrementCorrect() {
        numCorrect++;
    }

    public void incrementAnswered() {
        numAnswered++;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumAnswered() {
        return numAnswered;
    }

    public int getNumQuestions() {
        return numQuestions;
    }
    

}
