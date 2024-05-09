package com.example.runelogic.persistence.term;

import com.example.runelogic.model.terms.Term;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

@Component
public class termDatabaseDAO extends termDAO {

    private final String userPath = "src/input/user-pass.txt";
    private String username;
    private String password;
    private Connection connection;
    private String databasePath;

    public termDatabaseDAO(@Value("${terms.database}") String database, ObjectMapper objectMapper) throws IOException {
        super();
        databasePath = database;
        getUsernamePassword();
        load();
    }

    private void getUsernamePassword() {
        try (
            FileInputStream fis = new FileInputStream(userPath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
        ) {
            username = reader.readLine().strip();
            password = reader.readLine().strip();
        } catch (Exception exception) {

        }
    }

    @Override
    public LinkedHashMap<String, Term> getTerms(String filter) {
        return null;
    }

    @Override
    public Term createTerm(String name, ArrayList<String> meanings) {
        return null;
    }

    @Override
    public Term updateTerm(String name, ArrayList<String> change) {
        return null;
    }

    @Override
    public boolean deleteTerm(String name) {
        return false;
    }

    public void save() {

    }

    public void load() {
        try(Connection conn = DriverManager.getConnection(databasePath, username, password);) {
            connection = conn;
        } catch (Exception exception) {
            System.out.println("thing not working :( )");
            System.err.println(exception);
        }
    }
}
