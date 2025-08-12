package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.UpdateCardUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCardServiceImpl implements UpdateCardUseCase {

    private final CardRepository cardRepository;

    public CardDto execute(UUID cardId, UUID userId, String frontText, String backText) throws Exception {
        Card card = cardRepository.findByIdAndUserId(cardId, userId);

        if (card == null) {
            throw new EntityNotFoundException("Card não encontrado.");
        }

        card.setFrontText(frontText);
        card.setBackText(backText);
        card.setUpdatedAt(LocalDateTime.now());

        cardRepository.save(card);

        return new CardDto(
                card.getId().toString(),
                card.getFrontText(),
                card.getBackText(),
                card.getCreatedAt().toString(),
                card.getUpdatedAt() != null ? card.getUpdatedAt().toString() : null);
    }
}
