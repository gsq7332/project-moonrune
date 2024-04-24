package com.example.runelogic.controller;

import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.Game;
import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.game.gameDAO;
import com.example.runelogic.persistence.term.termDAO;

import java.util.logging.Logger;

@RestController
@RequestMapping("game")
public class gameController {

    gameDAO dao;
    termDAO tDao;
    private static final Logger LOG = Logger.getLogger(gameController.class.getName());

    public gameController(gameDAO dao, termDAO tDao) {
        this.dao = dao;
        this.tDao = tDao;
    }

    @PostMapping("create/{numQuestions}/{numAnswers}")
    public ResponseEntity<Integer> createGame(@PathVariable int numQuestions, @PathVariable int numAnswers) {
        LOG.info("POST /create/" + numQuestions + "/" + numAnswers);
        int sessionID = dao.createGame(numQuestions, numAnswers);
        return new ResponseEntity<>(sessionID, HttpStatus.CREATED);
    } 

    @PutMapping("question/{sessionID}/{questionType}/{answerType}")
    public ResponseEntity<Boolean> setQuestionType(@PathVariable int sessionID, @PathVariable String questionType, @PathVariable String answerType) {
        LOG.info("PUT /question/" + sessionID + "/" + questionType + "/" + answerType);
        boolean hasWorked = dao.setQuestionAnswer(sessionID, questionType, answerType);
        return new ResponseEntity<>(hasWorked, HttpStatus.OK);
    }

    @PutMapping("term/{sessionID}/{collection}")
    public ResponseEntity<Boolean> setLegalTerms(@PathVariable int sessionID, @PathVariable String collection) {
        LOG.info("PUT /term/" + sessionID + "/" + collection);
        LinkedHashMap<String, Term> terms = tDao.getTerms("");
        Term[] legalTerms = new Term[terms.size()];
        legalTerms = terms.values().toArray(legalTerms);
        boolean hasWorked = dao.setLegalTerms(sessionID, legalTerms);
        System.out.println(hasWorked);
        return new ResponseEntity<>(hasWorked, HttpStatus.OK);
    }

    @GetMapping("generate/{sessionID}")
    public ResponseEntity<String[]> generateQuestion(@PathVariable int sessionID) {
        LOG.info("GET /generate/" + sessionID);
        String[] terms = dao.generateAnswers(sessionID);
        if (terms == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @GetMapping("/{sessionID}")
    public ResponseEntity<Integer[]> getProgress(@PathVariable int sessionID) {
        LOG.info("GET /" + sessionID);
        Integer[] progress = dao.getProgress(sessionID);
        if (progress == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(progress, HttpStatus.OK);
    }

    @GetMapping("check/{sessionID}/{answer}")
    public ResponseEntity<Boolean> progressGame(@PathVariable int sessionID, @PathVariable String answer) {
        LOG.info("GET /progress/" + sessionID);
        boolean isCorrect = dao.checkAnswer(sessionID, answer);
        dao.increment(sessionID, isCorrect);
        return new ResponseEntity<>(isCorrect, HttpStatus.OK);
    }

    @GetMapping("active/{sessionID}")
    public ResponseEntity<Boolean> isOver(@PathVariable int sessionID) {
        LOG.info("GET /active/" + sessionID);
        boolean isOver = dao.isOver(sessionID);
        return new ResponseEntity<>(isOver, HttpStatus.OK);
    }

    @DeleteMapping("end/{sessionID}")
    public ResponseEntity<Void> endGame(@PathVariable int sessionID) {
        System.out.println("test");
        LOG.info("DELETE /end/" + sessionID);
        dao.endGame(sessionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
