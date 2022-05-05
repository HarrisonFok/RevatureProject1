package com.project1.springbootproject1.repositories;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReimbursementRepository extends JpaRepository<ReimbursementRequest, Integer> {
//    List<ReimbursementRequest> findAllByStatus(String status);
    List<ReimbursementRequest> findAllByUserID(int userID);
    List<ReimbursementRequest> findAllByReimbursementID(int reimbursementID);

//    @Modifying
//    @Query("UPDATE ReimbursementRequest r SET r.status = ?1 WHERE r.reimbursementID = ?2")
//    void setReimbursementRequestStatusByID(int reimbursementID, String status);
}
