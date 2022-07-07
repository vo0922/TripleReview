package com.example.TripleReview.controller;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/events")
    public ResponseEntity<String> event(@RequestBody EventDto eventDto) {
        switch(eventDto.getAction()) {
            case "ADD":
                reviewService.add(eventDto);
                return new ResponseEntity<String>("등록 성공", new HttpHeaders(), HttpStatus.CREATED);
            case "MOD":
                reviewService.mod(eventDto);
                return new ResponseEntity<String>("수정 성공", new HttpHeaders(), HttpStatus.CREATED);
            case "DELETE":
                reviewService.delete(eventDto);
                return new ResponseEntity<String>("삭제 성공", new HttpHeaders(), HttpStatus.CREATED);
            default:
                return new ResponseEntity<String>("잘못된 Action입니다.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

}
