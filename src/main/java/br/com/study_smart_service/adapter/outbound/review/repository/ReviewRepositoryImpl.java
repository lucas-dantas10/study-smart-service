package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import br.com.study_smart_service.utils.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
