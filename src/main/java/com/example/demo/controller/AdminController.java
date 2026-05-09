package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.Event;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/dashboard")
    public String displayDashboard(Model model) {
        List<Event> events = adminService.getAllEvents();
        model.addAttribute("events", events);
        return "admin-dashboard";
    }

    @GetMapping("/add-event")
    public String showAddForm(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        return "add-event";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Event event = adminService.getEventById(id);

        if(event != null) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(event.getId());
            eventDTO.setTitle(event.getTitle());
            eventDTO.setDescription(event.getDescription());
            eventDTO.setLocation(event.getLocation());
            eventDTO.setPrice(event.getPrice());
            eventDTO.setCapacity(event.getCapacity());
            eventDTO.setImageUrl(event.getImageUrl());

            model.addAttribute("eventDTO", eventDTO);
            return "edit-event";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/save")
    public String saveEvent(
            @Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
            BindingResult result) {
        
        if(result.hasErrors()) {
            if(eventDTO.getId() != null) {
                return "edit-event";
            }
            return "add-event";
        }

        Event event;
        
        if (eventDTO.getId() != null) {
            event = adminService.getEventById(eventDTO.getId());
            if (event == null) {
                event = new Event();
            }
        } else {
            event = new Event();
        }

        event.setTitle(eventDTO.getTitle());
        event.setLocation(eventDTO.getLocation());
        event.setPrice(eventDTO.getPrice());
        event.setCapacity(eventDTO.getCapacity());
        
        if (eventDTO.getDescription() != null) {
            event.setDescription(eventDTO.getDescription());
        }
        if (eventDTO.getImageUrl() != null) {
            event.setImageUrl(eventDTO.getImageUrl());
        }

        adminService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id) {
        adminService.deleteEventById(id);
        return "redirect:/admin/dashboard";
    }

    

    @GetMapping("/add-admin")
    public String showAddAdminForm(Model model) {
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "add-admin";
    }

    @PostMapping("/save-admin")
    public String saveAdmin(
            @ModelAttribute("userDTO") UserRegistrationDTO userDTO,
            RedirectAttributes redirectAttributes) {
        
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("error", "Email is already in use.");
            return "redirect:/admin/add-admin";
        }

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        
        User newAdmin = new User(
            userDTO.getName(), 
            userDTO.getEmail(), 
            hashedPassword, 
            "ROLE_ADMIN" 
        );
        
        userRepository.save(newAdmin);
        
        redirectAttributes.addFlashAttribute("success", "New Administrator added successfully.");
        return "redirect:/admin/dashboard";
    }
}