package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.application.usecase.card.DeleteCardByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCardByIdAndUserIdServiceImpl implements DeleteCardByIdAndUserIdUseCase {

    private final CardRepository cardRepository;

    public void execute(UUID cardId, UUID userId) throws Exception {
        Card card = cardRepository.findByIdAndUserId(cardId, userId);

        if (card == null) {
            throw new Exception("Card não encontrado.");
        }

        cardRepository.delete(card);
    }
}
