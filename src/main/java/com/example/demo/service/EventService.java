package com.example.demo.service;
import com.example.demo.model.Event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.EventRepository;

@Service
public class EventService {
    private EventRepository repo;
    
    public EventService(EventRepository repo)
    {
        this.repo = repo;
    }

}
