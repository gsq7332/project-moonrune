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
import java.sql.ResultSet;
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
        getLastTermID();
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

    public void getLastTermID() {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("""
                    select max(TermID) as lastUsed
                    from terms
                    """);
            ) {
                resultSet.next();
                lastUsedTermID = resultSet.getInt("lastUsed") + 1;
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    public Term getTerm(int id) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("""
                select terms.name, terms.TermID, hasMeaning.meaning
                from terms inner join hasmeaning
                where terms.TermID = hasmeaning.TermID
                and terms.TermID = %d
                    """, id));
            ) {
                resultSet.next();
                String name = resultSet.getString("name");
                int termID = resultSet.getInt("TermID");
                ArrayList<String> meaningsList = new ArrayList<>();
                meaningsList.add(resultSet.getString("meaning"));
                while (resultSet.next()) {
                    String meaning = resultSet.getString("meaning");
                    meaningsList.add(meaning);
                }
                return new Term(name, meaningsList, termID);
            } catch (Exception exception) {
                System.err.println(exception);
                return null;
        }
    }

    
    public Term createTerm(String name, int collection) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                lastUsedTermID += 1;
                statement.executeUpdate(String.format("""
                    insert into terms("TermID", "Name")
                    values("%s", "%s")
                """, lastUsedTermID, name));
                statement.executeUpdate(String.format("""
                    insert into inCollection("TermID", "CollectionID")
                    values("%s", %d)
                """, lastUsedTermID, collection));
        } catch (Exception exception) {
            System.err.println(exception);
        }
        
        return null;
    }

    
    public Term updateTerm(int id, String newName) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeUpdate(String.format("""
                update terms
                set Name = "%s"
                where TermID = %d
            """, newName, id));
            return getTerm(id);
        } catch (Exception exception) {
            System.err.println(exception);
            return null;
        }
    }

    public Term addMeaning(int id, String meaning) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("""
                select max(localIndex) as lastUsed
                from HasMeaning
                where termID = %d
            """, id))
            ) {
            int firstUnused = 1;
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                firstUnused = resultSet.getInt("lastUsed") + 1;
            }
            statement.executeUpdate(String.format("""
                insert into hasMeaning("TermID", "meaning", "localIndex")
                values(%d, "%s", %d)
            """, id, meaning, firstUnused));
        } catch (Exception exception) {
            System.err.println(exception);
        }
        return null;
    }

    public boolean removeMeanings(int id) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeUpdate(String.format("""
                delete from hasMeaning
                where id = %d
            """, id));
        } catch (Exception exception) {
            System.err.println(exception);
        }
        return false;
    }

    
    public boolean deleteTerm(int termID, int collectionID) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeQuery(String.format("""
                delete from inCollection
                where TermID = %d
                and CollectionID = %d
            """, termID, collectionID));
            return true;
        } catch (Exception exception) {
            System.err.println(exception);
            return false;
        }
    }

    public void addToCollection(int collectionID, int termID) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeQuery(String.format("""
                intert into inCollection (CollectionID, TermID)
                values(%d, %d)
            """, collectionID, termID));
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    public void clearCollection(int collectionID) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeQuery(String.format("""
                delete from inCollection
                and CollectionID = %d
            """, collectionID));
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }

    public boolean updateTermsInCollection(int collectionID, Term[] terms) {
        try {
            clearCollection(collectionID);
            for (Term term : terms) {
                int termID = term.getId();
                removeMeanings(termID);
                updateTerm(termID, term.getTerm());
                ArrayList<String> meanings = term.getMeanings();
                for (String meaning : meanings) {
                    addMeaning(termID, meaning);
                }
                addToCollection(collectionID, termID);
            }
            // clear terms from collection
            // for each term, do the following:
            // 1. update the term
            // 2. add the term back into the collection
            return true;
        } catch (Exception e) {
            return false;   
        }
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
