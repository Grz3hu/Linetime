package com.linetime.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    @JsonBackReference
    private User owner;

    @OneToMany(mappedBy="timeline", cascade = CascadeType.ALL)
    @JsonManagedReference("timeline-events")
    private Collection<Event> events; //WTF when there is Set instead of collection returned data is always null

    private String title;
    private String mode;

    public Timeline() {

    }

    public Timeline(int id, User owner, String timelineTitle, HashSet<Event> events) {
        this.id = id;
        this.owner = owner;
        this.title = timelineTitle;
        this.events = events;
    }
    public int getOwnerId(){
        return owner.getId();
    }

    public String getOwnerName(){
        return owner.getName();
    }
}
