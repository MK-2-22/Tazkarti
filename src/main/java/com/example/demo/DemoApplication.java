package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@ticketzone.com") == null) {
                
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail("    ");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                
                admin.setRole("ROLE_ADMIN"); 

                userRepository.save(admin);
            }
        };
    }
}