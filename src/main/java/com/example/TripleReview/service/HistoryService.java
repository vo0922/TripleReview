package com.example.TripleReview.service;

import com.example.TripleReview.entity.History;
import com.example.TripleReview.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Transactional
    public void addLog(UUID userId, Integer point, List<String> history) {
        History target = new History(userId, point, history);
        log.info(userId.toString() + "님이 리뷰작성으로" + point + "p를 획득하였습니다.");
        historyRepository.save(target);
    }
}
