package com.project1.springbootproject1email.repositories;

import com.project1.springbootproject1email.entities.Email;
import com.project1.springbootproject1email.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>  {
}
