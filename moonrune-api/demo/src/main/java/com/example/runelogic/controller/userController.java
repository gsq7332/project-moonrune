package com.example.runelogic.controller;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.runelogic.model.User;
import com.example.runelogic.persistence.user.userDao;

@RestController
@RequestMapping("user")
public class userController {
    
    private userDao dao;
    private static final Logger LOG = Logger.getLogger(userController.class.getName());

    public userController(userDao dao) {
        this.dao = dao;
    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        LOG.info("GET /" + username);
        User user = dao.getUser(username);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{username}/{password}")
    public ResponseEntity<User> signIn(@PathVariable String username, @PathVariable String password) {
        LOG.info("GET /" + username + "/" + password);
        User user = dao.signIn(username, password);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("{username}/{password}")
    public ResponseEntity<User> createUser(@PathVariable String username, @PathVariable String password) {
        LOG.info("POST /" + username + "/" + password);
        User user = dao.createUser(username, password);
        if (user == null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("{username}/{bio}")
    public ResponseEntity<User> updateBio(@PathVariable String username, @PathVariable String bio) {
        LOG.info("PUT /" + username + "/" + bio);
        User user = dao.updateBio(username, bio);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Boolean> removeUser(@PathVariable String username) {
        LOG.info("DELETE /" + username);
        boolean wasThere = dao.removeUser(username);
        if (wasThere) return new ResponseEntity<>(wasThere, HttpStatus.OK);
        return new ResponseEntity<>(wasThere, HttpStatus.NOT_FOUND);
        
    }

}