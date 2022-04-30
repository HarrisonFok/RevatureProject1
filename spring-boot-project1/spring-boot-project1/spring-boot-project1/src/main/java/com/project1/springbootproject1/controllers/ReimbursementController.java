package com.project1.springbootproject1.controllers;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.ReimbursementRepository;
import com.project1.springbootproject1.repositories.UserRepository;
import com.project1.springbootproject1.service.ReimbursementService;
import com.project1.springbootproject1.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

//    @Setter(onMethod =@__({@Autowired}) )
//    private ReimbursementRepository reimbursementRepository;
//
//    @Setter(onMethod =@__({@Autowired}))
//    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ReimbursementService reimbursementService;

    @GetMapping
    public ResponseEntity getAllReimbursementRequests() {
//        List<User> checkUser = userRepository.findAllByEmail(email);
//        if (checkUser.get(0).isEmployee()) {
//            throw new InvalidOperationException("User is an employee. Cannot view all reimbursement requests");
//        }
        return reimbursementService.getAllReimbursementRequests();
    }

    @GetMapping("me/{email}")
    public ResponseEntity getAllMyRequests(@PathVariable String email) throws InvalidOperationException {
//        List<User> checkUser = userRepository.findAllByEmail(email);
//        if (checkUser.get(0).isManager()) {
//            throw new InvalidOperationException("User is a manager. Cannot view only my reimbursement requests.");
//        }
//        List<ReimbursementRequest> listOfRequests = reimbursementRepository.findAllByUserID(checkUser.get(0).getUserID());
//        return ResponseEntity.ok(listOfRequests);
        return reimbursementService.getAllMyRequests(email);
    }

    @PostMapping
    public ResponseEntity addReimbursement(@RequestBody ReimbursementRequest reimbursementRequest) throws InvalidOperationException {
        //System.out.println(reimbursementRequest);
//        int userID = reimbursementRequest.getUserID();
//        List<User> checkUser = userRepository.findAllByUserID(userID);
//        if (checkUser.get(0).isManager()) {
//            throw new InvalidOperationException("User is a manager. Cannot submit a reimbursement request.");
//        }
//        try {
//            reimbursementRepository.save(reimbursementRequest);
//            return ResponseEntity.created(new URI("http://localhost/reimbursements/" + reimbursementRequest.getReimbursementID())).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
//        }
        return reimbursementService.addReimbursement(reimbursementRequest);
    }

    @GetMapping("{reimbursementID}")
    public ResponseEntity getReimbursementById(@PathVariable int reimbursementID) {
//        Optional<ReimbursementRequest> r = reimbursementRepository.findById(reimbursementID);
//        if (r.isPresent()) {
//            return ResponseEntity.ok(r);
//        }
//        return ResponseEntity.notFound().build();
        return reimbursementService.getReimbursementById(reimbursementID);
    }

    @PutMapping("{reimbursementID}/{action}")
    public ResponseEntity respondToReimbursement(@RequestBody ReimbursementRequest reimbursementRequest, @PathVariable String action) throws InvalidOperationException {
//        int userID = reimbursementRequest.getUserID();
//        List<User> checkUser = userRepository.findAllByUserID(userID);
//        if (checkUser.get(0).isEmployee()) {
//            throw new InvalidOperationException("User is an employee. Cannot respond to reimbursement requests");
//        }
//        if (!reimbursementRequest.isValidAction(action)) {
//            throw new InvalidOperationException("Action not allowed. It must either be approve, decline, or reassign");
//        }
//        try {
//            reimbursementRequest.setStatus(action);
//            reimbursementRepository.save(reimbursementRequest);
//            return ResponseEntity.ok(new URI("http://localhost/reimbursements/" + reimbursementRequest.getReimbursementID()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
//        }
        return  reimbursementService.respondToReimbursement(reimbursementRequest, action);
    }
    //        Optional<ReimbursementRequest> r = reimbursementRepository.findById(reimbursementID);
    //        if (r.isPresent()) {
    ////            if (!r.get().isValidAction(action)) {
    ////                throw new InvalidOperationException("Action not allowed. It must either be approve, decline, or reassign");
    ////            }
    //            reimbursementRepository.setReimbursementRequestStatusByID(reimbursementID, action);
    //            return ResponseEntity.ok(r);
    //        }
}
