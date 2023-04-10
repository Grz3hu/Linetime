package com.linetime.backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="timelines", nullable=false)
    private User owner;

    @OneToMany(mappedBy="timeline")
    private Set<Event> events;

    private String timelineTitle;

    public Timeline() {

    }

    public Timeline(int id, User owner, String timelineTitle, Set<Event> events) {
        this.id = id;
        this.owner = owner;
        this.timelineTitle = timelineTitle;
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTimelineTitle() {
        return timelineTitle;
    }

    public void setTimelineTitle(String timelineTitle) {
        this.timelineTitle = timelineTitle;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
