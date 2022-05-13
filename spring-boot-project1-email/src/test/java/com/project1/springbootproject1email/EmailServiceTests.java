package com.project1.springbootproject1email;

import com.project1.springbootproject1email.entities.Email;
import com.project1.springbootproject1email.entities.User;
import com.project1.springbootproject1email.exceptions.InvalidOperationException;
import com.project1.springbootproject1email.repositories.EmailRepository;
import com.project1.springbootproject1email.repositories.UserRepository;
import com.project1.springbootproject1email.services.EmailService;
import com.project1.springbootproject1email.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.mock;

public class EmailServiceTests {

    // Class to be tested
    private EmailService emailService;

    // Dependencies (to be mocked)
    private EmailRepository emailRepository;
    private UserRepository userRepository;
    private UserService userService;

    // Test data
    private Email email;

    @BeforeEach
    public void setup() {
        emailRepository = mock(EmailRepository.class);
        userRepository = mock(UserRepository.class);
        emailService = new EmailService(emailRepository, userRepository);
        userService = new UserService(userRepository);
    }

    @Test
    public void getAllEmailsShouldBeEmpty() {
        Assertions.assertEquals(emailService.getAllEmails(), ResponseEntity.ok(emailRepository.findAll()));
    }

    @Test
    public void addEmailShouldThrowException() {
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            emailService.sendEmail(null);
        });
        Assertions.assertEquals("Email is null when trying to add an email", ex.getMessage(), "Exception not thrown when adding null email");
    }

    @Test
    public void addEmailButEmailDoesnotExist() {
        Email email = new Email();
        email.setEmailID(222);
        email.setContent("HI");
        try {
            email.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        email.setToEmail("zzzzzzzzzzzzzzzzzz");
        email.setSubject("Hey man");
        email.setFromEmail("zzzzzzzzzzz");
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            emailService.sendEmail(email);
        });
        Assertions.assertEquals("From email and/or To email doesn't exist", ex.getMessage(), "From or To email doesn't exist");
    }

    @Test
    public void getEmailEmpty() {
        Assertions.assertEquals(emailService.getEmailByID(1), ResponseEntity.notFound().build());
    }

//    @Test
//    public void addEmail() {
////        User user1 = new User(1, "Test Firstname", "Test lastname", "Test username", "Test@email1.com", "Test password", "Test role");
////        User user2 = new User(1, "Test Firstname", "Test lastname", "Test username", "Test@email2.com", "Test password", "Test role");
////        try {
////            userService.addUser(user1);
////            userService.addUser(user2);
////        } catch (InvalidOperationException e) {
////            e.printStackTrace();
////        }
////        System.out.println(userService.getAllUsers());
//        Email email = new Email();
//        email.setEmailID(1);
//        email.setFromEmail("eh@eh.com");
//        email.setToEmail("eh@eh.com");
//        email.setContent("TestingContent");
//        email.setSubject("TestingSubject");
//        try {
//            ResponseEntity r = emailService.sendEmail(email);
//            System.out.println(r.getBody());
//        } catch (InvalidOperationException e) {
//            e.printStackTrace();
//        }
//    }
}
