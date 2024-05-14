package com.example.runelogic.controller;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.TermCollection;
import com.example.runelogic.model.terms.Term;
import com.example.runelogic.persistence.collection.collectionDAO;


@RestController
@RequestMapping("collections")
public class collectionController {
    private collectionDAO termThing;
    private static final Logger LOG = Logger.getLogger(TermController.class.getName());

public collectionController(collectionDAO termThing) {
        this.termThing = termThing;
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
}
