package com.example.TripleReview.controller;

import com.example.TripleReview.entity.UserPoint;
import com.example.TripleReview.service.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserPointController {
    private final UserPointService userPointService;

    @GetMapping("/point/user")
    public List<UserPoint> all() {
        return userPointService.searchAll();
    }

    @GetMapping("/point/{id}")
    public UserPoint find(@PathVariable UUID id) { return userPointService.search(id); }
}
