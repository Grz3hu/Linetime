package com.linetime.backend.controller;

import com.linetime.backend.exception.EventNotFoundException;
import com.linetime.backend.model.Event;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.EventDto;
import com.linetime.backend.repository.EventRepository;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    ResponseEntity<?> getEventById(@PathVariable Integer id){
        Event event = eventRepository.findById(id). //TODO add try catch
                orElseThrow(() -> new EventNotFoundException(id));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<?> createEvent(@RequestBody EventDto payload){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Integer timelineId = payload.getTimelineId();
        if(!timelineRepository.existsById(timelineId)){
            return new ResponseEntity<>("Timeline does not exist", HttpStatus.NOT_FOUND);
        }
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get();
        if(!owner.equals(requestUser)) { //TODO add admin check
            return new ResponseEntity<>("User has no permission to create event", HttpStatus.FORBIDDEN);
        }

        Event newEvent = new Event();
        newEvent.setTitle(payload.getTitle());
        newEvent.setCardSubtitle(payload.getCardSubtitle());
        newEvent.setUrl(payload.getUrl());
        newEvent.setCardTitle(payload.getCardTitle());
        newEvent.setCardDetailedText(payload.getCardDetailedText());
        newEvent.setTimeline(timelineRepository.findById(payload.getTimelineId()).get());

        eventRepository.save(newEvent);

        return new ResponseEntity<>(newEvent, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> createEvent(@RequestBody EventDto payload, @PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(!eventRepository.existsById(id)){
            return new ResponseEntity<>("Event does not exist", HttpStatus.NOT_FOUND);
        }

        Integer timelineId = payload.getTimelineId();
        if(!timelineRepository.existsById(timelineId)){
            return new ResponseEntity<>("Timeline does not exist", HttpStatus.NOT_FOUND);
        }
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get();
        if(!owner.equals(requestUser)) { //TODO add admin check
            return new ResponseEntity<>("User has no permission to update event", HttpStatus.FORBIDDEN);
        }

        Event newEvent = eventRepository.findById(id)
                        .map( event -> {
                                    event.setTitle(payload.getTitle());
                                    event.setCardSubtitle(payload.getCardSubtitle());
                                    event.setUrl(payload.getUrl());
                                    event.setCardTitle(payload.getCardTitle());
                                    event.setCardDetailedText(payload.getCardDetailedText());
                                    event.setTimeline(timelineRepository.findById(payload.getTimelineId()).get());
                                    return eventRepository.save(event);
                                }).orElseThrow(() -> new EventNotFoundException(id)); //TODO Investigate if needed

        return new ResponseEntity<>(newEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEvent(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(!eventRepository.existsById(id)){
            return new ResponseEntity<>("Event does not exist", HttpStatus.NOT_FOUND);
        }

        Event event = eventRepository.findById(id).get();
        Integer timelineId = event.getTimeline().getId();
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get();
        if(!owner.equals(requestUser)) { //TODO add admin check
            return new ResponseEntity<>("User has no permission to delete event", HttpStatus.FORBIDDEN);
        }

        eventRepository.deleteById(id);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }

}