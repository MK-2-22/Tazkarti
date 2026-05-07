package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupportController {

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/support")
    public String supportPage() {
        return "support";
    }

    @PostMapping("/support/send")
    public String handleSupportForm(@RequestParam String email,  @RequestParam String message,  Model model) {
                
        if (email == null || email.isEmpty() || !email.contains("@")) {
            model.addAttribute("error", "Invalid Email");
            return "support";
        }

        if (message == null || message.length() < 10) {
            model.addAttribute("error", "Message is too short, Please provide more details.");
            return "support";
        }

        model.addAttribute("success", "Your request has been sent successfully!");
        
        System.out.println("Form received from: " + email);
        
        return "support";
    }
}