package com.project1.springbootproject1.controllers;

import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.UserRepository;
import com.project1.springbootproject1.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

//    @Setter(onMethod =@__({@Autowired}))
//    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) throws URISyntaxException {
//        try {
//            userRepository.save(user);
//            return ResponseEntity.created(new URI("http://localhost/users/" + user.getUserID())).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error adding a user");
//        }
        boolean success = false;
        try {
            success = userService.addUser(user);
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
        if (success) return ResponseEntity.created(new URI("http://localhost/users/" + user.getUserID())).build();
        else return ResponseEntity.internalServerError().body("Error adding a user");
    }

    @GetMapping("{userID}")
    public ResponseEntity getUserWithId(@PathVariable int userID) {
        // return ResponseEntity.ok(userRepository.getById(userID));
//        Optional<User> user = userRepository.findById(userID);
//        if (user.isPresent()) {
//            return ResponseEntity.ok(user);
//        }
//        return ResponseEntity.notFound().build();
        Optional<User> user = userService.getUserById(userID);
        if (user != null && user.isPresent()) { return  ResponseEntity.ok(user); }
        return ResponseEntity.notFound().build();
    }
}
