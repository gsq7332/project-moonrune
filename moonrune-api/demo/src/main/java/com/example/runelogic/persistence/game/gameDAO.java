package com.example.runelogic.persistence.game;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.runelogic.model.Game;

@Component
public class gameDAO {
    private static final Logger LOG = Logger.getLogger(gameDAO.class.getName());
    
    private HashMap<Long, Game> ongoingGames;

    public gameDAO() {
        ongoingGames = new HashMap<>();
    }

    public Game createGame(int numQuestions, int numAnswers, long sessionID) {
        Game game = new Game(numQuestions, numAnswers, sessionID);
        ongoingGames.put(sessionID, game);
        return game;
    }

    public void setAnswer(long sessionID, String answer) {
        Game game = ongoingGames.get(sessionID);
        game.setCorrect(answer);
    }

    public boolean checkAnswer(long sessionID, String answer) {
        Game game = ongoingGames.get(sessionID);
        if (game == null) return false;
        return game.isCorrect(answer);
    }

    public boolean isActive(long sessionID) {
        Game game = ongoingGames.get(sessionID);
        return game.isOver();
    }

    public void increment(long sessionID, boolean isCorrect) {
        Game game = ongoingGames.get(sessionID);
        if (isCorrect) {
            game.incrementCorrect();
        }
        game.incrementAnswered();
    }

    public int[] getProgress(long sessionID) {
        Game game = ongoingGames.get(sessionID);
        return new int[]{game.getNumCorrect(), game.getNumAnswered(), game.getNumQuestions()};
    }

    public void endGame(long sessionID) {
        ongoingGames.remove(sessionID);
    }
}
