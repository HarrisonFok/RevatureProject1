package com.project1.springbootproject1email.services;

import com.project1.springbootproject1email.entities.Email;
import com.project1.springbootproject1email.exceptions.InvalidOperationException;
import com.project1.springbootproject1email.repositories.EmailRepository;
import com.project1.springbootproject1email.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class EmailService {

    final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private EmailRepository emailRepository;
    private UserRepository userRepository;

    public EmailService(EmailRepository emailRepository , UserRepository userRepository) { this.emailRepository = emailRepository; this.userRepository = userRepository; }

    public ResponseEntity getAllEmails() { logger.info("Getting all users"); return ResponseEntity.ok(emailRepository.findAll()); }

    public ResponseEntity sendEmail(Email email) throws InvalidOperationException {
        if (email == null) { throw new InvalidOperationException("Email is null when trying to add an email"); }
        if (this.userRepository.findAllByEmail(email.getFromEmail()).size() == 0 || this.userRepository.findAllByEmail(email.getToEmail()).size() == 0) throw new InvalidOperationException("From email and/or To email doesn't exist");
        try {
            logger.info("Sending an email");
            emailRepository.save(email);
            return ResponseEntity.created(new URI("http://localhost/emails/" + email.getEmailID())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while sending an email");
        }
    }

    public ResponseEntity getEmailByID(int id) {
        Optional<Email> email = emailRepository.findById(id);
        if (email.isPresent()) {
            logger.info("Getting a user by ID " + id);
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }
}
