package com.project1.springbootproject1;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.ReimbursementRepository;
import com.project1.springbootproject1.repositories.UserRepository;
import com.project1.springbootproject1.service.ReimbursementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;


public class ReimbursementServiceTests {

    // Class to be tested
    private ReimbursementService reimbursementService;

    // Dependencies (to be mocked)
    private UserRepository userRepository;
    private ReimbursementRepository reimbursementRepository;

    // Test data
    private ReimbursementRequest reimbursementRequest;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        reimbursementRepository = mock(ReimbursementRepository.class);
        reimbursementService = new ReimbursementService(userRepository, reimbursementRepository);
    }

    @Test
    public void getAllReimbursementRequests() {
        Assertions.assertEquals(reimbursementService.getAllReimbursementRequests(), ResponseEntity.ok(reimbursementRepository.findAll()));
    }

    @Test
    public void getReimbursementEmpty() {
        Assertions.assertEquals(reimbursementService.getReimbursementById(1), ResponseEntity.notFound().build());
    }

    @Test
    public void testAddReimbursementRequestNonExistingUser() {
        ReimbursementRequest r = new ReimbursementRequest();
        r.setReimbursementID(1);
        r.setDescription("HI");
        r.setManagerID(2);
        r.setStatus("pending");
        r.setTotal(100);
        r.setUserID(1);
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            reimbursementService.addReimbursement(r);
        });
        Assertions.assertEquals("User does not exist", ex.getMessage(), "Didn't throw exception when adding reimbursement request (and user doesn't exist)");
    }

    @Test
    public void testGetAllMyReimbursementRequestsNonExistingUser() {
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            reimbursementService.getAllMyRequests("notfound.com");
        });
        Assertions.assertEquals("User does not exist", ex.getMessage(), "Didn't throw exception when getting all my reimbursement requests (and user doesn't exist)");
    }

//    @Test
//    public void testRespondToReimbursement() {
//        ReimbursementRequest r = new ReimbursementRequest();
//        r.setReimbursementID(1);
//        try {
//            reimbursementService.addReimbursement(r);
//            reimbursementService.respondToReimbursement(1, "approve", "");
//        } catch (InvalidOperationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(r.getStatus());
//        Assertions.assertEquals(r.getStatus(), "approve");
//    }
//
//    @Test
//    public void testRespondToReimbursementShouldThrowException() {
//        ReimbursementRequest r = new ReimbursementRequest();
//        r.setReimbursementID(1);
//        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
//            reimbursementService.respondToReimbursement(1, "I say no", "");
//        });
//        Assertions.assertEquals("Action not allowed. It must either be approve, decline, or reassign", ex.getMessage(), "Didn't throw exception when responding to a request with an invalid action");
//    }

    @Test
    public void testNullAddReimbursementRequest() {
        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
            reimbursementService.addReimbursement(null);
        });
        Assertions.assertEquals("The reimbursement request is null (when trying to add one)", ex.getMessage(), "Didn't throw an exception when adding a null reimbursement request");
    }
}

//public class ReimbursementServiceTests {
//
//    // Class to be tested
//    private ReimbursementService reimbursementService;
//
//    // Dependencies (to be mocked)
//    private UserRepository userRepository;
//    private ReimbursementRepository reimbursementRepository;
//
//    // Test data
//    private ReimbursementRequest reimbursementRequest;
//
//    @BeforeEach
//    public void setup() {
//        userRepository = mock(UserRepository.class);
//        reimbursementRepository = mock(ReimbursementRepository.class);
//        reimbursementService = new ReimbursementService(userRepository, reimbursementRepository);
//    }
//
//    @Test
//    public void getAllReimbursementRequests() {
//        Assertions.assertEquals(reimbursementService.getAllReimbursementRequests(), ResponseEntity.ok(reimbursementRepository.findAll()));
//    }
//
//    @Test
//    public void getReimbursementEmpty() {
//        Assertions.assertEquals(reimbursementService.getReimbursementById(1), ResponseEntity.notFound().build());
//    }
//
////    @Test
////    public void testAddReimbursementRequestNonExistingUser() {
////        ReimbursementRequest r = new ReimbursementRequest();
////        r.setUserID(1);
////        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
////            reimbursementService.addReimbursement(r);
////        });
////        Assertions.assertEquals("User does not exist", ex.getMessage(), "Didn't throw exception when adding reimbursement request (and user doesn't exist)");
////    }
////
////    @Test
////    public void testGetAllMyReimbursementRequestsNonExistingUser() {
////        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
////            reimbursementService.getAllMyRequests("notfound.com");
////        });
////        Assertions.assertEquals("User does not exist", ex.getMessage(), "Didn't throw exception when getting all my reimbursement requests (and user doesn't exist)");
////    }
//
////    @Test
////    public void testRespondToReimbursement() {
////        ReimbursementRequest r = new ReimbursementRequest();
////        r.setReimbursementID(1);
////        try {
////            reimbursementService.addReimbursement(r);
////            reimbursementService.respondToReimbursement(1, "approve", "");
////        } catch (InvalidOperationException e) {
////            e.printStackTrace();
////        }
////        System.out.println(r.getStatus());
////        Assertions.assertEquals(r.getStatus(), "approve");
////    }
////
////    @Test
////    public void testRespondToReimbursementShouldThrowException() {
////        ReimbursementRequest r = new ReimbursementRequest();
////        r.setReimbursementID(1);
////        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
////            reimbursementService.respondToReimbursement(1, "I say no", "");
////        });
////        Assertions.assertEquals("Action not allowed. It must either be approve, decline, or reassign", ex.getMessage(), "Didn't throw exception when responding to a request with an invalid action");
////    }
//
//    @Test
//    public void testNullAddReimbursementRequest() {
//        ReimbursementRequest r = new ReimbursementRequest();
//        InvalidOperationException ex = Assertions.assertThrows(InvalidOperationException.class, () -> {
//            reimbursementService.addReimbursement(null);
//        });
//        Assertions.assertEquals("The reimbursement request is null (when trying to add one)", ex.getMessage(), "Didn't throw an exception when adding a null reimbursement request");
//    }
//}
