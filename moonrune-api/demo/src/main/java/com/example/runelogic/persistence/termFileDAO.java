package com.example.runelogic.persistence;

import com.example.runelogic.model.CyrillicLetter;
import com.example.runelogic.model.GreekLetter;
import com.example.runelogic.model.Kanji;
import com.example.runelogic.model.Term;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class termFileDAO extends termDAO {
    private static final Logger LOG = Logger.getLogger(termFileDAO.class.getName());


    private enum fileType {
        CYRILLIC, 
        GREEK,
        KANJI,
        OTHER
    }

    private String filename;
    private LinkedHashMap<String, Term> terms = new LinkedHashMap<>();
    private ObjectMapper objectMapper;
    
    private fileType type;

    public termFileDAO(@Value("${terms.file}") String filename, ObjectMapper objectMapper) throws IOException {
        super();
        this.filename = filename;
        this.objectMapper = objectMapper;
        switch (filename) {
            case "kanji.json" -> type = fileType.KANJI;
            case "cyrillic.json" -> type = fileType.CYRILLIC;
            case "greek.json" -> type = fileType.GREEK;
            default -> type = fileType.OTHER;
        }
        load();
    }

    public void save() {
        File file = new File(filename);
        
    }

    public void load() {
        try {
            Term[] termsArray;
            File file = new File(filename);
            switch (type) {
                case KANJI -> {
                    termsArray = objectMapper.readValue(file, Kanji[].class);
                }
                case CYRILLIC -> {
                    termsArray = objectMapper.readValue(file, CyrillicLetter[].class);
                }
                case GREEK -> {
                    termsArray = objectMapper.readValue(file, GreekLetter[].class);
                }
                default -> {
                    termsArray = objectMapper.readValue(file, Term[].class);
                }
            }

            for (Term term : termsArray) {
                String identifier = term.getTerm();
                terms.put(identifier, term);
            }
            System.out.println(terms);
        } catch (Exception e) {
            return;
        }
    }
}
