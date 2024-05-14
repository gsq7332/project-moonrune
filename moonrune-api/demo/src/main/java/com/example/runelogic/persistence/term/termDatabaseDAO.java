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
import java.sql.Statement;
import java.util.ArrayList;

@Component
public class termDatabaseDAO {

    private String userPath;
    private String username;
    private String password;
    private String databasePath;
    private int lastUsedTermID;

    public termDatabaseDAO(@Value("${terms.database}") String database, @Value("${database.user-pass}") String userPath, ObjectMapper objectMapper) throws IOException {
        this.userPath = userPath;
        databasePath = database;
        getUsernamePassword();
        lastUsedTermID = getLastTermID();
        //load();
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

    public int getLastTermID() {
        return 0;
    }

    public Term getTerm(int id) {
        return null;
    }

    
    public Term createTerm(String name, ArrayList<String> meanings) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                lastUsedTermID += 1;
                System.out.println("term connection works :)");
        } catch (Exception exception) {
            System.out.println("term thing not working :( )");
            System.err.println(exception);
        }
        
        return null;
    }

    
    public Term updateTerm(int id, ArrayList<String> change) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            System.out.println("term connection works :)");
        } catch (Exception exception) {
            System.out.println("term thing not working :( )");
            System.err.println(exception);
        }
        return null;
    }

    
    public boolean deleteTerm(int id) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            System.out.println("term connection works :)");
        } catch (Exception exception) {
            System.out.println("term thing not working :( )");
            System.err.println(exception);
        }
        return false;
    }

    public void load() {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            System.out.println("term connection works :)");
        } catch (Exception exception) {
            System.out.println("term thing not working :( )");
            System.err.println(exception);
        }
    }
}
