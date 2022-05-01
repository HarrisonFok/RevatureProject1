package com.project1.springbootproject1email.services;

import com.project1.springbootproject1email.entities.Email;
import com.project1.springbootproject1email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailRepository.findAll());
    }

    public ResponseEntity sendEmail(Email email) {
        try {
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
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }
}
