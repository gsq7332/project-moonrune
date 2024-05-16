package com.example.runelogic.persistence.collection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.runelogic.model.TermCollection;
import com.example.runelogic.model.terms.Term;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class collectionDAO {
    private String userPath;
    private String username;
    private String password;
    private String databasePath;
    private int lastUsedCollectionID;

    public collectionDAO(@Value("${terms.database}") String database, @Value("${database.user-pass}") String userPath, ObjectMapper objectMapper) throws IOException {
        this.userPath = userPath;
        databasePath = database;
        getUsernamePassword();
        lastUsedCollectionID = getLastCollectionID();
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

    public int getLastCollectionID() {
        return 0;
    }

    // function to get the list of admin collections (and properties)
    public TermCollection[] getCollectionsByOwner(String owner) {
        ArrayList<TermCollection> ownedCollections = new ArrayList<>();
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from collection
                    where collectionOwner like "%s"
                """
            , owner));
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("CollectionID");
                String name = resultSet.getString("CollectionName");
                String colOwner = resultSet.getString("CollectionOwner"); // here in case something somehow goes wrong
                int privacyLevel = resultSet.getInt("PrivacyLevel");
                String description = resultSet.getString("description");
                TermCollection collection = new TermCollection(id, name, colOwner, privacyLevel, description);
                ownedCollections.add(collection);
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
        TermCollection[] collectionsArray = new TermCollection[ownedCollections.size()];
        collectionsArray = ownedCollections.toArray(collectionsArray);
        return collectionsArray;
    }


    public LinkedHashMap<String, Term> getTerms(int collectionID, String filter) {
        LinkedHashMap<String, Term> terms = new LinkedHashMap<>();
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select terms.name, hasMeaning.meaning
                    from terms inner join hasmeaning
                    where terms.TermID = hasmeaning.TermID
                    and terms.TermID in (
                        select inCollection.TermID
                        from inCollection
                        where CollectionID = %d)
                    and terms.TermID not in (
                        select * from isDiacritic
                    )
                """
            , collectionID));
        ) {
            
            while (resultSet.next()) {
                String term = resultSet.getString("name");
                String meaning = resultSet.getString("meaning");
                if (terms.keySet().contains(term)) {
                    Term existing = terms.get(term);
                    HashSet<String> meanings = new HashSet<>();
                    meanings.add(meaning);
                    existing.addMeanings(meanings);
                } else {
                    ArrayList<String> meanings = new ArrayList<>();
                    meanings.add(meaning);
                    terms.put(term, new Term(term, meanings));
                }
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
        return terms;
    }

    public TermCollection createCollection(String username, String collectionName) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                lastUsedCollectionID += 1;
            System.out.println("term connection works :)");
        } catch (Exception exception) {
            System.out.println("term thing not working :( )");
            System.err.println(exception);
        }
        
        
        return null;
    }

    public boolean deleteCollection(int collectionID) {
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
