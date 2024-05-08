package com.example.runelogic.persistence.term;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

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
            username = reader.readLine();
            password = reader.readLine();
        } catch (Exception exception) {

        }
    }

    public void save() {

    }

    public void load() {
        try {
            connection = DriverManager.getConnection(databasePath, username, password);
        } catch (Exception exception) {
            System.out.println("thing not working :( )");
        }
    }
}
