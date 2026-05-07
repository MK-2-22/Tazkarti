package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Event> events = adminService.getAllEvents();
        model.addAttribute("events", events);
        return "admin-dashboard"; 
    }

    @GetMapping("/add-event")
    public String showAddEventPage(Model model) {
        model.addAttribute("event", new Event()); 
        return "add-event"; 
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("event") Event event) {
        adminService.saveEvent(event);
        return "redirect:/admin/dashboard"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        adminService.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }
}