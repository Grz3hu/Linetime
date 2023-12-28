package com.linetime.backend.service;

import com.linetime.backend.exception.EventNotFoundException;
import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.exception.UserNotFoundException;
import com.linetime.backend.model.Event;
import com.linetime.backend.model.Timeline;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.EventDto;
import com.linetime.backend.payload.updateTimelineDto;
import com.linetime.backend.repository.EventRepository;
import com.linetime.backend.repository.TimelineRepository;
import com.linetime.backend.repository.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private UserRepository userRepository;

    public Timeline getTimelineById(Integer id){
        return timelineRepository.findById(id).
                orElseThrow(() -> new TimelineNotFoundException(id));
    }

    public List<Timeline> getAllTimelines() {
        return timelineRepository.findAll();
    }

    public Timeline createTimeline(Timeline newTimeline, String username) throws UserNotFoundException {
        Timeline timeline = new Timeline();
        timeline.setTitle(newTimeline.getTitle());
        timeline.setEvents(newTimeline.getEvents());
        timeline.setMode(newTimeline.getMode());
        User owner = userRepository.findByUsernameOrEmail(username,username).orElseThrow( () -> new UserNotFoundException(username));
        timeline.setOwner(owner);
        timelineRepository.save(timeline);

        return timeline;
    }

    public Timeline updateTimeline(updateTimelineDto payload, Integer id, String username) throws AccessDeniedException, TimelineNotFoundException {
        User owner = timelineRepository.findById(id).get().getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to update timeline");
        }
        Timeline result = timelineRepository.findById(id)
                .map(timeline -> {
                    timeline.setTitle(payload.getTimelineTitle());
                    timeline.setMode(payload.getMode());
                    timeline.setEvents(payload.getEvents());
                    return timelineRepository.save(timeline);
                }).orElseThrow(() -> new TimelineNotFoundException(id));

        return result;
    }

    public void deleteTimeline(Integer id, String username) throws AccessDeniedException, TimelineNotFoundException{
        if(!timelineRepository.existsById(id)){
            throw new TimelineNotFoundException(id);
        }
        Timeline deletedTimeline = timelineRepository.findById(id).get();
        User owner = deletedTimeline.getOwner();
        User requestUser = userRepository.findByUsernameOrEmail(username, username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to delete timeline");
        }
        timelineRepository.deleteById(id);
    }
}
