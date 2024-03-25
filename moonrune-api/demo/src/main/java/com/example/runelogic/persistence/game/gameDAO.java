package com.example.runelogic.persistence.game;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.runelogic.model.Game;

@Component
public class gameDAO {
    private static final Logger LOG = Logger.getLogger(gameDAO.class.getName());
    
    private HashMap<Long, Game> ongoingGames = new HashMap<>();

    public gameDAO() {

    }

    public Game createGame(int numQuestions, int numAnswers, long sessionID) {
        Game game = new Game(numQuestions, numAnswers, sessionID);
        ongoingGames.put(sessionID, game);
        return game;
    }

    

}
