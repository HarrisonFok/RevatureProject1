package com.project1.springbootproject1.service;

import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    /**
     * Get all the users
     * @return all the users
     */
    public ResponseEntity getAllUsers() { logger.info("Getting all users"); return ResponseEntity.ok(userRepository.findAll()); }

    /**
     * Return the user repository
     * @return the user repository
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Add a user to the database
     * @param user - user to be added
     * @return true if successful, false otherwise
     * @throws InvalidOperationException - thrown if user is null
     */
    public boolean addUser(User user) throws InvalidOperationException {
        if (user == null) throw new InvalidOperationException("User is null when trying to add a user");
        try {
            logger.info("Adding a user");
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the user by user ID
     * @param userId - user ID of the user
     * @return the user object
     */
    public Optional<User> getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            logger.info("Getting a user by ID " + userId);
            return user;
        }
        return null;
    }
}
