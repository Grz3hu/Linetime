package com.linetime.backend.controller;

import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.model.Timeline;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.updateTimelineDto;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/timeline/")
public class TimelineController {

    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    ResponseEntity<?> getTimelineById(@PathVariable Integer id){
        Timeline timeline = timelineRepository.findById(id). //TODO add try catch
                orElseThrow(() -> new TimelineNotFoundException(id));
        return new ResponseEntity<>(timeline, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllTimelines() {
        return new ResponseEntity<>(timelineRepository.findAll(),HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<?> createTimeline(@RequestBody Timeline newTimeline) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Timeline timeline = new Timeline();
        timeline.setTitle(newTimeline.getTitle());
        timeline.setEvents(newTimeline.getEvents());
        timeline.setOwner(userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get());
        timelineRepository.save(timeline);

        return new ResponseEntity<>(timeline, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTimeline(@RequestBody updateTimelineDto payload, @PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(!timelineRepository.existsById(id)){
            return new ResponseEntity<>("Timeline does not exist", HttpStatus.NOT_FOUND);
        }
        User owner = timelineRepository.findById(id).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get();
        if(!owner.equals(requestUser)) { //TODO add admin check
            return new ResponseEntity<>("User has no permission to update timeline", HttpStatus.FORBIDDEN);
        }

        Timeline result = timelineRepository.findById(id)
                .map(timeline -> {
                    timeline.setTitle(payload.getTimelineTitle());
                    timeline.setEvents(payload.getEvents());
                    return timelineRepository.save(timeline);
                }).orElseThrow(() -> new TimelineNotFoundException(id)); //TODO Investigate if exception is needed

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTimeline(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(!timelineRepository.existsById(id)){
            return new ResponseEntity<>("Timeline does not exist", HttpStatus.NOT_FOUND);
        }

        Timeline deletedTimeline = timelineRepository.findById(id).get();
        User owner = deletedTimeline.getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(currentPrincipalName,currentPrincipalName).get();
        if(!owner.equals(requestUser)) { //TODO add admin check
            return new ResponseEntity<>("User has no permission to delete timeline", HttpStatus.FORBIDDEN);
        }
        timelineRepository.deleteById(id);
        return new ResponseEntity<>("Timeline deleted successfully", HttpStatus.OK);
    }
}
