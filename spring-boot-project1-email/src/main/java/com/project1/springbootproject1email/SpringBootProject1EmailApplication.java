package com.project1.springbootproject1email;

import com.project1.springbootproject1email.entities.User;
import com.project1.springbootproject1email.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootProject1EmailApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProject1EmailApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> { for (User u: userRepository.findAllByUserID(1)) { System.out.println(u); } };
	}
}
