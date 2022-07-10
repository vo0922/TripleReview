package com.example.TripleReview.service;

import com.example.TripleReview.dto.EventDto;
import com.example.TripleReview.entity.Review;
import com.example.TripleReview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserPointService userPointService;
    private final HistoryService historyService;

    @Transactional
    public Review add(EventDto eventDto) {
        List<String> history = new ArrayList<>();
        Integer point = 0;

        userPointService.add(eventDto.getUserId());

        if(!eventDto.getContent().isEmpty()) {point++; history.add("내용 추가");};
        if(!eventDto.getAttachedPhotoIds().isEmpty()) {point++; history.add("사진 추가");};
        if(!reviewRepository.existsByPlaceId(eventDto.getPlaceId())) {point++; history.add("첫 리뷰");};

        Review review = new Review(eventDto.getReviewId(), eventDto.getContent(), eventDto.getAttachedPhotoIds(), eventDto.getUserId(), eventDto.getPlaceId(), point);

        if(reviewRepository.existsByReviewId(eventDto.getReviewId())){
            throw new RuntimeException("중복된 리뷰 id 입니다.");
        } else if(reviewRepository.findByUserIdAndPlaceId(eventDto.getUserId(), eventDto.getPlaceId()) != null){
            throw new RuntimeException("이미 리뷰를 작성하셨습니다.");
        } else{
            if(!history.isEmpty()) historyService.addLog(review.getUserId(), point, history);
            Review reviewSave = reviewRepository.save(review);
            userPointService.setPoint(reviewSave.getUserId());
            return reviewSave;
        }

    }

    @Transactional
    public Review mod(EventDto eventDto) {
        Review review = reviewRepository.findById(eventDto.getReviewId()).orElse(null);

        if(review == null){
            throw new RuntimeException("수정할 리뷰가 없습니다.");
        }

        if(!review.getUserId().equals(eventDto.getUserId())) {
            throw new RuntimeException("수정할 권한이 없습니다.");
        }

        List<String> history = new ArrayList<>();
        Integer count = review.getPoint();
        Integer point = 0;

        if(review.getContent().isEmpty() && !eventDto.getContent().isEmpty()) {
            point++; history.add("내용 추가");
        }else if(!review.getContent().isEmpty() && eventDto.getContent().isEmpty()){
            point--; history.add("내용 삭제");
        }
        if(review.getAttachedPhtoIds().equals("[]") && !eventDto.getAttachedPhotoIds().isEmpty()) {
            point++; history.add("사진 추가");
        }else if(!review.getAttachedPhtoIds().equals("[]") && eventDto.getAttachedPhotoIds().isEmpty()) {
            point--; history.add("사진 삭제");
        }

        count += point;
        Review target = new Review(eventDto.getReviewId(), eventDto.getContent(), eventDto.getAttachedPhotoIds(), eventDto.getUserId(), eventDto.getPlaceId(), count);

        if(review.getUserId().equals(target.getUserId()) && review.getPlaceId().equals(target.getPlaceId())){
            review.patch(target);
            if(!history.isEmpty()) historyService.addLog(review.getUserId(), point, history);
            Review reviewSave = reviewRepository.save(review);
            userPointService.setPoint(reviewSave.getUserId());
            return reviewSave;
        }else {
            throw new RuntimeException("잘못된 유저 또는 장소입니다.");
        }

    }

    @Transactional
    public Review delete(EventDto eventDto) {
        Review review = reviewRepository.findById(eventDto.getReviewId()).orElse(null);
        List<String> history = new ArrayList<>();
        history.add("리뷰 삭제");

        if(review == null){
            throw new RuntimeException("삭제할 리뷰가 없습니다.");
        }else {
            historyService.addLog(review.getUserId(), -review.getPoint(), history);
            reviewRepository.delete(review);
            userPointService.setPoint(review.getUserId());
            return review;
        }
    }

}
