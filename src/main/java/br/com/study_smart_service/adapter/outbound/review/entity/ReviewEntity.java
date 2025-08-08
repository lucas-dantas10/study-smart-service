package br.com.study_smart_service.adapter.outbound.review.entity;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = CardEntity.class)
    @JoinColumn(name = "card_id")
    private CardEntity card;

    @Column(name = "next_review_at", nullable = false)
    private LocalDate nextReviewAt;

    @Column(nullable = false)
    private Integer interval;

    @Column(nullable = false)
    private Integer repetition;

    @Column(nullable = false)
    private Double easiness;

    @Column(name = "last_quality", nullable = false)
    private Integer lastQuality;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
