package com.example.runelogic.persistence.game;

import java.util.HashMap;
// import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.runelogic.model.Game;
import com.example.runelogic.model.terms.Term;

@Component
public class gameDAO {
    // private static final Logger LOG = Logger.getLogger(gameDAO.class.getName());
    
    private static int sessionID = 0;

    private static HashMap<Integer, Game> ongoingGames;

    public gameDAO() {
        ongoingGames = new HashMap<>();
    }

    public static int getSessionID() {
        int currSession = sessionID;
        sessionID++;
        return currSession;
    }

    public int createGame(int numQuestions, int numAnswers) {
        int sessionID = getSessionID();
        Game game = new Game(numQuestions, numAnswers, sessionID);
        ongoingGames.put(sessionID, game);
        return sessionID;
    }

    public boolean setQuestionAnswer(int id, String questionType, String answerType) {
        Game game = ongoingGames.get(id);
        return game.setQuestionAnswer(questionType, answerType);
    }

    public boolean setLegalTerms(int id, Term[] legalTerms) {
        Game game = ongoingGames.get(id);
        return game.setLegalTerms(legalTerms);
    }

    public String[] generateAnswers(int id) {
        Game game = ongoingGames.get(id);
        return game.generateTerms();
    }

    public void setAnswer(int id, String answer) {
        Game game = ongoingGames.get(id);
        game.setCorrect(answer);
    }

    public boolean checkAnswer(int id, String answer) {
        Game game = ongoingGames.get(id);
        if (game == null) return false;
        return game.isCorrect(answer);
    }

    public boolean isOver(int id) {
        Game game = ongoingGames.get(id);
        return game.isOver();
    }

    public void increment(int id, boolean isCorrect) {
        Game game = ongoingGames.get(id);
        if (isCorrect) {
            game.incrementCorrect();
        }
        game.incrementAnswered();
    }

    public Integer[] getProgress(int id) {
        Game game = ongoingGames.get(id);
        return new Integer[]{game.getNumCorrect(), game.getNumAnswered(), game.getNumQuestions()};
    }

    public void endGame(int id) {
        ongoingGames.remove(id);
    }
}
