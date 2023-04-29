package com.linetime.backend.payload;

import com.linetime.backend.model.Timeline;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class EventDto {
    private Integer timelineId;
    private String date;
    private String cardTitle;
    private String url;
    private String cardSubtitle;
    private String cardDetailedText;
}
