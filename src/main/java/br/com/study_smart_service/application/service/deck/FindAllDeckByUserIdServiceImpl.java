package br.com.study_smart_service.application.service.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindAllDeckByUserIdUseCase;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllDeckByUserIdServiceImpl implements FindAllDeckByUserIdUseCase {

    private final DeckRepository deckRepository;

    public List<DeckDto> execute(UUID userId) {
        List<Deck> decks = deckRepository.findAllByUserId(userId);

        return decks.stream()
                .map(deck -> new DeckDto(
                        deck.getId().toString(),
                        deck.getTitle(),
                        deck.getCreatedAt().toString(),
                        deck.getUpdatedAt() != null ? deck.getUpdatedAt().toString() : null)
                )
                .toList();
    }
}
