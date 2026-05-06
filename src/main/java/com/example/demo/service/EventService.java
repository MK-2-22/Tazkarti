package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

@Service
public class EventService {
    private EventRepository repo;
    
    public EventService(EventRepository repo)
    {
        this.repo = repo;
    }

    public List<Event> getAllEvents(){

    List<Event> events = repo.findAll();

    if(events.isEmpty())
    {
        System.out.println("No Events Found");
    }

    return events;
}

public Optional<Event> getEventByID(Long id)
{
    Optional<Event> optEvent = repo.findById(id);

      if(optEvent.isEmpty())
    {
        System.out.println("No events found with this ID");

    }

    return optEvent;
}

public List<Event> searchEvents(String keyword)
{
    return repo.findByTitleContainingIgnoreCase(keyword);
}

}
