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

import com.example.runelogic.model.Filters;
import com.example.runelogic.model.TermCollection;
import com.example.runelogic.model.terms.CyrillicLetter;
import com.example.runelogic.model.terms.GreekLetter;
import com.example.runelogic.model.terms.Kanji;
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
        getLastCollectionID();
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

    public void getLastCollectionID() {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("""
                    select max(CollectionID) as lastUsed
                    from collection
                    """);
            ) {
                resultSet.next();
                lastUsedCollectionID = resultSet.getInt("lastUsed");
        } catch (Exception exception) {
            System.err.println(exception);
        }
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
                    and PrivacyLevel < 99
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

    public TermCollection getCollectionInfo(int id) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from collection
                    where CollectionID = %d
                """
            , id));
        ) {
            resultSet.next();
            int collectionID = resultSet.getInt("CollectionID");
            String name = resultSet.getString("CollectionName");
            String colOwner = resultSet.getString("CollectionOwner"); // here in case something somehow goes wrong
            int privacyLevel = resultSet.getInt("PrivacyLevel");
            String description = resultSet.getString("description");
            TermCollection collection = new TermCollection(collectionID, name, colOwner, privacyLevel, description);
            return collection;
        } catch (Exception exception) {
            System.err.println(exception);
            return null;
        }
    }

    public boolean isOwner(String owner, int collectionID) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from collection
                    where CollectionID = %d
                    and CollectionOwner like "%s"
                """
            , collectionID, owner));  
        ) {
            return resultSet.isBeforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedHashMap<Integer, Term> getTerms(int collectionID, Filters filter) {
        LinkedHashMap<Integer, Term> terms = getMainTermInfo(collectionID, filter);
        switch(collectionID) {
            case 5:
                getNameInfo(collectionID, terms);
            case 4:
                getLowerInfo(collectionID, terms);
                break;
            case 6:
                getReadingInfo(collectionID, terms);
                getOtherKanjiInfo(collectionID, terms);
                break;
        }
        System.out.println(terms);
        return terms;
    }

    public void getLowerInfo(int collectionID, LinkedHashMap<Integer, Term> terms) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from haslower
                    where TermID in (
                        select TermID from inCollection
                        where CollectionID = %d
                    ) 
                """, collectionID));
        ) {
            while(resultSet.next()) {
                int termID = resultSet.getInt("TermID");
                if (!terms.keySet().contains(termID)) continue;
                String lower = resultSet.getString("lower");
                Term term = terms.get(termID);
                if (collectionID == 5) {
                    ((GreekLetter) term).addLower(lower);
                } else {
                    ((CyrillicLetter) term).setLower(lower);
                }
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
    }

    public void getNameInfo(int collectionID, LinkedHashMap<Integer, Term> terms) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from hasgreekname
                    where TermID in (
                        select TermID from inCollection
                        where CollectionID = %d
                    )
                """, collectionID));
        ) {
            while(resultSet.next()) {
                int termID = resultSet.getInt("TermID");
                if (!terms.keySet().contains(termID)) continue;
                String name = resultSet.getString("GreekName");
                Term term = terms.get(termID);
                ((GreekLetter) term).setname(name);
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
    }


    public void getReadingInfo(int collectionID, LinkedHashMap<Integer, Term> terms) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from hasreading
                    where TermID in (
                        select TermID from inCollection
                        where CollectionID = %d
                    )
                """, collectionID));
        ) {
            while(resultSet.next()) {
                int termID = resultSet.getInt("TermID");
                if (!terms.keySet().contains(termID)) continue;
                String reading = resultSet.getString("reading");
                String romaji = resultSet.getString("romaji");
                Term term = terms.get(termID);
                ((Kanji) term).addReading(reading);
                ((Kanji) term).addRomaji(romaji);
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
    }

    public void getOtherKanjiInfo(int collectionID, LinkedHashMap<Integer, Term> terms) {
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select * from kanjiproperties
                    where TermID in (
                        select TermID from inCollection
                        where CollectionID = %d
                    )
                """, collectionID));
        ) {
            while(resultSet.next()) {
                int termID = resultSet.getInt("TermID");
                if (!terms.keySet().contains(termID)) continue;
                String grade = resultSet.getString("Grade");
                String jlpt = resultSet.getString("jlpt");
                int ranking = resultSet.getInt("kanjiRank");
                int strokes = resultSet.getInt("strokes");
                Term term = terms.get(termID);
                ((Kanji) term).setGrade(grade);
                ((Kanji) term).setJlpt(jlpt);
                ((Kanji) term).setRank(ranking);
                ((Kanji) term).setStrokes(strokes);
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
    }


    public LinkedHashMap<Integer, Term> getMainTermInfo(int collectionID, Filters filter) {
        LinkedHashMap<Integer, Term> terms = new LinkedHashMap<>();
        try (
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
        ) {
            String matchingQuery = filter.getMatchingQuery();
            String diacriticQuery = "";
            if (collectionID < 3) {
                diacriticQuery = filter.getDiacriticQuery();
            } 
            String gradeQuery = "";
            String jlptQuery = "";
            String strokeQuery = "";
            String frequencyQuery = "";
            if (collectionID == 6) {
                gradeQuery = filter.getGradeQuery();
                jlptQuery = filter.getJlptQuery();
                strokeQuery = filter.getStrokeQuery();
                frequencyQuery = filter.getFrequencyQuery();
            }
            ResultSet resultSet = statement.executeQuery(String.format(
                """
                    select terms.name, terms.TermID, hasMeaning.meaning
                    from terms inner join hasmeaning
                    where terms.TermID = hasmeaning.TermID
                    and terms.TermID in (
                        select inCollection.TermID
                        from inCollection
                        where CollectionID = %d
                        )
                    %s
                    %s
                    %s
                    %s
                    %s
                    %s
                """
            , collectionID, matchingQuery, diacriticQuery, gradeQuery, jlptQuery, strokeQuery, frequencyQuery));

            while (resultSet.next()) {
                String term = resultSet.getString("name");
                String meaning = resultSet.getString("meaning");
                int id = resultSet.getInt("TermID");
                if (terms.keySet().contains(id)) {
                    Term existing = terms.get(id);
                    HashSet<String> meanings = new HashSet<>();
                    meanings.add(meaning);
                    existing.addMeanings(meanings);
                } else {
                    ArrayList<String> meanings = new ArrayList<>();
                    meanings.add(meaning);
                    Term newTerm = null;
                    switch(collectionID) {
                        case 4 -> {newTerm = new CyrillicLetter(term, "", meanings, id);}
                        case 5 -> {newTerm = new GreekLetter(term, new ArrayList<>(), "", meanings, id);}
                        case 6 -> {newTerm = new Kanji(term, meanings, new ArrayList<>(), new ArrayList<>(), "", "", 0, 0, id);}
                        default -> {newTerm = new Term(term, meanings, id);}
                    }
                    terms.put(id, newTerm);
                }
            }
        } catch (Exception exception) {
            System.err.println(exception);
            System.out.println("thing imploded");
        }
        return terms;
    }

    public int createCollection(String studier, String collectionName) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                lastUsedCollectionID += 1;
                statement.executeUpdate(String.format("""
                    insert into collection(CollectionID, CollectionName, CollectionOwner, PrivacyLevel, description)
                    values(%d, "%s", "%s", 0, "")
                """, lastUsedCollectionID, collectionName, studier));
            return lastUsedCollectionID;
        } catch (Exception exception) {
            System.err.println(exception);
            return -1;
        }
    }

    public TermCollection updateCollectionName(int id, String newName) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
            statement.executeUpdate(String.format("""
                update collection
                set CollectionName = "%s"
                where CollectionID = %d
            """, newName, id));
        } catch (Exception exception) {
            System.err.println(exception);
        }
        return null;
    }

    public TermCollection updateCollecitonDesc(int id, String desc) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                statement.executeUpdate(String.format("""
                    update collection
                    set description = "%s"
                    where CollectionID = %d
                """, desc, id));
        } catch (Exception exception) {
            System.err.println(exception);
        }
        return null;
    }

    public boolean deleteCollection(int collectionID) {
        try(
            Connection conn = DriverManager.getConnection(databasePath, username, password);
            Statement statement = conn.createStatement();
            ) {
                statement.executeUpdate(String.format("""
                    update collection
                    set PrivacyLevel = 99
                    where CollectionID = %d
                """, collectionID));
                return true;
        } catch (Exception exception) {
            System.err.println(exception);
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
