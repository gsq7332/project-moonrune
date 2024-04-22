package com.example.runelogic.persistence.game;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.runelogic.model.Game;
import com.example.runelogic.model.terms.Term;

@Component
public class gameDAO {
    private static final Logger LOG = Logger.getLogger(gameDAO.class.getName());
    
    private int sessionID = 0;

    private HashMap<Integer, Game> ongoingGames;

    public gameDAO() {
        ongoingGames = new HashMap<>();
    }

    public int getSessionID() {
        int currSession = sessionID;
        sessionID++;
        return currSession;
    }

    public Game createGame(int numQuestions, int numAnswers, int sessionID) {
        Game game = new Game(numQuestions, numAnswers, sessionID);
        ongoingGames.put(sessionID, game);
        return game;
    }

    public boolean setQuestionAnswer(int sessionID, String questionType, String answerType) {
        Game game = ongoingGames.get(sessionID);
        return game.setQuestionAnswer(questionType, answerType);
    }

    public boolean setLegalTerms(int sessionID, Term[] legalTerms) {
        Game game = ongoingGames.get(sessionID);
        return game.setLegalTerms(legalTerms);
    }

    public String[] generateAnswers(int sessionID) {
        Game game = ongoingGames.get(sessionID);
        return game.generateTerms();
    }

    public void setAnswer(int sessionID, String answer) {
        Game game = ongoingGames.get(sessionID);
        game.setCorrect(answer);
    }

    public boolean checkAnswer(int sessionID, String answer) {
        Game game = ongoingGames.get(sessionID);
        if (game == null) return false;
        return game.isCorrect(answer);
    }

    public boolean isOver(int sessionID) {
        Game game = ongoingGames.get(sessionID);
        return game.isOver();
    }

    public void increment(int sessionID, boolean isCorrect) {
        Game game = ongoingGames.get(sessionID);
        if (isCorrect) {
            game.incrementCorrect();
        }
        game.incrementAnswered();
    }

    public Integer[] getProgress(int sessionID) {
        Game game = ongoingGames.get(sessionID);
        return new Integer[]{game.getNumCorrect(), game.getNumAnswered(), game.getNumQuestions()};
    }

    public void endGame(int sessionID) {
        ongoingGames.remove(sessionID);
    }
}
