package com.example.TripleReview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(indexes = @Index(name = "pointSearchIdx", columnList = "userId"))
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;

    private String content;

    private String attachedPhtoIds;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime LastupdateDate;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID placeId;

    @ColumnDefault("0")
    private Integer point;

    public Review(UUID reviewId, String content, List<String> attachedPhotoIds, UUID userId, UUID placeId, Integer point) {
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhtoIds = attachedPhotoIds.toString();
        this.userId = userId;
        this.placeId = placeId;
        this.point = point;
    }

    public void patch(Review target) {
        this.content = target.getContent();
        this.attachedPhtoIds = target.getAttachedPhtoIds().toString();
        this.point = target.getPoint();
    }
}
