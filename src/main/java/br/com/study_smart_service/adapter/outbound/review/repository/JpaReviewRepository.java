package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    ReviewEntity findFirstByCardIdAndUserIdOrderByCreatedAtDesc(UUID cardId, UUID userId);
    void deleteByCardId(UUID cardId);
    ReviewEntity findTopByCardIdOrderByCreatedAtDesc(UUID cardId);

    @Query("""
            SELECT r FROM ReviewEntity r
            WHERE r.user.id = :userId
              AND r.card.id IN :cardIds
              AND r.createdAt = (
                  SELECT MAX(r2.createdAt) FROM ReviewEntity r2
                  WHERE r2.card = r.card AND r2.user.id = :userId
              )
            """)
    List<ReviewEntity> findLatestByUserIdAndCardIds(UUID userId, Collection<UUID> cardIds);

    @Query("""
            SELECT CAST(r.createdAt AS LocalDate), COUNT(r)
            FROM ReviewEntity r
            WHERE r.user.id = :userId
            GROUP BY CAST(r.createdAt AS LocalDate)
            """)
    List<Object[]> countReviewsByDay(UUID userId);

    @Query("""
            SELECT r.nextReviewAt, COUNT(r)
            FROM ReviewEntity r
            WHERE r.user.id = :userId
              AND r.nextReviewAt <= :until
              AND r.createdAt = (
                  SELECT MAX(r2.createdAt) FROM ReviewEntity r2
                  WHERE r2.card = r.card AND r2.user.id = :userId
              )
            GROUP BY r.nextReviewAt
            """)
    List<Object[]> countDueByDate(UUID userId, LocalDate until);

    @Query("""
            SELECT d.id, d.title, COUNT(r), SUM(CASE WHEN r.lastQuality >= 2 THEN 1 ELSE 0 END)
            FROM ReviewEntity r
            JOIN r.card c
            JOIN c.deck d
            WHERE r.user.id = :userId
            GROUP BY d.id, d.title
            ORDER BY COUNT(r) DESC
            """)
    List<Object[]> accuracyByDeck(UUID userId);
}
