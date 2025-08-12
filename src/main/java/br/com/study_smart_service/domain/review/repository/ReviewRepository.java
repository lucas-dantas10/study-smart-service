package br.com.study_smart_service.domain.review.repository;

import br.com.study_smart_service.domain.review.model.Review;

import java.util.UUID;

public interface ReviewRepository {

    Review findFirstByCardIdAndUserId(UUID id, UUID userId);
    Review save(Review review);
    void deleteByCardId(UUID cardId);
}
