package com.example.runelogic.controller;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.Game;
import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.game.gameDAO;

@RestController
@RequestMapping("game")
public class gameController {

    gameDAO dao;

    public gameController(gameDAO dao) {
        this.dao = dao;
    }

    @PostMapping("create")
    public ResponseEntity<Integer> createGame(int numQuestions, int numAnswers, 
    Term[] legalTerms, String questionType, String answerType) {
        int sessionID = dao.getSessionID();
        Game game = dao.createGame(numQuestions, numAnswers, sessionID,
        legalTerms, questionType, answerType);
        if (game == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(sessionID, HttpStatus.CREATED);
    } 

    @GetMapping("generate")
    public ResponseEntity<String[]> generateQuestion(long sessionID) {

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public ResponseEntity<Integer[]> getProgress(long sessionID) {
        Integer[] progress = dao.getProgress(sessionID);
        if (progress == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }

    @GetMapping("progress")
    public ResponseEntity<Boolean> progressGame(long sessionID, String answer) {
        boolean isCorrect = dao.checkAnswer(sessionID, answer);
        dao.increment(sessionID, isCorrect);
        return new ResponseEntity<>(isCorrect, HttpStatus.OK);
    }

    @GetMapping("active")
    public ResponseEntity<Boolean> isActive(long sessionID) {
        boolean isOver = dao.isActive(sessionID);
        return new ResponseEntity<>(isOver, HttpStatus.OK);
    }

    @DeleteMapping("end")
    public ResponseEntity<Void> endGame(long sessionID) {
        dao.endGame(sessionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
