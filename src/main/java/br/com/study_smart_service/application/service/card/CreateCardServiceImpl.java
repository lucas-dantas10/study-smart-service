package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.CreateCardUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCardServiceImpl implements CreateCardUseCase {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public CardDto execute(UUID deckId, UUID userId, String frontText, String backText) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId);

        if (deck == null) {
            throw new Exception("Deck não encontrado.");
        }

        Card card = new Card(deck, frontText, backText);

        card = cardRepository.save(card);

        return new CardDto(
                card.getId().toString(),
                card.getFrontText(),
                card.getBackText(),
                card.getCreatedAt().toString(),
                card.getUpdatedAt() != null ? card.getUpdatedAt().toString() : null);
    }
}
