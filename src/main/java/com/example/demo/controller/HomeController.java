package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.HomeService;



@Controller
public class HomeController {
    private HomeService hs;

    public HomeController(HomeService hs)
    {
        this.hs = hs;
    }
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String ViewHome(Model model) {
        model.addAttribute("eventsCount",hs.countEvents());
        model.addAttribute("featuredEvents",hs.getFeaturedEvents());

        return "home";
    }


    
    
}
