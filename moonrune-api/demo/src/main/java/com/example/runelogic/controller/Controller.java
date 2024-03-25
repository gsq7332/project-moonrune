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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.Term;
import com.example.runelogic.persistence.termDAO;
import com.example.runelogic.persistence.termFileDAO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("terms")
public class Controller {
    private termDAO termThing;
    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    public Controller(termFileDAO termThing) {
        this.termThing = termThing;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Term> getTerm(@PathVariable String name) {
        LOG.info("GET /terms/" + name);
        try {
            Term term = termThing.getTerm(name);
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

    @GetMapping("/random")
    public ResponseEntity<Term> getRandomTerm() {
        LOG.info("GET /terms/random");
        try {
            Term term = termThing.getRandomTerm();
            if (term != null)
                return new ResponseEntity<Term>(term,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Term[]> getTerms() {
        LOG.info("GET /terms");
        try {
            LinkedHashMap<String, Term> terms = termThing.getTerms("");
            Term[] returnTerms = new Term[terms.size()];
            returnTerms = terms.values().toArray(returnTerms);
            if (!terms.isEmpty()) 
                return new ResponseEntity<>(returnTerms,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Term[]> getTerms(@RequestParam String filter) {
        LOG.info("GET /terms/?name="+filter);
        try {
            LinkedHashMap<String, Term> terms = termThing.getTerms(filter);
            Term[] returnTerms = new Term[terms.size()];
            returnTerms = terms.values().toArray(returnTerms);
            if (!terms.isEmpty()) 
                return new ResponseEntity<>(returnTerms,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Term> createTerm(@RequestBody String name, @RequestBody ArrayList<String> meanings) {
        LOG.info("POST /terms " + name);
        try {
            Term newTerm = termThing.createTerm(name, meanings);
            if (newTerm == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            return new ResponseEntity<>(newTerm,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Term> updateTerm(@RequestBody String name, @RequestBody ArrayList<String> change) {
        LOG.info("PUT /terms " + name);
        try {
            Term updated = termThing.updateTerm(name, change);
            if (updated == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Term> deleteTerm(@PathVariable String name) {
        LOG.info("DELETE /terms/" + name);
        try {
            boolean wasPresent = termThing.deleteTerm(name);
            if (wasPresent)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
