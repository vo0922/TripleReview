package com.example.TripleReview.service;

import com.example.TripleReview.entity.UserPoint;
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

    public UserPoint search(String id) {return userPointRepository.findById(UUID.fromString(id)).orElse(null);}
}
