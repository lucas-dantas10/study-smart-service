package br.com.study_smart_service.application.service.review;

import br.com.study_smart_service.application.usecase.review.ReviewCardUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.enums.EQuality;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import br.com.study_smart_service.domain.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCardServiceImpl implements ReviewCardUseCase {

    private final CardRepository cardRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(User user, UUID cardId, int quality) throws Exception {
        Card card = cardRepository.findByIdAndUserId(cardId, user.getId());

        if (card == null) {
            throw new EntityNotFoundException("Card não encontrado.");
        }

        Review reviewModel = reviewRepository.findFirstByCardIdAndUserId(cardId, user.getId());

        reviewModel = reviewModel == null
                ? new Review(user, card)
                : new Review(user, card).createNewReview(
                        reviewModel.getNextReviewAt(),
                        reviewModel.getInterval(),
                        reviewModel.getRepetition(),
                        reviewModel.getEasiness(),
                        reviewModel.getLastQuality()
                );

        reviewModel.review(EQuality.fromQuality(quality));

        reviewRepository.save(reviewModel);
    }
}
