package com.project1.springbootproject1;

import com.project1.springbootproject1.entities.ReimbursementRequest;
import com.project1.springbootproject1.entities.User;
import com.project1.springbootproject1.repositories.ReimbursementRepository;
import com.project1.springbootproject1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootProject1Application {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReimbursementRepository reimbursementRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProject1Application.class, args);
	}

	@Bean
	CommandLineRunner runner() {
//		return args -> {
//			for (User u: userRepository.findAllByUsername("alicebob")) {
//				System.out.println(u);
//			}
//		};
		return args -> {
			for (ReimbursementRequest r: reimbursementRepository.findAllByUserID(1)) {
				System.out.println(r);
			}
		};
	}
}
