package com.example.runelogic.persistence.term;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

public class termDatabaseDAO extends termDAO {

    public termDatabaseDAO(@Value("${terms.database}") String database, ObjectMapper objectMapper) throws IOException {
        super();
    }

    public void save() {

    }

    public void load() {

    }
}
