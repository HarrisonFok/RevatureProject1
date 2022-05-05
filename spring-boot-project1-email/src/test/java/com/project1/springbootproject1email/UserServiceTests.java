package com.project1.springbootproject1email;

import com.project1.springbootproject1email.entities.User;
import com.project1.springbootproject1email.exceptions.InvalidOperationException;
import com.project1.springbootproject1email.repositories.UserRepository;
import com.project1.springbootproject1email.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.mock;

public class UserServiceTests {

    // Class to be tested
    private UserService userService;

    // Dependencies (to be mocked)
    private UserRepository userRepository;

    // Test data
    private User sampleUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void addUserShouldThrowException() {
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            userService.addUser(null);
        });
        Assertions.assertEquals("User is null when trying to add a user", ex.getMessage(), "Exception not thrown when adding null user");
    }

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUserID(1);
        user.setFirstName("Test Firstname");
        user.setLastName("Test lastname");
        user.setUsername("Test username");
        user.setEmail("Test email");
        user.setPassword("Test password");
        user.setRole("Test role");
        try {
            Assertions.assertTrue(userService.addUser(user));
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUserEmpty() {
        Assertions.assertNull(userService.getUserById(1));
    }

    @Test
    public void testDependency() {
        Assertions.assertEquals(userService.getUserRepository(), userRepository);
    }

    @Test
    public void getAllUsers() {
        Assertions.assertEquals(userService.getAllUsers(), ResponseEntity.ok(userRepository.findAll()));
    }
}


//public class UserServiceTests {
//
//    // Class to be tested
//    private UserService userService;
//
//    // Dependencies (to be mocked)
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setup() {
//        userRepository = mock(UserRepository.class);
//        userService = new UserService(userRepository);
//    }
//
//    @Test
//    public void getAllUsersTest() {
//        Assertions.assertEquals(userService.getAllUsers(), ResponseEntity.ok(userRepository.findAll()));
//    }
//
//    @Test
//    public void addUserShouldThrowException() {
//        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
//            userService.addUser(null);
//        });
//        Assertions.assertEquals("User is null when trying to add a user", ex.getMessage(), "Exception not thrown when adding null user");
//    }
//
//    @Test
//    public void addUserTest() {
//        User user = new User();
//        user.setUserID(1);
//        user.setFirstName("Test Firstname");
//        user.setLastName("Test lastname");
//        user.setUsername("Test username");
//        user.setEmail("Test email");
//        user.setPassword("Test password");
//        user.setRole("Test role");
//        try {
//            Assertions.assertTrue(userService.addUser(user));
//        } catch (InvalidOperationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testGetUserEmpty() {
//        Assertions.assertNull(userService.getUserById(1));
//    }
//}
