package com.linetime.backend.controller;

import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.exception.UserNotFoundException;
import com.linetime.backend.model.Timeline;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.updateTimelineDto;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import com.linetime.backend.service.AuthService;
import com.linetime.backend.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/timeline/")
public class TimelineController {
    private final TimelineService timelineService;

    @Autowired
    public TimelineController(final TimelineService timelineService) {
        this.timelineService = timelineService;
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getTimelineById(@PathVariable Integer id){
        try{
            Timeline timeline = timelineService.getTimelineById(id);
            return new ResponseEntity<>(timeline, HttpStatus.OK);
        }
        catch ( TimelineNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllTimelines() {
        return new ResponseEntity<>(timelineService.getAllTimelines(),HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<?> createTimeline(@RequestBody Timeline newTimeline) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Timeline timeline = timelineService.createTimeline(newTimeline, username);
        return new ResponseEntity<>(timeline, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTimeline(@RequestBody updateTimelineDto payload, @PathVariable Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Timeline timeline = timelineService.updateTimeline(payload, id, username);
            return new ResponseEntity<>(timeline, HttpStatus.OK);
        }
        catch( TimelineNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTimeline(@PathVariable Integer id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            timelineService.deleteTimeline(id, username);
            return new ResponseEntity<>("Timeline deleted successfully", HttpStatus.OK);
        }
        catch( TimelineNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
