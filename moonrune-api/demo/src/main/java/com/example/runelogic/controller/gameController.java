package com.example.runelogic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.Game;
import com.example.runelogic.persistence.game.gameDAO;

@RestController
@RequestMapping("game")
public class gameController {

    gameDAO dao;

    public gameController(gameDAO dao) {
        this.dao = dao;
    }

    @RequestMapping("session")
    public ResponseEntity<Integer> getSessionID() {
        int sessionID = dao.getSessionID();
        return new ResponseEntity<>(sessionID, HttpStatus.CREATED);
    }

    @RequestMapping("create")
    public ResponseEntity<Game> createGame(int sessionID, int numQuestions, int numAnswers) {
        Game game = dao.createGame(numQuestions, numAnswers, sessionID);
        if (game == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    } 

    @RequestMapping("")
    public ResponseEntity<Integer[]> getProgress(long sessionID) {
        Integer[] progress = dao.getProgress(sessionID);
        if (progress == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }

    @RequestMapping("progress")
    public ResponseEntity<Boolean> progressGame(long sessionID, String answer) {
        boolean isCorrect = dao.checkAnswer(sessionID, answer);
        dao.increment(sessionID, isCorrect);
        return new ResponseEntity<>(isCorrect, HttpStatus.OK);
    }

    @RequestMapping("active")
    public ResponseEntity<Boolean> isActive(long sessionID) {
        boolean isOver = dao.isActive(sessionID);
        return new ResponseEntity<>(isOver, HttpStatus.OK);
    }

    @RequestMapping("end")
    public ResponseEntity<Void> endGame(long sessionID) {
        dao.endGame(sessionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
