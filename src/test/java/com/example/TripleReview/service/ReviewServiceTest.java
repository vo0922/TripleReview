package com.example.TripleReview.service;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.entity.Review;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("리뷰 작성")
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

    @DisplayName("리뷰 작성 실패(사용자가 한 장소에 2개이상의 리뷰를 쓸 경우)")
    @Test
    @Transactional
    void add_fail1() {
        // 사용자가 한 장소에 2개이상의 리뷰를 쓸 경우
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

        EventDto dto_test = new EventDto(
                "REVIEW",
                "ADD",
                UUID.randomUUID(),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
        );

        // 실제
        Review reviewReal = reviewService.add(dto);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.add(dto_test));
        // 예상
        String message = "이미 리뷰를 작성하셨습니다.";
        // 비교
        assertEquals(exception.getMessage(), message);
    }

    @DisplayName("리뷰 작성 실패(reviewId가 같을경우)")
    @Test
    @Transactional
    void add_fail2() {
        // reviewId가 같을경우
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

        EventDto dto_test = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
                "좋아요",
                attachedPhotoIds,
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        // 실제
        Review reviewReal = reviewService.add(dto);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.add(dto_test));
        // 예상
        String message = "중복된 리뷰 id 입니다.";
        // 비교
        assertEquals(exception.getMessage(), message);
    }


    @DisplayName("리뷰 수정")
    @Test
    @Transactional
    void mod_success() {

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

        Review reviewReal = reviewService.add(dto);
        // 실제
        EventDto dto_mod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
                "너무 재밌었어요.",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
        );
        Review reviewMod = reviewService.mod(dto_mod);
        // 예상
        Integer point = 3;
        Review review = new Review(dto_mod.getReviewId(), dto_mod.getContent(), attachedPhotoIds.toString(), reviewReal.getCreateDate(), reviewMod.getLastupdateDate(), dto_mod.getUserId(), dto_mod.getPlaceId(), point);

        // 비교
        assertEquals(reviewMod.toString(), review.toString());
    }

    @DisplayName("리뷰 수정 실패(수정할 리뷰가 없을 경우)")
    @Test
    @Transactional
    void mod_fail1() {
        // 수정할 리뷰가 없을 경우
        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");

        // 실제
        EventDto dto_mod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
                "너무 재밌었어요.",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
        );
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.mod(dto_mod));
        // 예상
        String message = "수정할 리뷰가 없습니다.";
        // 비교
        assertEquals(exception.getMessage(), message);
    }

    @DisplayName("리뷰 수정 실패(작성된 userId와 수정된 리뷰 userId가 다른경우)")
    @Test
    @Transactional
    void mod_fail2() {
        // 작성된 리뷰 userId와 수정된 리뷰 userId가 다를경우
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

        Review reviewReal = reviewService.add(dto);
        // 실제
        EventDto dto_mod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
                "너무 재밌었어요.",
                attachedPhotoIds,
                UUID.randomUUID(),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
        );
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.mod(dto_mod));
        // 예상
        String message = "수정할 권한이 없습니다.";
        // 비교
        assertEquals(exception.getMessage(), message);
    }

    @DisplayName("리뷰 삭제")
    @Test
    @Transactional
    void delete_success() {

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
        Review reviewReal = reviewService.add(dto);
        // 실제
        Review reviewDelete = reviewService.delete(dto);

        // 예상
        Integer point = 3;
        Review review = new Review(dto.getReviewId(), dto.getContent(), attachedPhotoIds.toString(), reviewReal.getCreateDate(), reviewReal.getLastupdateDate(), dto.getUserId(), dto.getPlaceId(), point);

        // 비교
        assertEquals(reviewDelete.toString(), review.toString());
    }

    @DisplayName("리뷰 삭제 실패(삭제할 리뷰가 없을 경우)")
    @Test
    @Transactional
    void delete_fail() {
        // 삭제할 리뷰가 없을 경우
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
        RuntimeException exception = assertThrows(RuntimeException.class, () -> reviewService.delete(dto));
        // 예상
        String message = "삭제할 리뷰가 없습니다.";
        // 비교
        assertEquals(exception.getMessage(), message);
    }

}