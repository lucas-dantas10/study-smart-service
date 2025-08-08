package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import br.com.study_smart_service.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    Review findOneByIdAndUserId(UUID id, UUID userId);
}
