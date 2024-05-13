package com.example.runelogic.controller;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<User> getUser(String username) {
        User user = dao.getUser(username);
        return null;
    }

    public ResponseEntity<User> signIn(String username, String password) {
        User user = dao.signIn(username, password);
        return null;
    }

    public ResponseEntity<User> createUser(String username, String password) {
        User user = dao.createUser(username, password);
        return null;
    }

    public ResponseEntity<User> updateBio(String username, String bio) {
        User user = dao.updateBio(username, bio);
        return null;
    }

    public ResponseEntity<Boolean> removeUser(String username) {
        boolean wasThere = dao.removeUser(username);
        return null;
    }

}