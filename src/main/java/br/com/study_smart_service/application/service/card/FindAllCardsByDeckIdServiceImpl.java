package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.FindAllCardsByDeckIdUseCase;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllCardsByDeckIdServiceImpl implements FindAllCardsByDeckIdUseCase {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public List<CardDto> execute(UUID deckId, UUID userId) {
        return cardRepository.findAllByDeckIdAndUserId(deckId, userId)
                .stream()
                .map(card -> new CardDto(
                        card.getId().toString(),
                        card.getFrontText(),
                        card.getBackText(),
                        card.getCreatedAt().toString(),
                        card.getUpdatedAt() != null ? card.getUpdatedAt().toString() : null)
                )
                .toList();
    }
}
