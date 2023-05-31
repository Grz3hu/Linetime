package com.linetime.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name="timeline_id", nullable=false)
    @JsonBackReference("timeline-events")
    private Timeline timeline;

    private String date;
    private String cardTitle;
    private String url;
    private String cardSubtitle;
    private String cardDetailedText;

    public Event() {
    }

    public Event(int id, Timeline timeline, String date, String cardTitle, String url, String cardSubtitle, String cardDetailedText) {
        this.id = id;
        this.timeline = timeline;
        this.date = date;
        this.cardTitle = cardTitle;
        this.url = url;
        this.cardSubtitle = cardSubtitle;
        this.cardDetailedText = cardDetailedText;
    }

    public int getTimelineId(){
        return timeline.getId();
    }
}
