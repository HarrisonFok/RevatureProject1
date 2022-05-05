package com.project1.springbootproject1.service;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.exceptions.InvalidOperationException;
import com.project1.springbootproject1.repositories.ReimbursementRepository;
import com.project1.springbootproject1.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    public ReimbursementService(UserRepository userRepository, ReimbursementRepository reimbursementRepository) {
        this.userRepository = userRepository;
        this.reimbursementRepository = reimbursementRepository;
    }

    public ResponseEntity getAllReimbursementRequests() {
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    public ResponseEntity getAllMyRequests(String email) throws InvalidOperationException {
        List<User> checkUser = userRepository.findAllByEmail(email);
        if (checkUser.size() > 0) {
            if (checkUser.get(0).isManager()) { throw new InvalidOperationException("User is a manager. Cannot view only my reimbursement requests."); }
        } else throw new InvalidOperationException("User does not exist");
        logger.info("Getting the reimbursement requests of the user with email " + email);
        List<ReimbursementRequest> listOfRequests = reimbursementRepository.findAllByUserID(checkUser.get(0).getUserID());
        return ResponseEntity.ok(listOfRequests);
    }

    public ResponseEntity addReimbursement(ReimbursementRequest reimbursementRequest) throws InvalidOperationException {
        if (reimbursementRequest == null) { throw new InvalidOperationException("The reimbursement request is null (when trying to add one)"); }
        int userID = reimbursementRequest.getUserID();
        List<User> checkUser = userRepository.findAllByUserID(userID);
        if (checkUser.size() > 0) {
            if (checkUser.get(0).isManager()) { throw new InvalidOperationException("User is a manager. Cannot submit a reimbursement request."); }
        } else throw new InvalidOperationException("User does not exist");
        try {
            reimbursementRepository.save(reimbursementRequest);
            logger.info("Adding a new reimbursement request");
            return ResponseEntity.created(new URI("http://localhost/reimbursements/" + reimbursementRequest.getReimbursementID())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement request");
        }
    }

    public ResponseEntity getReimbursementById(int reimbursementId) {
        Optional<ReimbursementRequest> r = reimbursementRepository.findById(reimbursementId);
        if (r.isPresent()) { logger.info("Getting reimbursement by ID " + reimbursementId); return ResponseEntity.ok(r); }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity respondToReimbursement(int reimbursementID, String action, String managerId) throws InvalidOperationException {
        ReimbursementRequest reimbursementRequest = reimbursementRepository.findAllByReimbursementID(reimbursementID).get(0);
        //List<User> checkUser = userRepository.findAllByUserID(reimbursementRequest.getUserID());
        if (!reimbursementRequest.isValidAction(action)) { throw new InvalidOperationException("Action not allowed. It must either be approve, decline, or reassign"); }
        try {
            if (!action.equalsIgnoreCase("reassign")) { reimbursementRequest.setStatus(action); }
            if (action.equalsIgnoreCase("reassign")) { reimbursementRequest.setManagerID(Integer.parseInt(managerId)); }
            logger.info("Responding to a reimbursement with ID " + reimbursementID);
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
