package com.project1.springbootproject1.service;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.ReimbursementRepository;
import com.project1.springbootproject1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    public ResponseEntity getAllReimbursementRequests() {
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    public ResponseEntity getAllMyRequests(String email) throws InvalidOperationException {
        List<User> checkUser = userRepository.findAllByEmail(email);
        if (checkUser.get(0).isManager()) {
            throw new InvalidOperationException("User is a manager. Cannot view only my reimbursement requests.");
        }
        List<ReimbursementRequest> listOfRequests = reimbursementRepository.findAllByUserID(checkUser.get(0).getUserID());
        return ResponseEntity.ok(listOfRequests);
    }

    public ResponseEntity addReimbursement(ReimbursementRequest reimbursementRequest) throws InvalidOperationException {
        int userID = reimbursementRequest.getUserID();
        List<User> checkUser = userRepository.findAllByUserID(userID);
        if (checkUser.get(0).isManager()) {
            throw new InvalidOperationException("User is a manager. Cannot submit a reimbursement request.");
        }
        try {
            reimbursementRepository.save(reimbursementRequest);
            return ResponseEntity.created(new URI("http://localhost/reimbursements/" + reimbursementRequest.getReimbursementID())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
        }
    }

    public ResponseEntity getReimbursementById(int reimbursementId) {
        Optional<ReimbursementRequest> r = reimbursementRepository.findById(reimbursementId);
        if (r.isPresent()) {
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity respondToReimbursement(ReimbursementRequest reimbursementRequest, String action) throws InvalidOperationException {
        int userID = reimbursementRequest.getUserID();
        List<User> checkUser = userRepository.findAllByUserID(userID);
        if (checkUser.get(0).isEmployee()) {
            throw new InvalidOperationException("User is an employee. Cannot respond to reimbursement requests");
        }
        System.out.println(action.equalsIgnoreCase("approve"));
        if (!reimbursementRequest.isValidAction(action)) {
            throw new InvalidOperationException("Action not allowed. It must either be approve, decline, or reassign");
        }
        try {
            reimbursementRequest.setStatus(action);
            reimbursementRepository.save(reimbursementRequest);
            return ResponseEntity.ok(new URI("http://localhost/reimbursements/" + reimbursementRequest.getReimbursementID()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
        }
//        List<ReimbursementRequest> request = reimbursementRepository.findAllByReimbursementID(reimbursementID);
//        List<User> checkUser = userRepository.findAllByUserID(request.get(0).getUserID());
//        if (checkUser.get(0).isEmployee()) {
//            throw new InvalidOperationException("User is an employee. Cannot respond to reimbursement requests");
//        }
//        if (!request.get(0).isValidAction(action)) {
//            throw new InvalidOperationException("Action not allowed. It must either be approve, decline, or reassign");
//        }
//        try {
//            request.get(0).setStatus(action);
//            reimbursementRepository.save(request.get(0));
//            return ResponseEntity.ok(new URI("http://localhost/reimbursements/" + request.get(0).getReimbursementID()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
//        }
    }
}
