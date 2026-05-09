package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

@Service
public class AdminService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public void updateEvent(Event event) {
        // In Spring Data JPA, .save() handles both creating and updating
        eventRepository.save(event); 
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}