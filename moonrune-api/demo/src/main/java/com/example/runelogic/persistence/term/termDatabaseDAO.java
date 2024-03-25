package com.example.runelogic.persistence.term;

import com.example.runelogic.model.terms.Term;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/* 
import com.example.runelogic.model.CyrillicLetter;
import com.example.runelogic.model.GreekLetter;
import com.example.runelogic.model.Kanji;
import com.example.runelogic.model.Term;
import com.fasterxml.jackson.databind.ObjectMapper;
*/

// import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
// import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

public class termDatabaseDAO extends termDAO {

    public termDatabaseDAO(@Value("${terms.file}") String filename, ObjectMapper objectMapper) throws IOException {
        super();
    }

    public void save() {

    }

    public void load() {

    }
}
