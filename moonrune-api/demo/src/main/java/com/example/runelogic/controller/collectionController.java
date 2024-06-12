package com.example.runelogic.controller;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

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

import com.example.runelogic.model.Filters;
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
            LinkedHashMap<Integer, Term> terms = termThing.getTerms(collectionID, null);
            Term[] returnTerms = new Term[terms.size()];
            returnTerms = terms.values().toArray(returnTerms);
            if (!terms.isEmpty()) 
                return new ResponseEntity<>(returnTerms,HttpStatus.OK);
            return new ResponseEntity<>(returnTerms, HttpStatus.NOT_FOUND); 
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{collectionID}")
    public ResponseEntity<Term[]> getTerms(@PathVariable int collectionID, @RequestBody Filters filter) {
        LOG.info("GET /terms/");
        try {
            LinkedHashMap<Integer, Term> terms = termThing.getTerms(collectionID, filter);
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

    @GetMapping("isOwner/{owner}/{id}")
    public ResponseEntity<Boolean> checkOwner(@PathVariable String owner, @PathVariable int id) {
        try {
            boolean isOwner = termThing.isOwner(owner, id);
            return new ResponseEntity<>(isOwner, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/{owner}")
    public ResponseEntity<Integer> createCollection(@PathVariable String owner) {
        try {
            int id = termThing.createCollection(owner, String.format("%s's Collection", owner));
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getCollection/{id}")
    public ResponseEntity<TermCollection> getCollectionInfo(@PathVariable int id) {
        try {
            TermCollection collection = termThing.getCollectionInfo(id);
            if (collection == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("setInfo/{id}/{name}/{desc}")
    public ResponseEntity<TermCollection> setCollectionInfo(@PathVariable int id, @PathVariable String name, @PathVariable String desc) {
        try {
            termThing.updateCollectionName(id, name);
            termThing.updateCollecitonDesc(id, desc);
            TermCollection collection = termThing.getCollectionInfo(id);
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteCollection(@PathVariable int id) {
        try {
            boolean wasPresent = termThing.deleteCollection(id);
            return new ResponseEntity<>(wasPresent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
