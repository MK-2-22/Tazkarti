package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EmailService;

import org.springframework.stereotype.Controller;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService; 

    @PostMapping("/contact")
    public String sendMessage(@RequestParam String email, @RequestParam String message) {
        emailService.sendEmail("admin@ticketzone.com", "New Support Message", message);
        return "redirect:/contact";
    }
}