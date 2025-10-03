package br.com.study_smart_service.application.usecase.review;

import br.com.study_smart_service.domain.review.model.Review;
import java.util.UUID;

public interface FindRecentReviewByCardIdUseCase {

    Review execute(UUID cardId);
}
