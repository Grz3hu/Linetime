package com.linetime.backend.model;

import jakarta.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="timeline_id", nullable=false)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCardSubtitle() {
        return cardSubtitle;
    }

    public void setCardSubtitle(String cardSubtitle) {
        this.cardSubtitle = cardSubtitle;
    }

    public String getCardDetailedText() {
        return cardDetailedText;
    }

    public void setCardDetailedText(String cardDetailedText) {
        this.cardDetailedText = cardDetailedText;
    }
}
