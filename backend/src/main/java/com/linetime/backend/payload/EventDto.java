package com.linetime.backend.payload;

import lombok.Data;

@Data
public class EventDto {
    private Integer timelineId;
    private String title;
    private String cardTitle;
    private String url;
    private String cardSubtitle;
    private String cardDetailedText;
}
