package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.model.Booking;
import com.example.demo.service.AdminService;
import com.example.demo.service.Bookingservice; 
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

    @Autowired
    private Bookingservice bookingService; // Type updated to Bookingservice

    // --- DASHBOARD ---
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("events", adminService.getAllEvents());
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/dashboard";
    }

    // --- EVENT MANAGEMENT ---
    @GetMapping("/events/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/add-event";
    }

    @PostMapping("/events/save")
    public String saveEvent(@ModelAttribute("event") Event event) {
        adminService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        Event event = adminService.getEventById(id);
        model.addAttribute("event", event);
        return "admin/edit-event";
    }

    @PostMapping("/events/update")
    public String updateEvent(@ModelAttribute("event") Event event) {
        adminService.updateEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        adminService.deleteEventById(id);
        return "redirect:/admin/dashboard";
    }

    // --- BOOKING MANAGEMENT ---
    @GetMapping("/bookings")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/bookings-list"; 
    }

    @PostMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/admin/bookings";
    }
}