package br.com.study_smart_service.application.service.review;

import br.com.study_smart_service.application.usecase.review.UndoReviewUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UndoReviewServiceImpl implements UndoReviewUseCase {

    private final CardRepository cardRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(UUID cardId, UUID userId) throws Exception {
        if (cardRepository.findByIdAndUserId(cardId, userId) == null) {
            throw new EntityNotFoundException("Card não encontrado.");
        }

        if (!reviewRepository.deleteLatestByCardIdAndUserId(cardId, userId)) {
            throw new EntityNotFoundException("Este card não tem revisão para desfazer.");
        }
    }
}
