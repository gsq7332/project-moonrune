package com.example.runelogic.persistence.user;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.runelogic.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class userDao {
    
    private String userPath;
    private String username;
    private String password;
    private String databasePath;

    public userDao(@Value("${terms.database}") String database, @Value("${database.user-pass}") String userPath, ObjectMapper objectMapper) throws IOException {
        this.userPath = userPath;
        databasePath = database;
        getUsernamePassword();
        testConnection();
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


    public User getUser(String username) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("");
        ) {

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public User signIn(String username, String password) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("");
        ) {
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public User createUser(String username, String password) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("");
        ) {
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public User updateBio(String username, String bio) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("");
        ) {
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public Boolean removeUser(String username) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("");
        ) {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    private void testConnection() {
        try(Connection conn = DriverManager.getConnection(databasePath, username, password);) {
            System.out.println("user connection works :)");
        } catch (Exception exception) {
            System.out.println("user thing not working :( )");
            System.err.println(exception);
        }
    }

}
