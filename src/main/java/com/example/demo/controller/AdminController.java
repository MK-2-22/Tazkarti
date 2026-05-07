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

    // 1. Dashboard - View all events
    @GetMapping("/dashboard")
    public String displayDashboard(Model model) {
        List<Event> events = adminService.getAllEvents();
        model.addAttribute("events", events);
        return "admin-dashboard";
    }

    // 2. Show the separate "Add" form
    @GetMapping("/add-event")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Event());
        return "add-event"; // Points to add-event.html
    }

    // 3. Show the separate "Edit" form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Event event = adminService.getEventById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "edit-event"; // Points to edit-event.html
        }
        return "redirect:/admin/dashboard";
    }

    // 4. Save logic (Handles both creating and updating)
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("event") Event event) {
        adminService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    // 5. Delete logic
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id) {
        adminService.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }
}