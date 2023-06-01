package com.linetime.backend.payload;

import com.linetime.backend.model.Event;
import lombok.Data;

import java.util.Set;

@Data
public class updateTimelineDto {
    Integer ownerId;
    private Set<Event> events;

    private String timelineTitle;
    private String mode;
}
