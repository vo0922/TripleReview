package com.example.TripleReview.dto;

import lombok.*;

import java.util.UUID;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventDto {
    private String type;

    private String action;

    private UUID reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    private UUID userId;

    private UUID placeId;
}
