package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import br.com.study_smart_service.domain.review.model.DailyReviewCount;
import br.com.study_smart_service.domain.review.model.DeckAccuracy;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import br.com.study_smart_service.utils.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JpaReviewRepository jpaReviewRepository;
    private final ReviewMapper reviewMapper;

    public Review findFirstByCardIdAndUserId(UUID id, UUID userId) {
        ReviewEntity reviewEntity = jpaReviewRepository.findFirstByCardIdAndUserIdOrderByCreatedAtDesc(id, userId);

        return reviewMapper.jpaToDomain(reviewEntity);
    }

    public Review save(Review review) {
        ReviewEntity reviewEntity = reviewMapper.domainToJpa(review);

        reviewEntity = jpaReviewRepository.save(reviewEntity);

        return reviewMapper.jpaToDomain(reviewEntity);
    }

    public void deleteByCardId(UUID cardId) {
        jpaReviewRepository.deleteByCardId(cardId);
    }

    public Review findRecentByCardId(UUID cardId) {
        ReviewEntity reviewEntity = jpaReviewRepository.findTopByCardIdOrderByCreatedAtDesc(cardId);

        return reviewMapper.jpaToDomain(reviewEntity);
    }

    @Override
    public boolean deleteLatestByCardIdAndUserId(UUID cardId, UUID userId) {
        ReviewEntity latest = jpaReviewRepository.findFirstByCardIdAndUserIdOrderByCreatedAtDesc(cardId, userId);

        if (latest == null) {
            return false;
        }

        jpaReviewRepository.delete(latest);

        return true;
    }

    @Override
    public Map<UUID, Review> findLatestByUserIdAndCardIds(UUID userId, Collection<UUID> cardIds) {
        Map<UUID, Review> latestByCard = new HashMap<>();

        if (cardIds.isEmpty()) {
            return latestByCard;
        }

        for (ReviewEntity entity : jpaReviewRepository.findLatestByUserIdAndCardIds(userId, cardIds)) {
            latestByCard.put(entity.getCard().getId(), reviewMapper.jpaToDomain(entity));
        }

        return latestByCard;
    }

    @Override
    public List<DailyReviewCount> countReviewsByDay(UUID userId) {
        return jpaReviewRepository.countReviewsByDay(userId).stream()
                .map(row -> new DailyReviewCount(toLocalDate(row[0]), ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<DailyReviewCount> countDueByDate(UUID userId, LocalDate until) {
        return jpaReviewRepository.countDueByDate(userId, until).stream()
                .map(row -> new DailyReviewCount(toLocalDate(row[0]), ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<DeckAccuracy> accuracyByDeck(UUID userId) {
        return jpaReviewRepository.accuracyByDeck(userId).stream()
                .map(row -> new DeckAccuracy(
                        (UUID) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        row[3] == null ? 0 : ((Number) row[3]).longValue()))
                .toList();
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }

        return ((java.sql.Date) value).toLocalDate();
    }
}
