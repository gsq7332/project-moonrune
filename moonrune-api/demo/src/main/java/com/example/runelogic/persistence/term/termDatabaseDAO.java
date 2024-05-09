package com.example.runelogic.persistence.term;

import com.example.runelogic.model.TermCollection;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

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

    // function to get the list of admin collections (and properties)
    public TermCollection[] getCollectionsByOwner(String owner) {
        ArrayList<TermCollection> ownedCollections = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from collection
                    where collectionOwner like "%s"
                """
            , owner));
            
            while (resultSet.next()) {
                int id = resultSet.getInt("CollectionID");
                String name = resultSet.getString("CollectionName");
                String colOwner = resultSet.getString("CollectionOwner"); // here in case something somehow goes wrong
                int privacyLevel = resultSet.getInt("PrivacyLevel");
                String description = resultSet.getString("description");
                ownedCollections.add(new TermCollection(id, name, colOwner, privacyLevel, description));
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
        TermCollection[] collectionsArray = new TermCollection[ownedCollections.size()];
        collectionsArray = ownedCollections.toArray(collectionsArray);
        return collectionsArray;
    }

    @Override
    public LinkedHashMap<String, Term> getTerms(int collectionID, String filter) {
        LinkedHashMap<String, Term> terms = new LinkedHashMap<>();
        try {
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
