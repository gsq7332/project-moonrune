package com.example.runelogic.model.terms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Kanji extends Term {

    private final ArrayList<String> readings;
    private final ArrayList<String> romaji;
    private String jlpt;
    private String grade;
    private int rank;
    private int strokes;

    public Kanji(@JsonProperty("term") String term, @JsonProperty("meanings") ArrayList<String> meanings, 
    @JsonProperty("readings") ArrayList<String> readings, @JsonProperty("romaji") ArrayList<String> romaji, 
    @JsonProperty("grade") String grade, @JsonProperty("jlpt") String jlpt, @JsonProperty("rank") int rank, 
    @JsonProperty("strokes") int strokes, int id) {
        super(term, meanings, id);
        this.readings = readings;
        this.romaji = romaji;
        this.jlpt = jlpt;
        this.grade = grade;
        this.rank = rank;
        this.strokes = strokes;
    }

    public void addReadings(HashSet<String> reading) {
        readings.addAll(reading);
    }

    public boolean hasReading(String reading) {
        return readings.contains(reading);
    }

    public ArrayList<String> getReadings() {
        return readings;
    }

    public ArrayList<String> getRomaji() {
        return romaji;
    }

    public String getJlpt() {
        return jlpt;
    } 

    public String getGrade() {
        return grade;
    }

    public int getRank() {
        return rank;
    }

    public int getStrokes() {
        return strokes;
    }

    @Override
    public boolean equals(Object obj) {
        boolean value = super.equals(obj);
        if (value && obj.getClass() == this.getClass()) {
            value = Objects.equals(this.getMeanings(), ((Kanji) obj).getMeanings());
        }
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getTerm(), readings, super.getMeanings());
    }

    @Override
    public String toString() {
        return super.toString() + "; readings:" + readings;
    }
}
