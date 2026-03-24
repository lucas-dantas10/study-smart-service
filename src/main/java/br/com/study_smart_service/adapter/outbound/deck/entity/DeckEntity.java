package br.com.study_smart_service.adapter.outbound.deck.entity;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import org.hibernate.annotations.Formula;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_deck")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CardEntity> cards;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Formula("(SELECT COUNT(c.id) FROM tb_card c WHERE c.deck_id = id)")
    private int totalCards;

    @Formula(
            """
            (
                SELECT COUNT(DISTINCT c.id)
                FROM tb_card c
                LEFT JOIN tb_review r ON r.card_id = c.id
                WHERE c.deck_id = id
                  AND (
                        r.id IS NULL
                        OR r.next_review_at <= CURRENT_DATE
                  )
            )
            """
    )
    private int cardsToReviewToday;

    @Formula("(SELECT MAX(r.created_at) FROM tb_review r JOIN tb_card c ON r.card_id = c.id WHERE c.deck_id = id)")
    private LocalDateTime lastReview;
}
