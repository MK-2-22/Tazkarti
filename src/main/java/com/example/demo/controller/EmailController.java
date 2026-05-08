package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EmailService;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/admin/send-email")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body) {

        emailService.sendEmail(to, subject, body);

        return "redirect:/admin/dashboard";
    }
}