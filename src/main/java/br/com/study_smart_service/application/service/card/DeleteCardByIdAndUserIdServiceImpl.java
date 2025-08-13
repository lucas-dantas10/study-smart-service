package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.application.usecase.card.DeleteCardByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCardByIdAndUserIdServiceImpl implements DeleteCardByIdAndUserIdUseCase {

    private final CardRepository cardRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void execute(UUID cardId, UUID userId) throws Exception {
        Card card = cardRepository.findByIdAndUserId(cardId, userId);

        if (card == null) {
            throw new EntityNotFoundException("Card não encontrado.");
        }

        reviewRepository.deleteByCardId(card.getId());

        cardRepository.delete(card);
    }
}
