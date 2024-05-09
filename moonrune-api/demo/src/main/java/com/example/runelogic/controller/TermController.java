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

import com.example.runelogic.model.TermCollection;
import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.term.termDAO;
import com.example.runelogic.persistence.term.termDatabaseDAO;
// import com.example.runelogic.persistence.term.termFileDAO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @GetMapping("get/{collectionID}")
    public ResponseEntity<Term[]> getTerms(@PathVariable int collectionID) {
        LOG.info("GET /terms");
        try {
            LinkedHashMap<String, Term> terms = termThing.getTerms(collectionID, "");
            Term[] returnTerms = new Term[terms.size()];
            returnTerms = terms.values().toArray(returnTerms);
            if (!terms.isEmpty()) 
                return new ResponseEntity<>(returnTerms,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{collectionID}/{filter}")
    public ResponseEntity<Term[]> getTerms(@PathVariable int collectionID, @PathVariable String filter) {
        LOG.info("GET /terms/?name="+filter);
        try {
            LinkedHashMap<String, Term> terms = termThing.getTerms(collectionID, filter);
            Term[] returnTerms = new Term[terms.size()];
            returnTerms = terms.values().toArray(returnTerms);
            if (!terms.isEmpty()) 
                return new ResponseEntity<>(returnTerms,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("collections/{owner}")
    public ResponseEntity<TermCollection[]> getCollectionsByOwner(@PathVariable String owner) {
        try {
            TermCollection[] collection = termThing.getCollectionsByOwner(owner);
            TermCollection[] emptyCollection = new TermCollection[]{};
            if (collection.equals(emptyCollection)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(collection, HttpStatus.OK);
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
