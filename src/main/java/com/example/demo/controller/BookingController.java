package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Booking;
import com.example.demo.model.Event;
import com.example.demo.model.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Bookingservice;

@Controller
public class BookingController {

    @Autowired
    private Bookingservice bookingService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/booking/{id}")
    public String showBookingPage(@PathVariable Long id,
                                  Model model,
                                  Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Event event = eventRepository.findById(id)
            .orElseThrow(() ->
            new RuntimeException("Event not found"));

        model.addAttribute("event", event);

        return "booking";
    }

    @GetMapping("/booking/payment")
    public String showPaymentPage(@RequestParam Long eventId,
                                  @RequestParam int tickets,
                                  Model model,
                                  Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Event event = eventRepository.findById(eventId)
            .orElseThrow(() ->
            new RuntimeException("Event not found"));

        if (event.getCapacity() < tickets) {

            model.addAttribute("error",
            "Not enough tickets available. Only "
            + event.getCapacity() + " left.");

            model.addAttribute("event", event);

            return "booking";
        }

        model.addAttribute("event", event);

        model.addAttribute("tickets", tickets);

        model.addAttribute("totalPrice",
        event.getPrice() * tickets);

        return "payment";
    }

    @PostMapping("/booking/confirm")
    public String confirmBooking(@RequestParam Long eventId,
                                 @RequestParam int tickets,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user =
        userRepository.findByEmail(
        principal.getName());

        try {

            bookingService.createBooking(
            user,
            eventId,
            tickets);

            redirectAttributes.addFlashAttribute(
            "success",
            "Booking confirmed successfully!");

            return "redirect:/my-bookings";

        }

        catch (Exception e) {

            redirectAttributes.addFlashAttribute(
            "error",
            e.getMessage());

            return "redirect:/events/" + eventId;
        }
    }

    @GetMapping("/my-bookings")
    public String viewMyBookings(Principal principal,
                                 Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user =
        userRepository.findByEmail(
        principal.getName());

        List<Booking> bookings =
        bookingService.getBookingsByUser(user);

        model.addAttribute("bookings", bookings);

        return "my-bookings";
    }
}