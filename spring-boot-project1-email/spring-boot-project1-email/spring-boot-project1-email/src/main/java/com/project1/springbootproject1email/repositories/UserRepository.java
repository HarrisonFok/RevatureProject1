package com.project1.springbootproject1email.repositories;

import com.project1.springbootproject1email.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("From User where userID = :userID")
    List<User> findAllByUserID(@Param("userID") int userID);
}
