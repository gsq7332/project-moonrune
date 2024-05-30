package com.example.runelogic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.term.termDatabaseDAO;

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


    @PutMapping("updateTerms/{id}")
    public ResponseEntity<Boolean> updateTerms(@PathVariable int id, @RequestBody Term[] terms) {
        try {
            boolean working = termThing.updateTermsInCollection(id, terms);
            return new ResponseEntity<>(working, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
