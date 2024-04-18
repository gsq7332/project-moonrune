package com.example.runelogic.model.terms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Term {
    private final String term;
    private final ArrayList<String> meanings;

    public Term(@JsonProperty("term") String term, @JsonProperty("meanings") ArrayList<String> meanings) {
        this.term = term;
        this.meanings = meanings;
    }

    public void addMeanings(HashSet<String> meaning) {
        meanings.addAll(meaning);
    }

    public boolean hasMeaning(String meaning) {
        for (String termMeaning:meanings) {
            if (meaning.equalsIgnoreCase(termMeaning)) return true;
        }
        return false;
    }

    public String getTerm() {
        return term;
    }

    public ArrayList<String> getMeanings() {
        return meanings;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Term) obj;
        return Objects.equals(this.term, that.term) &&
                Objects.equals(this.meanings, that.meanings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, meanings);
    }

    @Override
    public String toString() {
        return term + "; meanings:" + meanings;
    }


}
