package com.linetime.backend.service;

import com.linetime.backend.exception.EmailTakenException;
import com.linetime.backend.exception.EventNotFoundException;
import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.exception.UsernameTakenException;
import com.linetime.backend.jwt.JwtTokenUtil;
import com.linetime.backend.model.Event;
import com.linetime.backend.model.Role;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.EventDto;
import com.linetime.backend.payload.LoginDto;
import com.linetime.backend.payload.SignUpDto;
import com.linetime.backend.repository.EventRepository;
import com.linetime.backend.repository.RoleRepository;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private UserRepository userRepository;

    public Event getEventById(Integer id) throws EventNotFoundException{
        return eventRepository.findById(id).
                orElseThrow(() -> new EventNotFoundException(id));
    }

    public Event createEvent(EventDto payload, String username) throws TimelineNotFoundException, AccessDeniedException {
        Integer timelineId = payload.getTimelineId();
        if(!timelineRepository.existsById(timelineId)){
            throw new TimelineNotFoundException(timelineId);
        }
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to create event");
        }

        Event newEvent = new Event();
        newEvent.setTitle(payload.getTitle());
        newEvent.setCardSubtitle(payload.getCardSubtitle());
        newEvent.setUrl(payload.getUrl());
        newEvent.setCardTitle(payload.getCardTitle());
        newEvent.setCardDetailedText(payload.getCardDetailedText());
        newEvent.setTimeline(timelineRepository.findById(payload.getTimelineId()).get());
        eventRepository.save(newEvent);
        return newEvent;
    }

    public Event updateEvent(EventDto payload, Integer id, String username) throws TimelineNotFoundException, EventNotFoundException, AccessDeniedException {
        if(!eventRepository.existsById(id)){
            throw new EventNotFoundException(id);
        }
        Integer timelineId = payload.getTimelineId();
        if(!timelineRepository.existsById(timelineId)){
            throw new TimelineNotFoundException(timelineId);
        }
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to update event");
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
                }).orElseThrow(() -> new EventNotFoundException(id));
        return newEvent;
    }

    public void deleteEvent(Integer id, String username) throws EventNotFoundException, AccessDeniedException {
        if(!eventRepository.existsById(id)){
            throw new EventNotFoundException(id);
        }

        Event event = eventRepository.findById(id).get();
        Integer timelineId = event.getTimeline().getId();
        User owner = timelineRepository.findById(timelineId).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to delete event");
        }
        eventRepository.deleteById(id);
    }

}
