package com.example.runelogic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.Game;
import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.collection.collectionDAO;
import com.example.runelogic.persistence.game.gameDAO;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("game")
public class gameController {

    gameDAO dao;
    collectionDAO tDao;
    private static final Logger LOG = Logger.getLogger(gameController.class.getName());

    public gameController(gameDAO dao, collectionDAO tDao) {
        this.dao = dao;
        this.tDao = tDao;
    }

    @PostMapping("generateGame") 
    public ResponseEntity<Integer> createGameRevamped(@RequestBody Game game) {
        LOG.info("POST /generateGame");
        LinkedHashMap<Integer, Term> terms = tDao.getTerms(game.getCollectionID(), null);
        Term[] returnTerms = new Term[terms.size()];
        returnTerms = terms.values().toArray(returnTerms);
        game.setLegalTerms(returnTerms);
        int sessionID = dao.generateGame(game);
        return new ResponseEntity<>(sessionID, HttpStatus.CREATED);
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
