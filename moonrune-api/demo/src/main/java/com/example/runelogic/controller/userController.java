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

    public ResponseEntity<User> getUser() {
        return null;
    }

    public ResponseEntity<User> createUser(String username, String password) {
        return null;
    }

    public ResponseEntity<User> removeUser(String username) {
        return null;
    }

}