package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Event;



@Service
public class HomeService {
 
    private EventService es;

    public HomeService(EventService es)
    {
        this.es = es;
    }

    public int countEvents()
    {
        return es.getAllEvents().size();
    }

     public List<Event> getFeaturedEvents()
    {
        return es.getAllEvents();
    }
}
