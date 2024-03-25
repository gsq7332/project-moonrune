package com.example.runelogic.persistence.term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import com.example.runelogic.model.terms.Term;

public abstract class termDAO {

    protected LinkedHashMap<String, Term> terms = new LinkedHashMap<>();

    public termDAO() throws IOException {}

    public Term getTerm(String name) {
        return terms.get(name);
    }

    public Term getRandomTerm() {
        ArrayList<String> termKeys = new ArrayList<String>(terms.keySet());
        int length = termKeys.size();
        if (length == 0) return null;
        Random random = new Random();
        int randomInt = random.nextInt(length);
        return getTerm(termKeys.get(randomInt));
    }

    public LinkedHashMap<String, Term> getTerms(String filter) {
        System.out.println(terms);
        LinkedHashMap<String, Term> results = new LinkedHashMap<>();
        for (String term : terms.keySet()) {
            if (term.startsWith(filter)) {
                results.put(term, terms.get(term));
            }
        }
        return results;
    }

    public Term createTerm(String name, ArrayList<String> meanings) {
        boolean isThere = terms.containsKey(name);
        Term newTerm = null;
        if (!isThere) {
            newTerm = new Term(name, meanings);
            terms.put(name, newTerm);
        }
        return newTerm;
    }

    public Term updateTerm(String name, ArrayList<String> change) {
        boolean isThere = terms.containsKey(name);
        Term term = null;
        if (isThere) {
            term = terms.get(name);
        }
        return term;
    }

    public boolean deleteTerm(String name) {
        boolean isThere = terms.containsKey(name);
        if (isThere) {
            terms.remove(name);
        }
        return isThere;
    }

    public abstract void save();

    public abstract void load();
    
} 
