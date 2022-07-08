package com.example.TripleReview.service;

import com.example.TripleReview.entity.UserPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPointServiceTest {

    @Autowired UserPointService userPointService;

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
}