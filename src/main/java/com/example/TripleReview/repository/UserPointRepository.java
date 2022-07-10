package com.example.TripleReview.repository;

import com.example.TripleReview.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserPointRepository extends JpaRepository<UserPoint, UUID> {
    boolean existsByUserId(UUID id);

}
