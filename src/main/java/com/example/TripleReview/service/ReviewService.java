package com.example.TripleReview.service;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.entity.Review;
import com.example.TripleReview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserPointService userPointService;
    @Transactional
    public ResponseEntity<String> add(EventDto eventDto) {
        Integer point = 0;

        userPointService.add(eventDto.getUserId());

        if(!eventDto.getContent().isEmpty()) point++;
        if(!eventDto.getAttachedPhotoIds().isEmpty()) point++;
        if(!reviewRepository.existsByPlaceId(eventDto.getPlaceId())) point++;

        Review review = new Review(eventDto.getReviewId(), eventDto.getContent(), eventDto.getAttachedPhotoIds(), eventDto.getUserId(), eventDto.getPlaceId(), point);

        if(reviewRepository.existsByReviewId(eventDto.getReviewId())){
            return new ResponseEntity<String>("등록 실패 중복된 리뷰id 입니다.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else if(reviewRepository.findByUserIdAndPlaceId(eventDto.getUserId(), eventDto.getPlaceId()) != null){
            return new ResponseEntity<String>("등록 실패 이미 리뷰를 작성했습니다.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else{
            reviewRepository.save(review);
            return new ResponseEntity<String>("등록 성공", new HttpHeaders(), HttpStatus.CREATED);
        }

    }


    @Transactional
    public Review mod(EventDto eventDto) {
        Review review = reviewRepository.findById(eventDto.getReviewId()).orElse(null);

        if(review == null){
            throw new RuntimeException("수정할 리뷰가 없습니다.");
        }

        Integer point = 0;

        if(!eventDto.getContent().isEmpty()) point++;
        if(!eventDto.getAttachedPhotoIds().isEmpty()) point++;
        if(reviewRepository.findByPlaceId(eventDto.getPlaceId()).size()<=1) point++;

        Review target = new Review(eventDto.getReviewId(), eventDto.getContent(), eventDto.getAttachedPhotoIds(), eventDto.getUserId(), eventDto.getPlaceId(), point);

        if(review.getUserId().equals(target.getUserId()) && review.getPlaceId().equals(target.getPlaceId())){
            review.patch(target);
            return reviewRepository.save(review);
        }else {
            throw new RuntimeException("잘못된 유저 또는 장소입니다.");
        }

    }

    @Transactional
    public void delete(EventDto eventDto) {
        Review review = reviewRepository.findById(eventDto.getReviewId()).orElse(null);
        if(review == null){
            throw new RuntimeException("삭제할 리뷰가 없습니다.");
        }else {
            reviewRepository.delete(review);
        }
    }

}
