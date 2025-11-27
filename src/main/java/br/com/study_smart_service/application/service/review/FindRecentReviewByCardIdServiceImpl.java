package br.com.study_smart_service.application.service.review;

import br.com.study_smart_service.application.usecase.review.FindRecentReviewByCardIdUseCase;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindRecentReviewByCardIdServiceImpl implements FindRecentReviewByCardIdUseCase {

    private final ReviewRepository reviewRepository;

    public Review execute(UUID cardId) {
        return reviewRepository.findRecentByCardId(cardId);
    }
}
