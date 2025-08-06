package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.FindOneCardByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindOneCardByIdAndUserIdServiceImpl implements FindOneCardByIdAndUserIdUseCase {

    private final CardRepository cardRepository;

    public CardDto execute(UUID cardId, UUID userId) throws Exception {
        Card card = this.cardRepository.findByIdAndUserId(cardId, userId);

        if (card == null) {
            throw new Exception("Card não encontrado.");
        }

        return new CardDto(
                card.getId().toString(),
                card.getFrontText(),
                card.getBackText(),
                card.getCreatedAt().toString(),
                card.getUpdatedAt() != null ? card.getUpdatedAt().toString() : null);
    }
}
