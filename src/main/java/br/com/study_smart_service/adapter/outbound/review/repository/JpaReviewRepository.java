package br.com.study_smart_service.adapter.outbound.review.repository;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    ReviewEntity findFirstByCardIdAndUserIdOrderByCreatedAtDesc(UUID cardId, UUID userId);
}
