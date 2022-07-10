package com.example.TripleReview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString
public class UserPoint {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    @ColumnDefault("0")
    private Integer point;

    public void setPoint(Integer point) {
        this.point = point;
    }

    public UserPoint(UUID id) {
        this.userId = id;
    }

}
