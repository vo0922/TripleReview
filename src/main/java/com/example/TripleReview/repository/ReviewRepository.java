package com.example.TripleReview.repository;

import com.example.TripleReview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    boolean existsByReviewId(UUID id);

    boolean existsByPlaceId(UUID placeId);

    Review findByUserIdAndPlaceId(UUID userId, UUID placeId);

    List<Review> findByPlaceId(UUID placeId);
}
