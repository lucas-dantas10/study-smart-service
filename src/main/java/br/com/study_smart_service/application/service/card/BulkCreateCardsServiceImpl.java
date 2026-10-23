package br.com.study_smart_service.application.service.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.BulkCreateCardsDto;
import br.com.study_smart_service.application.usecase.card.BulkCreateCardsUseCase;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.deck.model.Deck;
import br.com.study_smart_service.domain.deck.repository.DeckRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BulkCreateCardsServiceImpl implements BulkCreateCardsUseCase {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Transactional
    public int execute(UUID userId, BulkCreateCardsDto request) throws Exception {
        Deck deck = deckRepository.findByIdAndUserId(UUID.fromString(request.deckId()), userId);

        if (deck == null) {
            throw new EntityNotFoundException("Deck não encontrado.");
        }

        for (BulkCreateCardsDto.Item item : request.cards()) {
            cardRepository.save(new Card(deck, item.frontText(), item.backText(), item.tags()));
        }

        return request.cards().size();
    }
}
