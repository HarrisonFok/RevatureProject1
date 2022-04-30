package com.project1.springbootproject1email.controllers;

import com.project1.springbootproject1email.entities.Email;
import com.project1.springbootproject1email.repositories.EmailRepository;
import com.project1.springbootproject1email.services.EmailService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("emails")
public class EmailController {

//    @Setter(onMethod = @__({@Autowired}))
//    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailService.getAllEmails());
    }

    @PostMapping
    public ResponseEntity sendEmail(@RequestBody Email email) {
//        try {
//            emailRepository.save(email);
//            return ResponseEntity.created(new URI("http://localhost/emails/" + email.getEmailID())).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error occurred while sending an email");
//        }
        return emailService.sendEmail(email);
    }

    @GetMapping("{id}")
    public ResponseEntity getEmailWithId(@PathVariable int id) {
//        Optional<Email> email = emailRepository.findById(id);
//        if (email.isPresent()) {
//            return ResponseEntity.ok(email);
//        }
//        return ResponseEntity.notFound().build();
        return emailService.getEmailByID(id);
    }
}
