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
        WHERE d.id = :deckId
          AND EXISTS (
              SELECT 1
              FROM ReviewEntity r
              WHERE r.card = c
                AND r.user.id = :userId
                AND r.createdAt = (
                    SELECT MAX(r2.createdAt)
                    FROM ReviewEntity r2
                    WHERE r2.card = c AND r2.user.id = :userId
                )
                AND r.nextReviewAt <= CURRENT_DATE
          )
        ORDER BY (
            SELECT r3.nextReviewAt
            FROM ReviewEntity r3
            WHERE r3.card = c AND r3.user.id = :userId
            ORDER BY r3.createdAt DESC
            LIMIT 1
        ) ASC
    """)
    List<CardEntity> findAllStudyCardsByDeckIdAndUserId(UUID deckId, UUID userId);

    @Query("SELECT card FROM CardEntity card " +
            "JOIN card.deck deck " +
            "WHERE card.id = :cardId AND deck.user.id = :userId")
    CardEntity findByIdAndUserId(UUID cardId, UUID userId);
}
