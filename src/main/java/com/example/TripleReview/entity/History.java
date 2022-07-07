package com.example.TripleReview.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    private Integer point;

    private String text;

    @CreatedDate
    private LocalDateTime createDate;

    public History(UUID userId, Integer point, List<String> history) {
        this.userId = userId;
        this.point = point;
        this.text = history.toString();
    }
}
