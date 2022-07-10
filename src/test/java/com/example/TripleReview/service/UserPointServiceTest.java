package com.example.TripleReview.service;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.entity.Review;
import com.example.TripleReview.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPointServiceTest {

    @Autowired UserPointService userPointService;
    @Autowired ReviewService reviewService;

    @DisplayName("유저 포인트 테이블 생성")
    @Test
    @Transactional
    void add() {

        UUID userId = UUID.randomUUID();

        // 실제
        userPointService.add(userId);
        UserPoint userPoint = userPointService.search(userId);

        // 예상
        UserPoint userPoint_test = new UserPoint(userId);

        // 비교
        assertEquals(userPoint.toString(), userPoint_test.toString());
    }

    @DisplayName("유저 포인트 조회")
    @Test
    @Transactional
    void search() {

        UUID userId = UUID.randomUUID();
        userPointService.add(userId);

        // 실제
        UserPoint userPoint = userPointService.search(userId);

        // 예상
        UserPoint userPoint_test = new UserPoint(userId);

        // 비교
        assertEquals(userPoint.toString(), userPoint_test.toString());
    }

    @Test
    @DisplayName("첫 리뷰 등록(첫 리뷰 + 1, 내용 + 1, 사진 + 1)")
    @Transactional
    void firstReviewAll() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        // 실제
        UserPoint userPoint = userPointService.search(dto.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 3;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 등록(내용 + 1, 사진 + 1)")
    @Transactional
    void review() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        EventDto dto2 = new EventDto(
                "REVIEW",
                "ADD",
                UUID.randomUUID(),
                "좋아요",
                attachedPhotoIds,
                UUID.randomUUID(),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);
        Review reviewReal2 = reviewService.add(dto2);

        // 실제
        UserPoint userPoint = userPointService.search(dto2.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 2;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("첫 리뷰 등록 내용x(첫 리뷰 + 1, 사진 + 1)")
    @Transactional
    void firstReviewPhoto() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        // 실제
        UserPoint userPoint = userPointService.search(dto.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 2;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("첫 리뷰 등록 사진, 내용x(첫 리뷰 + 1)")
    @Transactional
    void firstReview() {

        List<String> attachedPhotoIds = new ArrayList<String>();

        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        // 실제
        UserPoint userPoint = userPointService.search(dto.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 1;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 수정 내용 -> 내용x ,(내용 삭제 - 1)")
    @Transactional
    void reviewModContent() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");

        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        EventDto dtoMod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );


        // 실제
        Review reviewMod = reviewService.mod(dtoMod);
        UserPoint userPoint = userPointService.search(reviewMod.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 2;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 수정 내용 -> 내용o ,(내용 추가 + 1)")
    @Transactional
    void reviewModContent2() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");

        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        EventDto dtoMod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );


        // 실제
        Review reviewMod = reviewService.mod(dtoMod);
        UserPoint userPoint = userPointService.search(reviewMod.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 3;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 수정 사진 -> 사진x ,(사진 삭제 - 1)")
    @Transactional
    void reviewModPhoto() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");

        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);
        attachedPhotoIds = new ArrayList<>();
        EventDto dtoMod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "재밌었어요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );


        // 실제
        Review reviewMod = reviewService.mod(dtoMod);
        UserPoint userPoint = userPointService.search(reviewMod.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 2;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 수정 사진 -> 사진o ,(사진 추가 + 1)")
    @Transactional
    void reviewModPhoto2() {

        List<String> attachedPhotoIds = new ArrayList<String>();

        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");

        EventDto dtoMod = new EventDto(
                "REVIEW",
                "MOD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "재밌었어요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );


        // 실제
        Review reviewMod = reviewService.mod(dtoMod);
        UserPoint userPoint = userPointService.search(reviewMod.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 3;

        // 비교
        assertEquals(point, point_test);
    }

    @Test
    @DisplayName("리뷰 삭제 -> (- point)")
    @Transactional
    void reviewDelete() {

        List<String> attachedPhotoIds = new ArrayList<String>();
        attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"); attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
        EventDto dto = new EventDto(
                "REVIEW",
                "ADD",
                UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667778"),
                "좋아요",
                attachedPhotoIds,
                UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f747"),
                UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b10f")
        );

        Review reviewReal = reviewService.add(dto);

        // 실제
        reviewService.delete(dto);
        UserPoint userPoint = userPointService.search(reviewReal.getUserId());
        Integer point = userPoint.getPoint();

        // 예상
        Integer point_test = 0;

        // 비교
        assertEquals(point, point_test);
    }

}