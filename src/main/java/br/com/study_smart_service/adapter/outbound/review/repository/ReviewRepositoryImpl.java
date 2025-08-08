package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;

import java.util.UUID;

public class ReviewRepositoryImpl implements ReviewRepository {

    public Review findOneByIdAndUserId(UUID id, UUID userId) {
        return null;
    }

    public Review save(Review review) {
        return null;
    }
}
