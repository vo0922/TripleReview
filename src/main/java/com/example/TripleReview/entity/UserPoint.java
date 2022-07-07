package com.example.TripleReview.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
public class UserPoint {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    @ColumnDefault("0")
    private Integer point;

    public UserPoint(UUID id) {
        this.userId = id;
    }
}
