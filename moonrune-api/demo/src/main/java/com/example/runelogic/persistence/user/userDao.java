package com.example.runelogic.persistence.user;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.runelogic.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class userDao {
    
    private String userPath;
    private String dataUser;
    private String dataPassword;
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
            dataUser = reader.readLine().strip();
            dataPassword = reader.readLine().strip();
        } catch (Exception exception) {

        }
    }


    public User getUser(String username) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("""
            select *
            from studier
            where username like "%s"  
            """, username
            ));
        ) {
            String databaseUser = resultSet.getString("username");
            String bio = resultSet.getString("bio");
            return new User(databaseUser, bio);
        } catch (Exception e) {
            return null;
        }
    }

    public User signIn(String username, String password) {
        String hashed = passHash(username, password);
        try (
            Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("""
                select *
                from studier
                where username like "%s" and password like "%s" 
                """, username, hashed
                ));
        ) {
            String databaseUser = resultSet.getString("username");
            String bio = resultSet.getString("bio");
            return new User(databaseUser, bio);
        } catch (Exception e) {
            return null;
        }
    }

    public User createUser(String username, String password) {
        String bio = "";
        String hashed = passHash(username, password);
        try (
            Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);
            Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate(String.format("""
                insert into studier
                values("%s", "%s", "%s")
                """, username, hashed, bio));
            return getUser(username);
        } catch (Exception e) {
            return null;
        }
    }

    public User updateBio(String username, String bio) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);
            Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate(String.format("""
                    update studier
                    set bio = "%s"
                    where username like "%s"
                    """, bio, username));
            return getUser(username);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeUser(String username) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("""
                delete from studier
                where username like "%s"
            """, username));
        ) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void testConnection() {
        try(Connection conn = DriverManager.getConnection(databasePath, dataUser, dataPassword);) {
            System.out.println("user connection works :)");
        } catch (Exception exception) {
            System.out.println("user thing not working :( )");
            System.err.println(exception);
        }
    }

    private static String passHash(String username, String password) {
        String userPass = username + password;
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(userPass.getBytes());
            String hexString = new BigInteger(1, hash).toString(16);
            String md5Str = Base64.getEncoder().encodeToString(hexString.getBytes());
            return md5Str;
        } catch (Exception e) {
            return null;
        }
    }

}
