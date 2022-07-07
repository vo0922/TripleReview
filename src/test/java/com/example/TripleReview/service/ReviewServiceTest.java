package com.example.TripleReview.service;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired ReviewService reviewService;

    @Test
    @Transactional
    void add_success() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
        );

        // 실제
        Review reviewReal = reviewService.add(dto);

        // 예상
        Integer point = 3;
        Review review = new Review(dto.getReviewId(), dto.getContent(), attachedPhotoIds.toString(), reviewReal.getCreateDate(), reviewReal.getLastupdateDate(), dto.getUserId(), dto.getPlaceId(), point);

        // 비교
        assertEquals(review.toString(), reviewReal.toString());
    }

    @Test
    void mod() {
    }

    @Test
    void delete() {
    }

}