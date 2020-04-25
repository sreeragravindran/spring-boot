package com.springboot.demo.rest.controllers;

import com.springboot.demo.models.User;
import com.springboot.demo.repositories.IRepository;
import com.springboot.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> user(@PathVariable String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.save(user);
        return null;
        //return ResponseEntity.created("/user/" + user.getId());
    }
}
