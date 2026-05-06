package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.EventService;



@Controller

public class EventController {
    private EventService eventService;

    public EventController(EventService eventService)
    {
        this.eventService = eventService;
    }

     @GetMapping("/events")
    public String viewEvents(Model model)
    {
        model.addAttribute("events", eventService.getAllEvents());

        return "events";
    }

    @GetMapping("events/{id}")
    public String viewEventDetails(@PathVariable Long id, Model model) {
        model.addAttribute("event",eventService.getEventByID(id));
        return "event-details";
    }
    

    @GetMapping("/events/search")
    public String searchEvents(@RequestParam String keyword,
                           Model model)
    {
        model.addAttribute("events", eventService.searchEvents(keyword));

        return "events";
    }
  

    
}
