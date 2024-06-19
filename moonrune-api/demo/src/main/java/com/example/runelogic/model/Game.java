package com.example.runelogic.model;

import java.util.ArrayList;
import java.util.Random;

import com.example.runelogic.model.terms.CyrillicLetter;
import com.example.runelogic.model.terms.GreekLetter;
import com.example.runelogic.model.terms.Kanji;
import com.example.runelogic.model.terms.Term;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {
    
    private int sessionID;
    private String correct;
    private int numCorrect;
    private int numAnswered;
    private GameProperties gameProperties;
    private final int collectionID;
    private Term[] legalTerms;
    /*
     * Legal types for question/answer type:
     * term
     * meanings
     * lower
     * name
     * kanji
     */
    
    public Game(@JsonProperty("gameProperties") GameProperties gameProperties, 
    @JsonProperty("sessionID") int sessionID, @JsonProperty("collectionID") int collectionID, 
    @JsonProperty("filters") Filters filters) {
        this.gameProperties = gameProperties;
        this.sessionID = sessionID;
        this.collectionID = collectionID;
        correct = "";
        numCorrect = 0;
        numAnswered = 0;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public void setLegalTerms(Term[] legalTerms) {
        this.legalTerms = legalTerms;
    }

    public String[] generateTerms() {
        int numAnswers = gameProperties.numAnswers();
        String[] terms = new String[numAnswers+1];
        Term[] inUse = new Term[numAnswers];
        Random random = new Random();
        for (int i = 0; i < numAnswers; i++) {
            int answerIdx = random.nextInt(legalTerms.length);
            Term currTerm = legalTerms[answerIdx];
            boolean skip = false;
            for (Term term: inUse) {
                if (term == null) continue;
                if (currTerm.equals(term)) {
                    skip = true;
                }
                // making sure there is absolutely no overlap between the questions
                if (!skip) skip = checkOverlap(term, currTerm, false);
                if (!skip) skip = checkOverlap(term, currTerm, true);
            }
            if (skip) {
                i--;
                continue;
            }
            inUse[i] = currTerm;
            String possibleAnswer = getNeededTerm(currTerm, false);
            // last thing in returned string of term answers is the question
            if (i == 0) {
                terms[numAnswers] = getNeededTerm(currTerm, true);
                correct = possibleAnswer;
            }
            terms[i] = possibleAnswer;
        }
        return terms;
    }

    private String getNeededTerm(Term term, boolean question) {
        String comparison = "";
        String returnString = "";
        if (question) {
            comparison = gameProperties.questionType();
        } else {
            comparison = gameProperties.answerType();
        }
        switch (comparison) {
            case "term" -> {
                returnString = term.getTerm();
            }
            case "meanings" -> {
                ArrayList<String> meanings = term.getMeanings();
                Random random = new Random();
                int randNum = random.nextInt(meanings.size());
                returnString = meanings.get(randNum);
            }
            case "readings" -> {
                if (term instanceof Kanji) {
                    ArrayList<String> readings = ((Kanji) term).getReadings();
                    Random random = new Random();
                    int randNum = random.nextInt(readings.size());
                    returnString = readings.get(randNum);
                }
            }
            case "romaji" -> {
                if (term instanceof Kanji) {
                    ArrayList<String> readings = ((Kanji) term).getRomaji();
                    Random random = new Random();
                    int randNum = random.nextInt(readings.size());
                    returnString = readings.get(randNum);
                }
            }
            case "name" -> {
                if (term instanceof GreekLetter) { 
                    returnString = ((GreekLetter) term).getName();
                }
            }
            case "lower" -> {
                if (term instanceof CyrillicLetter) { 
                    returnString = ((CyrillicLetter) term).getLower();
                }
                if (term instanceof GreekLetter) { 
                    ArrayList<String> lower = ((GreekLetter) term).getLower();
                    Random random = new Random();
                    int randNum = random.nextInt(lower.size());
                    returnString = lower.get(randNum);
                }
            }
        }
        return returnString;
    }

    private boolean checkOverlap(Term term1, Term term2, boolean question) {
        boolean doesOverlap = false;
        String comparison = "";
        if (question) {
            comparison = gameProperties.questionType();
        } else {
            comparison = gameProperties.answerType();
        }
        switch (comparison) {
            case "term" -> {
                doesOverlap = term1.getTerm().equals(term2.getTerm());
            }
            case "meanings" -> {
                for (String meaning1: term1.getMeanings()) {
                    for (String meaning2: term2.getMeanings()) {
                        if (meaning1.equals(meaning2)) doesOverlap = true;
                    }
                }
            }
            case "readings" -> {
                if (term1 instanceof Kanji && term2 instanceof Kanji) {
                    for (String reading1: ((Kanji) term1).getReadings()) {
                        for (String reading2: ((Kanji) term2).getReadings()) {
                            if (reading1.equals(reading2)) doesOverlap = true;
                        }
                    }
                }
            }
            case "romaji" -> {
                if (term1 instanceof Kanji && term2 instanceof Kanji) {
                    for (String reading1: ((Kanji) term1).getRomaji()) {
                        for (String reading2: ((Kanji) term2).getRomaji()) {
                            if (reading1.equals(reading2)) doesOverlap = true;
                        }
                    }
                }
            }
            case "name" -> {
                if (term1 instanceof GreekLetter && term2 instanceof GreekLetter) { 
                    doesOverlap = ((GreekLetter) term1).getName().equals(((GreekLetter) term2).getName());
                }
            }
            case "lower" -> {
                if (term1 instanceof CyrillicLetter && term2 instanceof CyrillicLetter) { 
                    doesOverlap = ((CyrillicLetter) term1).getLower().equals(((CyrillicLetter) term2).getLower());
                }
                if (term1 instanceof GreekLetter && term2 instanceof GreekLetter) { 
                    for (String lower1: ((GreekLetter) term1).getLower()) {
                        for (String lower2: ((GreekLetter) term2).getLower()) {
                            if (lower1.equals(lower2)) doesOverlap = true;
                        }
                    }
                }
            }
        }
        return doesOverlap;
    }

    public long getSessionID() {
        return sessionID;
    }

    public int getNumAnswers() {
        return gameProperties.numAnswers();
    }

    public boolean isOver() {
        int numQuestions = gameProperties.numQuestions();
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
        return gameProperties.numQuestions();
    }

}
