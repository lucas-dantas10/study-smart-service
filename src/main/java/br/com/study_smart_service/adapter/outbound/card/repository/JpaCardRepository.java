package br.com.study_smart_service.adapter.outbound.card.repository;

import br.com.study_smart_service.adapter.outbound.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaCardRepository extends JpaRepository<CardEntity, UUID> {

    @Query("SELECT card FROM CardEntity card " +
            "JOIN card.deck deck " +
            "WHERE deck.id = :deckId AND deck.user.id = :userId")
    List<CardEntity> findAllByDeckIdAndUserId(UUID deckId, UUID userId);

    @Query("""
        SELECT c
        FROM CardEntity c
        JOIN c.deck d
        LEFT JOIN ReviewEntity r ON r.card = c AND r.user.id = :userId
        WHERE d.id = :deckId AND (
                r.id IS NULL
                OR
                (
                    r.createdAt = (
                        SELECT MAX(r2.createdAt)
                        FROM ReviewEntity r2
                        WHERE r2.card = c AND r2.user.id = :userId
                    )
                    AND r.nextReviewAt <= CURRENT_DATE
                )
        )
        ORDER BY
            CASE
                WHEN r.nextReviewAt IS NULL THEN 0
                ELSE 1
            END,
            r.nextReviewAt ASC
        """)
    List<CardEntity> findAllStudyCardsByDeckIdAndUserId(UUID deckId, UUID userId);

    @Query("SELECT card FROM CardEntity card " +
            "JOIN card.deck deck " +
            "WHERE card.id = :cardId AND deck.user.id = :userId")
    CardEntity findByIdAndUserId(UUID cardId, UUID userId);
}
