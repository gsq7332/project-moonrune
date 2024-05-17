package com.example.runelogic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.term.termDatabaseDAO;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("terms")
public class TermController {
    private termDatabaseDAO termThing;
    private static final Logger LOG = Logger.getLogger(TermController.class.getName());

    public TermController(termDatabaseDAO termThing) {
        this.termThing = termThing;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Term> getTerm(@PathVariable int id) {
        LOG.info("GET /terms/" + id);
        try {
            Term term = termThing.getTerm(id);
            if (term != null)
                return new ResponseEntity<Term>(term,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Term> createTerm(@RequestBody String name, @RequestBody int collection) {
        LOG.info("POST /terms " + name);
        try {
            Term newTerm = termThing.createTerm(name, collection);
            if (newTerm == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            return new ResponseEntity<>(newTerm,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Term> updateTerm(@RequestBody int id, @RequestBody ArrayList<String> change) {
        LOG.info("PUT /terms " + id);
        try {
            Term updated = termThing.updateTerm(id, change);
            if (updated == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Term> deleteTerm(@PathVariable int id) {
        LOG.info("DELETE /terms/" + id);
        try {
            boolean wasPresent = termThing.deleteTerm(id);
            if (wasPresent)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
