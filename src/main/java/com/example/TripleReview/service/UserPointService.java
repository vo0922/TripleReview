package com.example.TripleReview.service;

import com.example.TripleReview.entity.UserPoint;
import com.example.TripleReview.repository.ReviewRepository;
import com.example.TripleReview.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPointService {

    private final UserPointRepository userPointRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void add(UUID id) {
        if(!userPointRepository.existsByUserId(id)){
            UserPoint userPoint = new UserPoint(id);
            userPointRepository.save(userPoint);
        }
    }

    public List<UserPoint> searchAll() {
        return userPointRepository.findAll();
    }

    public UserPoint search(UUID id) {return userPointRepository.findById(id).orElse(null);}

    @Transactional
    public UserPoint setPoint(UUID userId) {
        Integer pointAll = reviewRepository.pointGrant(userId);
        if(pointAll == null) pointAll = 0;
        UserPoint userPoint = userPointRepository.findById(userId).orElse(null);
        userPoint.setPoint(pointAll);
        return userPointRepository.save(userPoint);
    }
}
