package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Booking;
import com.example.demo.model.Event;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.EventRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public Booking createBooking(User user, Long eventId, int tickets) {
        
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getCapacity() < tickets) {
            throw new RuntimeException("Booking failed: Only " + event.getCapacity() + " tickets remaining.");
        }

        double totalPrice = event.getPrice() * tickets;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setNumberOfTickets(tickets);
        booking.setTotalPrice(totalPrice);

        event.setCapacity(event.getCapacity() - tickets);
        eventRepository.save(event); 

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }
}