package com.springboot.demo.rest.controllers;

import com.springboot.demo.models.User;
import com.springboot.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

        User user = userService.get(id);

        // HATEOAS implementation ( Hypermedia as the Engine of Application State )
        EntityModel<User> userModel = new EntityModel<>(user);
        userModel.add(linkTo(methodOn(this.getClass()).users()).withRel("all-users"));

        return ResponseEntity.ok(userModel);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(
            @Valid // ensures the input is validated
            @RequestBody
                    User user){
        User savedUser = userService.save(user);
        URI uri =  ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
