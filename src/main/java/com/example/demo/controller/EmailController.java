package com.example.demo.controller;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/support")
    public String sendMessage(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String message) {

        String body =
                "Name: " + name +
                "\nEmail: " + email +
                "\nMessage: " + message;

        emailService.sendEmail(
                "bodyamrbody2006@gmail.com@gmail.com",
                "TicketZone Support Message",
                body
        );

        return "redirect:/support";
    }
}