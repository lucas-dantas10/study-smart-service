package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindOneDeckByIdAndUserIdUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindOneDeckByIdAndUserIdServiceImpl implements FindOneDeckByIdAndUserIdUseCase {

    private final DeckRepository deckRepository;

    public DeckDto execute(UUID deckId, UUID userId) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(deckId, userId);

        if (deck == null) {
            throw new Exception("Deck não encontrado.");
        }

        return new DeckDto(
                deck.getId().toString(),
                deck.getTitle(),
                deck.getCreatedAt().toString(),
                deck.getUpdatedAt() != null ? deck.getUpdatedAt().toString() : null);
    }
}
