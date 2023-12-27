package com.linetime.backend.controller;

import com.linetime.backend.exception.EventNotFoundException;
import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.model.Event;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.EventDto;
import com.linetime.backend.repository.EventRepository;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import com.linetime.backend.service.AuthService;
import com.linetime.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/event/")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getEventById(@PathVariable Integer id){
        try {
            Event event = eventService.getEventById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        catch(EventNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    ResponseEntity<?> createEvent(@RequestBody EventDto payload){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Event newEvent = eventService.createEvent(payload, username);
            return new ResponseEntity<>(newEvent, HttpStatus.OK);
        }
        catch( TimelineNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> createEvent(@RequestBody EventDto payload, @PathVariable Integer id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Event newEvent = eventService.updateEvent(payload, id, username);
            return new ResponseEntity<>(newEvent, HttpStatus.OK);
        }
        catch( TimelineNotFoundException|EventNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEvent(@PathVariable Integer id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            eventService.deleteEvent(id, username);
            return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
        }
        catch( EventNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}